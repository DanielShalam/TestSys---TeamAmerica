package il.cshaifasweng.HSTS.server;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import il.cshaifasweng.HSTS.entities.Exam;
import il.cshaifasweng.HSTS.entities.ExamType;
import il.cshaifasweng.HSTS.entities.Examination;
import il.cshaifasweng.HSTS.server.utilities.WordHandler;

public class ServerExaminationController {
	
	static File desktopDir = new File(System.getProperty("user.home"), "Desktop"+".doc");
			

	public static String commitExaminationToDB(Examination examination) {
		int new_id = ConnectToDB.save(examination);
		// Failure
		if (new_id == examination.getExamination_id()) {
			return "Error - Please try again. ";
		}
		// Success			
		return "Exam commited successfully. ";		
	}
	
	public static Examination getExaminationById(int id) {	
		// Get exam by its id
		try {
			Examination exam = ConnectToDB.getById(Examination.class, id);
			return exam;	// Success
			
		} catch (Exception nullPointerException) {	// No Exam is found
			return null;
		}
	}
	
	public void calcExaminationStatistics(int exec_code) {
		
	}	
	
//	public static WordHandler createManualExam(Examination examination) throws IOException {
//	}	
	
	public static Examination getByExecutionCode(int exec_code) {
		try {
			Examination exemination = (Examination) ConnectToDB.getByAttribute(Examination.class, "executionCode", exec_code);
			return exemination;
			
		} catch (Exception illegalArgumentException) {	// No examination match this attrubute
			return null;
		}
	}	
	
	public static List<Examination> getExamsinationsByAtrribute(String attribute, int value) {
		try {
			List<Examination> eList = ConnectToDB.getByAttribute(Examination.class, attribute, value);	// Getting by Teacher id
			return eList;	
	    	
		} catch (Exception illegalArgumentException) {	// No examination match this attrubute
			return null;
		}
	}
	
	// Delete exam from database using its id
	public static String deleteExamByEntity(Examination examination) {
		//TODO Validation of usedInExamination - Client or Server?
		ConnectToDB.deleteByInstance(Examination.class, examination);
		return "Exam deleted successfully. ";
	}
	
}
