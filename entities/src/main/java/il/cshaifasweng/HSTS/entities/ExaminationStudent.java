package il.cshaifasweng.HSTS.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;


@Entity
@Table(name = "examination_student_table")
public class ExaminationStudent implements Serializable {

   
	private static final long serialVersionUID = 1L;

	@EmbeddedId
    private ExaminationStudentPK examinationStudentId;

    @ManyToOne
    @MapsId("examinationId") 
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private User student;
    
    @Column(name = "actual_exam_start_time")
	private LocalTime actualExamStartTime;
	
	@Column(name = "actual_exam_end_time")
	private LocalTime actualExamEndTime;
    
	@Column(name = "examination_status")
	private ExaminationStatus examinationStatus;
	
	@Column(name = "grade")
	private int grade;
	
	@Column(name = "forced_to_finish")
	private boolean forcedToFinish;
	
	@Column(name = "notes_to_student")
	private String notesToStudent;
	
	@Column(name = "change_grade_notes")
	private String changeGradeNotes;
	
	@Column(name = "students_answers")
	private ArrayList<Integer> studentsAnswers;
	
	public ExaminationStudent(User student, Examination examination) {
		
		this.examinationStudentId = new ExaminationStudentPK(student.getUserId(),examination.getExamination_id());
		/* adding reference of of student and examination */
		this.examination = examination;
		this.student = student;
		this.grade = -1;
		this.forcedToFinish = false;
		this.notesToStudent = null;
		this.changeGradeNotes = null;
		this.studentsAnswers = new ArrayList<Integer>();
		this.examinationStatus = ExaminationStatus.STARTED;	
	}
	
	public ExaminationStudent() {
		
	}
	
	public List<Integer> getStudentsAnswers() {
		return studentsAnswers;
	}

	public void setStudentsAnswers(ArrayList<Integer> studentsAnswers) {
		this.studentsAnswers = studentsAnswers;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public boolean isForcedToFinish() {
		return forcedToFinish;
	}

	public void setForcedToFinish(boolean forcedToFinish) {
		this.forcedToFinish = forcedToFinish;
	}

	public String getNotesToStudent() {
		return notesToStudent;
	}

	public void setNotesToStudent(String notesToStudent) {
		this.notesToStudent = notesToStudent;
	}

	public String getChangeGradeNotes() {
		return changeGradeNotes;
	}

	public void setChangeGradeNotes(String changeGradeNotes) {
		this.changeGradeNotes = changeGradeNotes;
	}
	
	public ExaminationStudentPK getExaminationStudentId() {
		return examinationStudentId;
	}

	public Examination getExamination() {
		return examination;
	}

	public User getStudent() {
		return student;
	}

	public LocalTime getActualExamStartTime() {
		return actualExamStartTime;
	}

	public void setActualExamStartTime(LocalTime actualExamStartTime) {
		this.actualExamStartTime = actualExamStartTime;
	}

	public LocalTime getActualExamEndTime() {
		return actualExamEndTime;
	}

	public void setActualExamEndTime(LocalTime actualExamEndTime) {
		this.actualExamEndTime = actualExamEndTime;
	}

	public void changeStatus(ExaminationStatus newStatus) {
		this.examinationStatus = newStatus;
	}
	
	public ExaminationStatus getStatus() {
		return examinationStatus;
	}
	
	
}

