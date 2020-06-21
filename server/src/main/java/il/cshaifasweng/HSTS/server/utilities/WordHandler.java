package il.cshaifasweng.HSTS.server.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

import il.cshaifasweng.HSTS.entities.Examination;
import il.cshaifasweng.HSTS.entities.Question;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.mysql.cj.xdevapi.Table;

public class WordHandler implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static void CreateWordFile(Examination examination) throws IOException {
//		String fileName = String.valueOf(examination.getExamId());
		String fileName = "Newfile.docx";
		//Blank Document
		XWPFDocument document = new XWPFDocument(); 
		XWPFParagraph labelParagraph = document.createParagraph();
		XWPFRun tempRun = labelParagraph.createRun();
		tempRun.setText("\tCourse id: "+examination.getExam().getCourseId());
		tempRun.addBreak();
		tempRun.setText("\tDate: "+examination.getDuration());
		tempRun.addBreak();
		tempRun.setText("\tInstructions: "+examination.getExam().getTeacherInstructions());
		tempRun.addBreak();
		tempRun.addBreak();
		tempRun.addBreak();

	    // Format as desired
		tempRun.setFontSize(14);
		tempRun.setFontFamily("Verdana");
		tempRun.setColor("4169E1");
		tempRun.setBold(true);  
		labelParagraph.setAlignment(ParagraphAlignment.RIGHT);
		Set<Question> questionsInExam= examination.getExam().getQuestionList();
		
		XWPFParagraph questionParagraph = document.createParagraph();
		XWPFRun questionRun = labelParagraph.createRun();
		
		int questionIndex=0;
		for(Question question:questionsInExam)//Sets all questions with their info on screen.
		{
			questionRun.setText(questionIndex+". "+question.getQuestion()+" ("+examination.getExam().getScoringList()[questionIndex]+" Points) ");
			questionRun.addCarriageReturn();
			questionRun.setText("");
			if(!question.getInstructions().equals(""))
			{
				questionRun.addTab();
				questionRun.setText("Instructions: " + question.getInstructions());
				questionRun.addCarriageReturn();
				questionRun.addCarriageReturn();
			}
			questionIndex++;
			for(int i=0;i<4;i++) {
				questionRun.addTab();
				questionRun.setText(4-i+". "+question.getAnswers()[i]);
			}
			questionRun.addCarriageReturn();
			questionRun.addCarriageReturn();

		}
		questionRun.setFontSize(13);
		questionRun.setFontFamily("David");
		questionParagraph.setAlignment(ParagraphAlignment.RIGHT);
		questionRun.setText("\n\nGood Luck!");		


		String folder = "C:/Exams/";
		File file = new File(folder);
		
        if (!file.exists()) {
            System.out.println("Creating folder. ");
        	file.mkdirs();
        }

        FileOutputStream out = new FileOutputStream(folder+fileName);
        document.write(out);
        document.close();
        System.out.println("Finish Writing document. ");
        out.close();
        return;
	}

}
