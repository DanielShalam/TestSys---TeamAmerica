package il.cshaifasweng.HSTS.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import il.cshaifasweng.HSTS.entities.Exam;
import il.cshaifasweng.HSTS.entities.ExaminationStudent;
import il.cshaifasweng.HSTS.entities.Question;

public class ServerStudentExaminationController {
	
	// Function to calculate grade of student examination
	private static void calcGrades(ExaminationStudent studentExam) {
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
	}
	
}
