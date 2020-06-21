package il.cshaifasweng.HSTS.server;

import java.time.Duration;

import il.cshaifasweng.HSTS.entities.ExamType;
import il.cshaifasweng.HSTS.entities.Examination;

public class ServerExaminationController {
	
	public static void createBeforeCommit(Examination examination) {
		
	}
	
	public void calcExaminationStatistics(int exec_code) {
		
	}
	
	public static void instigateExam(int teacherID, ExamType type, Duration duration, int examID) {
		Examination newExamination = new Examination(teacherID, type, duration, examID);
	}
		
	
}
