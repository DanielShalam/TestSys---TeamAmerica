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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "subject_table")
public class Subject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int subjectId;
	
	@Column(name = "subject_name")
	private String subjectName;
	
	// subject course relation - Unidirectional
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subjectId") // mapping owner side
	private List<Course> courseList;

	public Subject(String subjectName) {
		this.subjectName = subjectName;
		courseList = new ArrayList<Course>(); 
	}
	
	public Subject() {
		
	}
	
	public int getSubjectId() {
		return subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public List<Course> getCourseList() {
		return courseList;
	}

	
	
	
	
}
