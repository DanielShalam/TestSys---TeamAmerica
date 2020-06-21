package il.cshaifasweng.HSTS.entities;

import java.io.Serializable;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



// TODO - use singleton 

@Entity
@Table(name = "course_table")
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int courseId;

	@Column(name = "course_name")
	private String courseName;

	// subject course  relation - Unidirectional
	@Column(name = "subject_id")
	private int subjectId;
	
	// owning side
	@ManyToMany( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = User.class ) 
	@JoinTable( name="courses_students", joinColumns = @JoinColumn(name ="course_id"),
				inverseJoinColumns = @JoinColumn(name = "student_id") ) 
	private List<User> studentList;
	 
	// teacher courses relation - Bidirectional
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "teacher_id")
	private User teacher;

	// course questions relation - Unidirectional
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "courseId") // mapping owner side
	private List<Question> questionList;

	public Course(String courseName, Subject subject, User teacher) {
		this.courseName = courseName;
		this.subjectId = subject.getSubjectId();
		setTeacher(teacher);
		this.studentList = new ArrayList<User>();
		this.questionList = new ArrayList<Question>();		
	}

	public Course() {

	}
	
	/* add teacher to course 
	   add course to teachers course list */
	public void setTeacher(User teacher) {
		this.teacher = teacher;
		teacher.getCoursesTeaching().add(this);		
	}
	
	public List<Question>  getQuestionList(){
		return questionList;
	}
	
	public int getTeacherId() {
		return teacher.getUserId();
	}
	
	
	public int getCourseId() {
		return courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public List<User> getStudentList() {
		return studentList;
	}


}
