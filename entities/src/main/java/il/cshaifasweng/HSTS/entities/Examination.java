package il.cshaifasweng.HSTS.entities;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="examination_table")
public class Examination implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int examination_id;		//primary key
	
	@Column
	private String executionCode;

	@Column
	private Duration actualDuration;
	
	@Column
	private LocalDate examDate;
	
	@Column
	private LocalTime examStartTime;
	
	@Column
	private LocalTime examEndTime;
	
	@Column
	private AddTimeRequest addTimeRequest;
	
	@Column
	private ExamType examType;	// move to connecting table? 
	
	@Column
	private int studentsStarted;
	
	@Column
	private int studentsFinished;
	
	@Column
	private int studentsNotFinished;
	
	// exam examination relation - Unidirectional
	@ManyToOne
	@JoinColumn(name = "exam_id")
	private Exam exam;

	// teacher exams relation - Unidirectional
	@Column(name = "teacher_id")
	private int teacherId;
	
	// course examination relation - Unidirectional
	@Column(name = "course_id")
	private int courseId;

	@OneToMany(mappedBy = "examination")
    private Set<ExaminationStudent> examineesList = new HashSet<ExaminationStudent>();

	public Examination(String execuationCode, int teacherId,ExamType examType,	LocalDate examDate, 
						LocalTime examStartTime, Exam exam) {
		this.executionCode = execuationCode;
		this.studentsStarted = 0;
		this.studentsFinished = 0;
		this.studentsNotFinished = 0;
		this.examineesList = new HashSet<ExaminationStudent>(); 
		this.teacherId = teacherId;
		this.examType = examType;
		this.addTimeRequest = null; 
		this.exam = exam;
		this.actualDuration = exam.getAssignedDuration();
		setExamDate(examDate);
		this.examStartTime = examStartTime;
		updateExamEndTime();
		this.courseId = exam.getCourseId();
	}
	
	public Examination() {
		
	}
	
	public int getCourseId() {
		return courseId;
	}
	
	// TODO - ask Liel if it's OK
	public void addStudent(ExaminationStudent examinationStudent) {
		examineesList.add(examinationStudent);
	}
	
	
	public void endExamination() {
		/* TODO - reach the connecting table and check:
		 * 		if student started the test
		 * 		if student ended by themselves
		 * 		if student was forced to end (only in computerized test)
		*/		
	}	
	
	public int getExamination_id() {
		return examination_id;
	}

	public Duration getActualDuration() {
		return actualDuration;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
		this.courseId = exam.getCourseId();
	}
	
	public void setDuration(Duration duration) {
		this.actualDuration = duration;
		updateExamEndTime();
	}
	
	public void addDuration(Duration addeDuration) {
		this.actualDuration.plus(addeDuration);
		
	}

	public AddTimeRequest getAddTimeRequest() {
		return addTimeRequest;
	}

	public void setAddTimeRequest(AddTimeRequest addTimeRequest) {
		this.addTimeRequest = new AddTimeRequest(addTimeRequest);
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

	public int getStudentsNotFinished() {
		return studentsNotFinished;
	}

	public void setStudentsNotFinished(int studentsNotFinished) {
		this.studentsNotFinished = studentsNotFinished;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getExecutionCode() {
		return executionCode;
	}

	public LocalDate getExamDate() {
		return examDate;
	}

	public void setExamDate(LocalDate examDate) {
		this.examDate = examDate.plusDays(1);
	}

	public LocalTime getExamStartTime() {
		return examStartTime;
	}

	public void setExamStartTime(LocalTime examStartTime) {
		this.examStartTime = examStartTime;
	}
	
	public LocalTime setEndTime() {
		return examEndTime;
	}
	
	public LocalTime getExamEndTime() {
		return examEndTime;
	}
	
	private void updateExamEndTime() {
		examEndTime = examStartTime.plus(actualDuration);
	}
	
	public void setExamEndTime(Duration actualDuration) {
		examEndTime = examStartTime.plus(actualDuration);
	}
	
	public Set<ExaminationStudent> getExamineesList() {
		return examineesList;
	}
	
	public void timeAddition(Duration duration) {
		this.examEndTime = examEndTime.plus(duration);
	}

	public void setExamineesList(Set<ExaminationStudent> examineesList) {
		this.examineesList = examineesList;
	}
	
	public void addStudentsStarted() {
		this.studentsStarted++;
	}

	public void addStudentsFinished() {
		this.studentsFinished++;
	}
	
	public void addStudentsNotFinished() {
		this.studentsNotFinished++;
	}
	
	
}


