package il.cshaifasweng.HSTS.server;

import java.util.List;

import il.cshaifasweng.HSTS.entities.Question;


public class ServerQuestionController {
	
	public ServerQuestionController() {
		
	}

	// Commit new Question to database
	public static String commitQuestionToDB(Question question, int id) {
		Boolean isValid = ConnectToDB.checkForDuplicateQuestion(question);
		if (isValid) {
			try {
				if(id == -1) { // Adding new question
					int new_id = ConnectToDB.save(question);

					if (new_id == question.getQuestionId()) {	// Failure
						return "Error - Please try again. ";
					}
					return "Question commited successfully. ";	// Success	
				}
				
				else {	// Edit existing one
					Question questionToEdit = ConnectToDB.getById(Question.class, id);	// Get question for update
					questionToEdit.setQuestion(question.getQuestion());
					questionToEdit.setAnswers(question.getAnswers());
					questionToEdit.setInstructions(question.getInstructions());
					questionToEdit.setCorrectAnswer(question.getCorrectAnswer());
					ConnectToDB.update(questionToEdit);
				}
				
			} catch (Exception logExceptions) {		// Foreign key is invalid
				return "Error - Course ID is invalied. ";
			}
		}
		return "Error - Question already in Database. ";
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
	public static String deleteQuestion(Question question) {

		ConnectToDB.deleteByInstance(Question.class, question);	// Getting the question
		return "Question deleted successfully. ";
	}
	
	// Update existing question without creating new instance
	public static void updateQuestion(Question question) {
		
		if (!question.getUsedInTest()) {		// Question id not in database
			question.setUsedInTest(true);
			ConnectToDB.update(question);
		}

	}
}
