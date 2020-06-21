package il.cshaifasweng.HSTS.server;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import il.cshaifasweng.HSTS.entities.ExamType;
import il.cshaifasweng.HSTS.entities.Examination;
import il.cshaifasweng.HSTS.server.utilities.WordHandler;

public class ServerExaminationController {
	
	static File desktopDir = new File(System.getProperty("user.home"), "Desktop"+".doc");
			
	public static void createBeforeCommit(Examination examination) {
		
	}
	
	public void calcExaminationStatistics(int exec_code) {
		
	}	
	
//	public static WordHandler createManualExam(Examination examination) throws IOException {
//	}	
	
}
