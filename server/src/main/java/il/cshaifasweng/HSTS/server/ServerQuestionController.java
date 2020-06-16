package il.cshaifasweng.HSTS.server;

import java.util.List;

import il.cshaifasweng.HSTS.entities.Question;

public class ServerQuestionController {
	
	private static final int MAX_NUM_OF_QUESTIONS = 1000;
	public static final int ILLEGAL_ID = 0;
	
	public ServerQuestionController() {
		
	}
	
	public static String createBeforeCommit(Question question) {
		Question newQuestion = new Question(
				question.getCourseId(),
				question.getQuestion(),
				question.getAnswers(),
				question.getInstructions(),
				question.getCorrectAnswer(),
				question.getTeacherId());
		
		int return_value = commitQuestionToDB(newQuestion);
		if (return_value == 1) {
			return "Question commited to Database";
		}
		return "Commit failed";
	}
	
	public static int commitQuestionToDB(Question question) {
		int new_id = ConnectToDB.save(question);
		// Failure
		if (new_id == question.getQuestionId()) {
			return -1;
		}
		// Success
		return 1;
	}
	
	public static Question getQuestionById(int id) {	
		// Get question by its id
		Question question = ConnectToDB.getById(Question.class, id);
		return question;
	}
	
	public static List<Question> getAllQuestions() {
		// Get all question from database
		List<Question> qList = ConnectToDB.getAll(Question.class);
    	return qList;	
	}
	
	public List<Question> getQuestionsByTeacher(int teacher_id) {
		// Get all the question of some teacher by its id
		List<Question> qList = ConnectToDB.getByAttribute(Question.class, "teacher_id", teacher_id);
    	return qList;	
	}
}
