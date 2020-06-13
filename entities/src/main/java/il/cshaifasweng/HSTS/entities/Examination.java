package il.cshaifasweng.HSTS.entities;

import java.io.Serializable;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="examination_table")
public class Examination implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int examination_id;		//primary key
	
	@Column
	private Exam exam;
	
	@Column
	private Duration duration;
	
	@Column
	private AddTimeRequest addTimeRequest;
	
	@Column
	private ExamType examType;	// move to connecting table? 
	
	@Column
	private int studentsStarted;
	
	@Column
	private int studentsFinished;
	
	@Column
	private int studentsNotFinsished;
	
	@Column
	private int assigningTeacher;
	
	// student examination relation -  TODO use a connecting table as entity
	@ManyToMany
	private List<User> examinees;
	
	public Examination(int assigningTeacher,ExamType examType) {
		this.studentsStarted = 0;
		this.studentsFinished = 0;
		this.studentsNotFinsished = 0;
		this.examinees = new ArrayList<User>(); 
		this.assigningTeacher = assigningTeacher;
		this.examType = examType;
		this.addTimeRequest = null; 
	}
	
	public Examination() {
		
	}
	
	public int getExamination_id() {
		return examination_id;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public AddTimeRequest getAddTimeRequest() {
		return addTimeRequest;
	}

	public void setAddTimeRequest(AddTimeRequest addTimeRequest) {
		this.addTimeRequest = addTimeRequest;
	}

	public ExamType getExamType() {
		return examType;
	}

	public void setExamType(ExamType examType) {
		this.examType = examType;
	}

	public int getStudentsStarted() {
		return studentsStarted;
	}

	public void setStudentsStarted(int studentsStarted) {
		this.studentsStarted = studentsStarted;
	}

	public int getStudentsFinished() {
		return studentsFinished;
	}

	public void setStudentsFinished(int studentsFinished) {
		this.studentsFinished = studentsFinished;
	}

	public int getStudentsNotFinsished() {
		return studentsNotFinsished;
	}

	public void setStudentsNotFinsished(int studentsNotFinsished) {
		this.studentsNotFinsished = studentsNotFinsished;
	}

	public int getAssigningTeacher() {
		return assigningTeacher;
	}

	public void setAssigningTeacher(int assigningTeacher) {
		this.assigningTeacher = assigningTeacher;
	}

	public List<User> getExaminees() {
		return examinees;
	}

	public void setExaminees(List<User> examinees) {
		this.examinees = examinees;
	}

	
	
	
}


