package il.cshaifasweng.HSTS.server;

import java.util.List;
import il.cshaifasweng.HSTS.entities.Exam;

public class ServerExamsController {

	public static final int ILLEGAL_ID = 0;
	
	public ServerExamsController() {
		
	}

	
	public static String createBeforeCommit(Exam exam) {
	
		int return_value = commitExamToDB(exam);
		if (return_value == 1) {
			return "Exam commited successfully. ";
		}
		else if (return_value == -2) {
			return "Error - Course ID is invalied. "; //TODO check if thats ok
		}
		return "Error - Please try again. ";
	}
	
	public static int commitExamToDB(Exam exam) {
		int new_id = ConnectToDB.save(exam);
		// Failure
		if (new_id == exam.getExamId()) {
			return -1;
		}
		// Success			
		return 1;		
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
	public static String deleteExamByID(int exam_id) {

		Exam exam = ServerExamsController.getExamById(exam_id);	// Getting the exam

		if (exam == null) {		// Exam id not in database
			return "Error - Exam not found. ";
		}
		else if (exam.isUsedInExamination()) {	// Validation condition
			return "Error - Exam already been used. ";
		}
		return "Exam deleted successfully. ";
	}
	
	// Update existing exam without creating new instance
	public static String updateExam(int exam_id) {
		
		Exam exam = ServerExamsController.getExamById(exam_id);	// Getting the exam

		if (exam == null) {		// Exam id not in database
			return "Error - Exam not found. ";
		}
		
		exam.setUsedInExamination(true);
		
		ConnectToDB.update(exam);
		return "Exam updated successfully. ";
		
	}
}
