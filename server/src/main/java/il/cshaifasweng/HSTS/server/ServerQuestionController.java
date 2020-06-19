package il.cshaifasweng.HSTS.server;

import java.util.List;

import il.cshaifasweng.HSTS.entities.Question;

public class ServerQuestionController {
	
	public ServerQuestionController() {
		
	}

	// Create new instance of question before committing to database
	public static String createBeforeCommit(Question question) {
		// Crate new question
		Question newQuestion = new Question(
				question.getCourseId(),
				question.getQuestion(),
				question.getAnswers(),
				question.getInstructions(),
				question.getCorrectAnswer(),
				question.getTeacherId());
		
		// Commit the new question
		int return_value = commitQuestionToDB(newQuestion);
		if (return_value == 1) {
			return "Question commited successfully. ";
		}
		else if (return_value == -2) {
			return "Error - Course ID is invalied. ";
		}
		return "Error - Please try again. ";
	}

	// Commit new Question to database
	public static int commitQuestionToDB(Question question) {
		try {
			int new_id = ConnectToDB.save(question);

			if (new_id == question.getQuestionId()) {	// Failure
				return -1;
			}
			return 1;	// Success
			
		} catch (Exception logExceptions) {		// Foreign key is invalid
			return -2;
		}
	}

	// Get question by its id
	public static Question getQuestionById(int id) {	
		try {
			Question question = ConnectToDB.getById(Question.class, id);
			return question;	// Success
			
		} catch (Exception nullPointerException) {	// No Question is found
			return null;
		}

	}

	// Get all question from database
	public static List<Question> getAllQuestions() {
		List<Question> qList = ConnectToDB.getAll(Question.class);
    	return qList;	
	}

	// Get all the question of some teacher by its id
	public static List<Question> getQuestionsByAtrribute(String attribute, int value) {
		try {
			List<Question> qList = ConnectToDB.getByAttribute(Question.class, attribute, value);	// Getting by Teacher id
	    	return qList;	
	    	
		} catch (Exception illegalArgumentException) {	// No question matches teacher_id
			return null;
		}
	}
	
	// Delete question from database using its id
	public static String deleteQuestionByID(int question_id) {

		Question question = ServerQuestionController.getQuestionById(question_id);	// Getting the question

		if (question == null) {		// Question id not in database
			return "Error - Question not found. ";
		}
		else if (question.getUsedInTest()) {	// Validation condition
			return "Error - Question already used in Exam. ";
		}
		return "Question deleted successfully. ";
	}
	
	// Update existing question without creating new instance
	public static Question updateQuestion(int question_id) {
		
		Question question = ServerQuestionController.getQuestionById(question_id);	// Getting the question

		if (question == null) {		// Question id not in database
			return null;
		}
		
		question.setUsedInTest(true);
		
		ConnectToDB.update(question);
		return ServerQuestionController.getQuestionById(question_id);
		
	}
}
