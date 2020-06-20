package il.cshaifasweng.HSTS.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ExaminationStudentPK implements Serializable{
	
   
	private static final long serialVersionUID = 1L;

	@Column(name = "examination_id")
    private int examinationId;

    @Column(name = "student_id")
    private int studentId;

	public int getExaminationId() {
		return examinationId;
	}

	public void setExaminationId(int examinationId) {
		this.examinationId = examinationId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public ExaminationStudentPK(int studentId, int examinationId){
		this.examinationId = examinationId;
		this.studentId = studentId;
	}
	
	public ExaminationStudentPK(){
		
	}
}
