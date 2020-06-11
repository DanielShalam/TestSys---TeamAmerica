package il.cshaifasweng.HSTS.server;

import java.util.List;

import il.ac.haifa.cs.sweng.Hibernate.ConnectToDB;
import il.cshaifasweng.HSTS.entities.Question;

public class ServerQuestionController {
	
	private static final int MAX_NUM_OF_QUESTIONS = 1000;
	public static final int ILLEGAL_ID = 0;
	
	public ServerQuestionController() {
		
	}
	
	public void createBeforeCommit(Question question) {
		Questions newQuestion = new Questions(
				question.getCourseId(),
				question.getQuestion(),
				question.getAnswers(),
				question.getInstructions(),
				question.getCorrectAnswer(),
				question.getLecturerId());
		
		commitQuestionToDB(newQuestion);
	}
	
	public int commitQuestionToDB(Question question) {
		ConnectToDB.session.beginTransaction();
		ConnectToDB.session.save(question);
		question.setQuestionId();
		ConnectToDB.session.save(question);
		ConnectToDB.session.flush();
		ConnectToDB.session.clear();
		ConnectToDB.session.getTransaction().commit();
	}
	
	public Questions getQuestionById(int id) {	
		// Get question by its id
		Questions question = (Questions) ConnectToDB.session.get(Questions.class , id%MAX_NUM_OF_QUESTIONS);
		return question;
	}
	
	public List<Questions> getAllQuestions() {
		// Get all question from database
		List<Questions> qList = ConnectToDB.getAll(Questions.class);
    	return qList;	
	}
	
	public List<Questions> getQuestionsByTeacher(int teacher_id) {
		// Get all the question of some teacher by its id
		
		Criteria criteria = session.createCriteria(Question.class);
		List<Questions> qList = criteria.add(Restrictions.eq("teacher_id", teacher_id)).list();

    	return qList;	
	}
}
