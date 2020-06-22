package il.cshaifasweng.HSTS.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;




@Entity
@Table(name="user_table")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int user_id;		//primary key
	
	@Column(name = "first_name")
	private String first_name;
	
	@Column(name = "last_name")
	private String last_name;
	
	@Column(name = "user_role")
	private Role role;
	
	@Column(name = "password")
	private String password;
	
	// teacher courses relation - Bidirectional (owning side)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL,mappedBy="teacher")	// mapping owner side
	private List<Course> coursesTeaching;
	
	// teacher questions relation - Unidirectional (owning side)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy="teacherId")	// mapping owner side
	private List<Question> questionsWritten;
	
	// teacher exams relation - Unidirectional (owning side)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy="teacherId")	// mapping owner side
	private List<Exam> examsWritten;
	
	// teacher examination relation - Unidirectional (owning side)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy="teacherId")	// mapping owner side
	private List<Examination> examinationInstigated;
	
	
	
	// student examinationStudent relation - Bidirectional
	@OneToMany(mappedBy = "student")		// mapping owner side
    private Set<ExaminationStudent> examinationList = new HashSet<ExaminationStudent>();
	
	// student courses relation 
		@ManyToMany(mappedBy = "studentList",	// owner side
				cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER,
				targetEntity = Course.class	)
		private List<Course> coursesStudying;
	
	public User(String firstName, String lastName, String password, Role role) {
		this.first_name = firstName;
		this.last_name = lastName;
		this.password = password;
		this.setRole(role);
		this.questionsWritten = new ArrayList<Question>();
		this.examsWritten = new ArrayList<Exam>();
		this.coursesTeaching = new ArrayList<Course>();
		this.coursesStudying = new ArrayList<Course>();
		this.examinationInstigated = new ArrayList<Examination>();
	}
	
	public User() {
	}
	
	public int getUserId() {
		return user_id;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String firstName) {
		this.first_name = firstName;
	}

	public String getLastname() {
		return last_name;
	}

	public void setLastname(String lastName) {
		this.last_name = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	/* adding a list of existing courses to student
	   and adding the student to each course */
	public void addCoursesToStudent(Course... courses) {
		for (Course course : courses) {
			coursesStudying.add(course);
			course.getStudentList().add(this); 
		}
	}
	
	public List<Course> getCoursesStudying(){
		return coursesStudying;
	}
	
	public List<Course> getCoursesTeaching(){
		return coursesTeaching;
	}
	
	public List<Exam> getExamsWritten(){
		return examsWritten;
	}
	
	public List<Question> getQuestionsWritten(){
		return questionsWritten;
	}
	
	public void addExamination(ExaminationStudent examinationStudent) {
		examinationList.add(examinationStudent);
	}
}	
