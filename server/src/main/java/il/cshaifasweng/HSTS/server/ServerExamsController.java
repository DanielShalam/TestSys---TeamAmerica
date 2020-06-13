package il.cshaifasweng.HSTS.server;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import il.cshaifasweng.HSTS.entities.Exam;
import il.cshaifasweng.HSTS.entities.Question;

public class ServerExamsController {

	private static final int MAX_NUM_OF_EXAMS = 1000;
	public static final int ILLEGAL_ID = 0;
	
	public ServerExamsController() {
		
	}

	
	public void createBeforeCommit(Exam exam) {
		Exam newExam = new Exam(
				exam.getTeacherId(),
				exam.getCourseId(),
				exam.getQuestionList(),
				exam.getAnswerList(),
				exam.getStudentInstructions(),
				exam.getTeacherInstructions(),
				exam.getAssignedDuration());
		
		commitExamToDB(newExam);
		newExam.setExamId();
	}
	
	public int commitExamToDB(Exam exam) {
		ConnectToDB.save(exam);
		exam.setExamId();
		
		return 1;
	}
	
	public Exam getExamById(int id) {	
		// Get exam by its id
		Exam exam = ConnectToDB.getById(Exam.class, id);
		return exam;
	}
	
	public List<Exam> getAllExam() {
		// Get all exams from database
		List<Exam> eList = ConnectToDB.getAll(Exam.class);
    	return eList;	
	}
	
//	public List<Exam> getExamsByCreator(int teacher_id) {
//		// Get all the exams of some teacher by its id
//		
//		ConnectToDB.getBySomeKey(type, key, value)
//		Criteria criteria = ConnectToDB.session.createCriteria(Exam.class);
//		List<Exam> eList = criteria.add(Restrictions.eq("creatorId", teacher_id)).list();
//
//    	return eList;	
//	}
}
