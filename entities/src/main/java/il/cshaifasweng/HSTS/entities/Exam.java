package il.cshaifasweng.HSTS.entities;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "exam_table")
public class Exam implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int examNum;
	
	// exam_id is composed of course_id (2 digits) + exam_num (2 digits)
	@Column(name = "exam_id")
	private int examId;
	
	@Column(name = "course_id")
	private int courseId;
	
	@Column(name = "question_list")
	private ArrayList<Question> questionList;
	
	@Column(name = "answer_list")
	private ArrayList<Integer> answerList;
		
	@Column(name = "student_instructions")
	private String studentInstructions;
	
	@Column(name = "teacher_instructions")
	private String teacherInstructions;
	
	@Column(name = "used")
	private boolean usedInExamination;
	
	@Column(name = "duration")
	private Duration assignedDuration;
	
	// teacher exams relation - Unidirectional
	@Column(name = "teacher_id")
	private int teacherId;
	
	

	public Exam(int teacherId, int courseId, ArrayList<Question> questionList, ArrayList<Integer> answerList,
			String studentInstructions,String teacherInstructions, Duration assignedDuration ) {
		this.teacherId = teacherId;
		this.courseId = courseId;
		this.questionList = questionList;
		this.answerList = answerList;
		this.studentInstructions = studentInstructions;
		this.teacherInstructions = teacherInstructions;
		this.assignedDuration = assignedDuration;
	}

	public Exam() {
		
	}
	
	public int getExamNum() {
		return examNum;
	}

	public int getExamId() {
		return examId;
	}
	
	/* call it afeter we persist the object */
	public void setExamId() {
		this.examId = courseId * 1000 + examNum;;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public ArrayList<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(ArrayList<Question> questionList) {
		this.questionList = questionList;
	}

	public ArrayList<Integer> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(ArrayList<Integer> answerList) {
		this.answerList = answerList;
	}

	public String getStudentInstructions() {
		return studentInstructions;
	}

	public void setStudentInstructions(String studentInstructions) {
		this.studentInstructions = studentInstructions;
	}

	public String getTeacherInstructions() {
		return teacherInstructions;
	}

	public void setTeacherInstructions(String teacherInstructions) {
		this.teacherInstructions = teacherInstructions;
	}

	public boolean isUsedInExamination() {
		return usedInExamination;
	}

	public void setUsedInExamination(boolean usedInExamination) {
		this.usedInExamination = usedInExamination;
	}


	public Duration getAssignedDuration() {
		return assignedDuration;
	}

	public void setAssignedDuration(Duration assignedDuration) {
		this.assignedDuration = assignedDuration;
	}
	
	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
}
