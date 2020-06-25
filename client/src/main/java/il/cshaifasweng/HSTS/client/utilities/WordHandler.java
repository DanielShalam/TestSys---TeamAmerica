package il.cshaifasweng.HSTS.client.utilities;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import il.cshaifasweng.HSTS.client.LoginController;
import il.cshaifasweng.HSTS.entities.Examination;
import il.cshaifasweng.HSTS.entities.Question;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;


public class WordHandler implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static XWPFDocument CreateWordFile(Examination examination) throws IOException {
		// Initialize data
		String courseName = null;
		int courseId = examination.getExam().getCourseId();
	    for (Entry<String, Integer> entry : LoginController.userReceviedCourses.entrySet()) {
	        if (courseId == entry.getValue()) {
	        	courseName = entry.getKey();
	        }
	    }
	    
		//Blank Document
		XWPFDocument document = new XWPFDocument(); 
		XWPFParagraph labelParagraph = document.createParagraph();
		XWPFRun tempRun = labelParagraph.createRun();
		tempRun.setText("Student name: " + LoginController.userReceviedfullName + ".");
		tempRun.addBreak();
		tempRun.setText("Course name: " + courseName + ".");
		tempRun.addBreak();
		tempRun.setText("Date: " + examination.getExamDate() + ".");
		tempRun.addBreak();
		tempRun.setText("Start time: " + examination.getExamStartTime() + ".");
		tempRun.addBreak();
		tempRun.setText("Duration: " + Utils.dts(examination.getActualDuration()) + ".");
		tempRun.addBreak();
		tempRun.setText("Instructions: " + examination.getExam().getTeacherInstructions() + ".");
		tempRun.addBreak();
		tempRun.addBreak();
		tempRun.addBreak();

	    // Format as desired
		tempRun.setFontSize(14);
		tempRun.setFontFamily("Ariel");
		tempRun.setColor("4169E1");
		tempRun.setBold(true); 
		tempRun.setUnderline(UnderlinePatterns.SINGLE);
		labelParagraph.setAlignment(ParagraphAlignment.RIGHT);
		List<Question> questionsInExam= examination.getExam().getQuestionList();
		
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
		questionRun.addCarriageReturn();
		questionRun.addTab();
		questionRun.setText("Good Luck!");		


//		String folder = "C:/Exams/";
//		File file = new File(folder);
//		
//        if (!file.exists()) {
//            System.out.println("Creating folder. ");
//        	file.mkdirs();
//        }
//
//        FileOutputStream out = new FileOutputStream(folder+fileName);
//        document.write(out);
//        document.close();
//        System.out.println("Finish Writing document. ");
//        out.close();
        return document;
	}
	
}
