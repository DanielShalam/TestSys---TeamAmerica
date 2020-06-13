package il.cshaifasweng.HSTS.entities;

import java.io.Serializable;

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
@Table(name = "question_table")
public class Question implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int question_num;
	
	// question_id is composed of course_id (2 digits) + question_num (3 digits)
	@Column(name = "question_id")
	private int question_id;
			
	@Column(name = "question")
	private String question;
	
	@Column(name = "instructions")
	private String instructions;
	
	@Column(name = "answers")
	private String[] answers = new String[4];
	
	@Column(name = "correct_answer")
	private int correct_answer;
	
	@Column(name = "used_in_test")
	private boolean used_in_test;
	
	// teacher question relation - Unidirectional
	@Column(name = "teacher_id")
	private int teacherId;
	
	// course question relation - Unidirectional
	@Column(name = "course_id")
	private int courseId;
	
	public Question(int courseId, String question, String[] answers, String instructions, 
					int correct_answer, int teacherId) {
		this.question = question;
		this.answers = answers;
		this.instructions = instructions;
		this.correct_answer = correct_answer;
		this.used_in_test = false;	
		this.teacherId = teacherId;
		this.courseId = courseId;
	}
	
	public Question() {
		
	}
	
	public int getCourseId() {
		return courseId;
	}
	
	public int getTeacherId() {
		return teacherId;
	}
	
	public int getQuestionNum() {
		return this.question_num;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getInstructions() {
		return this.instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String[] getAnswers() {
		return this.answers;
	}
	
	public void setAnswers(int i, String answer) {
		this.answers[i] = answer;
	}
	
	public void setAnswers(String[] answers) {
		this.answers = answers;
	}
	
	
	public int getCorrectAnswer() {
		return this.correct_answer;
	}

	public void setCorrectAnswer(int correct_answer) {
		this.correct_answer = correct_answer;
	}
	
	public boolean getUsedInTest() {
		return this.used_in_test;
	}

	public void setUsedInTest(boolean used_in_test) {
		this.used_in_test = used_in_test;
	}
	
	public void printQeustionNum() {
		System.out.println("Q num is: "+question_num);
	}
	
	// only done when object is persistence
	public void setQuestionId() {
		question_id = courseId *1000 + question_num;
	}
	
	public int getQuestionId() {
		return question_id;
	}
	
	
}
