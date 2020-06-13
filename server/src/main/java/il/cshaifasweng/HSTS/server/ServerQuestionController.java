package il.cshaifasweng.HSTS.server;

import java.util.List;

import il.cshaifasweng.HSTS.entities.Question;

public class ServerQuestionController {
	
	private static final int MAX_NUM_OF_QUESTIONS = 1000;
	public static final int ILLEGAL_ID = 0;
	
	public ServerQuestionController() {
		
	}
	
	public static void createBeforeCommit(Question question) {
		Question newQuestion = new Question(
				question.getCourseId(),
				question.getQuestion(),
				question.getAnswers(),
				question.getInstructions(),
				question.getCorrectAnswer(),
				question.getTeacherId());
		
		commitQuestionToDB(newQuestion);
	}
	
	public static int commitQuestionToDB(Question question) {
		ConnectToDB.save(question);
		return 1;
	}
	
	public static Question getQuestionById(int id) {	
		// Get question by its id
		Question question = ConnectToDB.getById(Question.class, id);
		return question;
	}
	
	public List<Question> getAllQuestions() {
		// Get all question from database
		List<Question> qList = ConnectToDB.getAll(Question.class);
    	return qList;	
	}
	
//	public List<Question> getQuestionsByTeacher(int teacher_id) {
//		// Get all the question of some teacher by its id
//		
//		Criteria criteria = ConnectToDB.session.createCriteria(Question.class);
//		List<Question> qList = criteria.add(Restrictions.eq("teacher_id", teacher_id)).list();
//
//    	return qList;	
//	}
}
