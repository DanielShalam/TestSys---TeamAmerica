package il.cshaifasweng.HSTS.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import il.cshaifasweng.HSTS.entities.Carrier;
import il.cshaifasweng.HSTS.entities.Course;
import il.cshaifasweng.HSTS.entities.Exam;
import il.cshaifasweng.HSTS.entities.Examination;
import il.cshaifasweng.HSTS.entities.ExaminationStatus;
import il.cshaifasweng.HSTS.entities.ExaminationStudent;
import il.cshaifasweng.HSTS.entities.Question;
import il.cshaifasweng.HSTS.entities.User;

public class ServerStudentExaminationController {
	
	protected static ExaminationStudent commitToDB(Carrier carrier) {
			
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
	
	protected static void updateGrade(ExaminationStudent studentExam) {
		studentExam.setExaminationStatus(ExaminationStatus.FINALIZED);
		ConnectToDB.update(studentExam);
	}
	
	// Function to calculate grade of student examination
	private static ExaminationStudent calcGrades(ExaminationStudent studentExam) {
		int grade = 0;
		Exam exam = studentExam.getExamination().getExam();
		List<Question> examQuestions = exam.getQuestionList();
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
	
	public static List<ExaminationStudent> getExamsinationsByAtrribute(String attribute, int value) {
		try {
			List<ExaminationStudent> eList = ConnectToDB.getByAttribute(ExaminationStudent.class, attribute, value);	// Getting by Teacher id
			return eList;
	    	
		} catch (Exception illegalArgumentException) {	// No examination match this attrubute
			return null;
		}
	}
	
	// ExaminationStudent for teacher to view scores
	public static List<ExaminationStudent> getByTeacherExams(int teacherId, int courseId) {
		Session session = ConnectToDB.getNewSession();
		User user = session.get(User.class, teacherId);
		Course course = session.get(Course.class, courseId);
		List<ExaminationStudent> examinationStudents = new ArrayList<ExaminationStudent>();
		Set<Examination> examinations = course.getExaminationList(); 	// Getting examination by teacher
		for (Examination examination: examinations) {
			if(examination.getExam().getTeacherId() == teacherId) {
				for(ExaminationStudent examinationStudent: examination.getExamineesList()) {
					if(examinationStudent.getExaminationStatus() == ExaminationStatus.FINALIZED) {
						examinationStudents.add(examinationStudent);
					}
				}
			}
		}
		
		ConnectToDB.closeOuterSession(session);
		return examinationStudents;
	}
	
	// ExaminationStudent to grade by teacher
	public static List<ExaminationStudent> getByUser(int userId, ExaminationStatus status) {
		Session session = ConnectToDB.getNewSession();
		User user = session.get(User.class, userId);
		Set<ExaminationStudent> examinations = user.getExaminationList(); 	// Getting examination by teacher
//		List<ExaminationStudent> examinationStudents = new ArrayList<ExaminationStudent>();
//		for (Examination examination: examinations) {
//			for(ExaminationStudent examinationStudent: examination.getExamineesList()) {
//				if(examinationStudent.getExaminationStatus() == status) {
//					examinationStudents.add(examinationStudent);
//				}
//			}
//		}
		List<ExaminationStudent> eList = new ArrayList<ExaminationStudent>();
		eList.addAll(examinations);
		ConnectToDB.closeOuterSession(session);
		return eList;
	}
	
	// ExaminationStudent to grade by teacher
	public static List<ExaminationStudent> getByCourse(int teacherId, int courseId, ExaminationStatus status) {
		Session session = ConnectToDB.getNewSession();
		Course course = session.get(Course.class, courseId);
		Set<Examination> examinations = course.getExaminationList(); 	// Getting examination by teacher
		List<ExaminationStudent> examinationStudents = new ArrayList<ExaminationStudent>();
		for (Examination examination: examinations) {
			if(examination.getTeacherId() == teacherId) {
				for(ExaminationStudent examinationStudent: examination.getExamineesList()) {
					if(examinationStudent.getExaminationStatus() == status) {
						examinationStudents.add(examinationStudent);
					}
				}
			}
		}
		ConnectToDB.closeOuterSession(session);
		return examinationStudents;
	}
	
}
