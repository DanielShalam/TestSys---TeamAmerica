package il.cshaifasweng.HSTS.server;

import il.ac.haifa.cs.sweng.Hibernate.ConnectToDB;
import il.cshaifasweng.HSTS.entities.Exam;

public class ServerExamsController {

	private static final int MAX_NUM_OF_EXAMS = 1000;
	public static final int ILLEGAL_ID = 0;
	
	public ServerExamsController() {
		
	}
	
	public void createBeforeCommit(Exam exam) {
		Exam newExam = new Exam(
				exam.getCourseId(),
				exam.getQuestionList(),
				exam.getAnswerList(),
				exam.getStudentInstructions(),
				exam.getTeacherInstructions(),
				exam.getCreatorId(),
				exam.getAssignedDuration);
		
		commitExamToDB(newExam);
	}
	
	public int commitExamToDB(Exam exam) {
		ConnectToDB.session.beginTransaction();
		ConnectToDB.session.save(exam);
		exam.setExamId();
		ConnectToDB.session.save(exam);
		ConnectToDB.session.flush();
		ConnectToDB.session.clear();
		ConnectToDB.session.getTransaction().commit();
	}
	
	public Exam getExamById(int id) {	
		// Get exam by its id
		Exam exam = (Exam) ConnectToDB.session.get(Exam.class , id%MAX_NUM_OF_EXAMS);
		return exam;
	}
	
	public List<Exam> getAllExam() {
		// Get all exams from database
		List<Exam> eList = ConnectToDB.getAll(Exam.class);
    	return eList;	
	}
	
	public List<Exam> getExamsByCreator(int teacher_id) {
		// Get all the exams of some teacher by its id
		
		Criteria criteria = session.createCriteria(Exam.class);
		List<Exam> eList = criteria.add(Restrictions.eq("creatorId", teacher_id)).list();

    	return eList;	
	}
}
