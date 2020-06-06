package il.cshaifasweng.HSTS.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "question_table")
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int courseId;
	
	@Column(name = "course_name")
	private String courseName;
	
	
	@Column(name = "subject_id")
	private int subjectId;
	
	@Column(name = "teacher_id")
	private int teacherId;
	
	@Column(name = "stuednt_list")
	private List<User> studentList;
	
	public Course(String courseName,int subjectId,int teacherId,List<User> studentList) {
		this.courseName = courseName;
		this.subjectId = subjectId;
		this.teacherId = teacherId;
		this.studentList = studentList;
	}
	
	public Course() {
		
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

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public List<User> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<User> studentList) {
		this.studentList = studentList;
	}
	
}
