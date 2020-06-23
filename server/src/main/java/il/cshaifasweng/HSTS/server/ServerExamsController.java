package il.cshaifasweng.HSTS.server;

import java.util.List;

import com.google.protobuf.Duration;

import il.cshaifasweng.HSTS.entities.Exam;
import il.cshaifasweng.HSTS.entities.ExamType;
import il.cshaifasweng.HSTS.entities.Examination;
import il.cshaifasweng.HSTS.entities.Question;

public class ServerExamsController {

	public static final int ILLEGAL_ID = 0;
	
	public ServerExamsController() {
		
	}
	
	public static String commitExamToDB(Exam exam) {
		for (Question question: exam.getQuestionList()) {	// Update questions if needed
			ServerQuestionController.updateQuestion(question);
		}
		
		int new_id = ConnectToDB.save(exam);
		
		// Failure
		if (new_id == exam.getExamId()) {
			return "Error - Please try again. ";
		}
		// Success			
		return "Exam commited successfully. ";		
	}
	
	public static Exam getExamById(int id) {	
		// Get exam by its id
		try {
			Exam exam = ConnectToDB.getById(Exam.class, id);
			return exam;	// Success
			
		} catch (Exception nullPointerException) {	// No Exam is found
			return null;
		}
	}
	
	public static List<Exam> getAllExam() {
		// Get all exams from database
		List<Exam> eList = ConnectToDB.getAll(Exam.class);
    	return eList;	
	}
	
	public static List<Exam> getExamsByAtrribute(String attribute, int value) {
		try {
			List<Exam> eList = ConnectToDB.getByAttribute(Exam.class, attribute, value);	// Getting by Teacher id
	    	return eList;	
	    	
		} catch (Exception illegalArgumentException) {	// No exam match this attrubute
			return null;
		}
	}
	
	// Delete exam from database using its id
	public static String deleteExamByID(int examID) {

		Exam exam = ServerExamsController.getExamById(examID);	// Getting the exam

		if (exam == null) {		// Exam id not in database
			return "Error - Exam not found. ";
		}
		else if (exam.isUsedInExamination()) {	// Validation condition
			return "Error - Exam already been used. ";
		}
		return "Exam deleted successfully. ";
	}
	
	// Delete exam from database using its id
	public static String deleteExamByEntity(Exam exam) {
		//TODO Validation of usedInExamination - Client or Server?
		ConnectToDB.deleteByInstance(Exam.class, exam);
		return "Exam deleted successfully. ";
	}
	
	// Update existing exam without creating new instance
	public static String updateExam(Exam exam) {
		
		if (!exam.isUsedInExamination()) {
			exam.setUsedInExamination(true);
			ConnectToDB.update(exam);
		}
				
		return "Exam updated successfully. ";
		
	}
	
}
