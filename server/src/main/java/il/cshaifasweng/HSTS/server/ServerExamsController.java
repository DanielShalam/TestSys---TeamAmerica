package il.cshaifasweng.HSTS.server;

import java.util.List;
import il.cshaifasweng.HSTS.entities.Exam;

public class ServerExamsController {

	public static final int ILLEGAL_ID = 0;
	
	public ServerExamsController() {
		
	}

	
	public int createBeforeCommit(Exam exam) {
		Exam newExam = new Exam(
				exam.getTeacherId(),
				exam.getCourseId(),
				exam.getQuestionList(),
				exam.getAnswerList(),
				exam.getStudentInstructions(),
				exam.getTeacherInstructions(),
				exam.getAssignedDuration());
		
		int return_value = commitExamToDB(newExam);
		return return_value;
	}
	
	public int commitExamToDB(Exam exam) {
		int new_id = ConnectToDB.save(exam);
		// Failure
		if (new_id == exam.getExamId()) {
			return -1;
		}
		// Success			
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
	
	public List<Exam> getQuestionsByTeacher(int teacher_id) {
		// Get all the question of some teacher by its id
		List<Exam> eList = ConnectToDB.getByAttribute(Exam.class, "teacher_id", teacher_id);
    	return eList;	
	}
}
