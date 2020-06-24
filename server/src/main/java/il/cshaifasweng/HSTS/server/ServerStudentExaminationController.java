package il.cshaifasweng.HSTS.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import il.cshaifasweng.HSTS.entities.Carrier;
import il.cshaifasweng.HSTS.entities.Exam;
import il.cshaifasweng.HSTS.entities.ExaminationStudent;
import il.cshaifasweng.HSTS.entities.Question;

public class ServerStudentExaminationController {
	
	protected static ExaminationStudent commitToDB(Carrier carrier) {
			
		System.out.println("server - start commit");
		int studentId = (int) carrier.carrierMessageMap.get("studentId");
		int examinationId = (int) carrier.carrierMessageMap.get("examinationId");
		
		switch (ConnectToDB.checkIfSubmitted(studentId, examinationId)) {
			case "New": 	// New StudentExamination
				ExaminationStudent exmnStudent = ConnectToDB.saveExmnStudent(studentId, examinationId);
				return exmnStudent;
				
				
			case "Submit": 	
				System.out.println("SWITCH CASE - start submit");
				ExaminationStudent exmnToSubmit = (ExaminationStudent)carrier.carrierMessageMap.get("exmnStudent");
				exmnToSubmit = calcGrades(exmnToSubmit);
				carrier.carrierMessageMap.replace("exmnStudent",exmnToSubmit);
				ConnectToDB.updateExmnStudent(carrier);
				// TODO add method to calculate grade and submit examination  
				return null;
				
				
			case "Already submited": 	// Student already submitted exam
				return null;
				
				
			default:
				return null;
		}
	}
	
	// Function to calculate grade of student examination
	private static ExaminationStudent calcGrades(ExaminationStudent studentExam) {
		int grade = 0;
		Exam exam = studentExam.getExamination().getExam();
		Set<Question> examQuestions = exam.getQuestionList();
		ArrayList<Question> questionList = new ArrayList<Question>(examQuestions);
		List<Integer> studentAnswers = studentExam.getStudentsAnswers();

	    for (int index = 0; index < examQuestions.size(); index++) {
	        if(questionList.get(index).getCorrectAnswer() == studentAnswers.get(index)) {
	        	grade += exam.getScoringList()[index];
	        }
	    }
	    studentExam.setGrade(grade);
	    return studentExam;
	}
	
}
