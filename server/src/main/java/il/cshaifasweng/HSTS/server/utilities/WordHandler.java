package il.cshaifasweng.HSTS.server.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import il.cshaifasweng.HSTS.entities.Examination;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WordHandler implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static void CreateWordFile(Examination examination) throws IOException {
		String fileName = String.valueOf(examination.getExamId());
		//Blank Document
		XWPFDocument document = new XWPFDocument(); 
		XWPFParagraph tempParagraph = document.createParagraph();
		XWPFRun tempRun = tempParagraph.createRun();
		tempRun.setText("\tCourse id: "+examination.getExam().getCourseId()+"\n");
		tempRun.setText("\tDate: "+examination.getDuration()+"\n\n");
		tempRun.setText("\tInstractions: "+examination.getExam().getTeacherInstructions());
		//		writer.write("\tField: "+activeExam.getExam().getField().getName()+"\n");
		
//		List<Question> questionsInExam= examination.getExam().getQuestionList();
//		int questionIndex=1;
//		for(Question question:questionsInExam)//Sets all questions with their info on screen.
//		{
//			writer.write(questionIndex+". "+question.getQuestion()+" ("+examination.getExam().getScoringList()[questionIndex]+" Points)\n");
//			if(!question.getInstructions().equals(""))
//			{
//				writer.write("Note:" + question.getInstructions() +"\n");
//			}
//			questionIndex++;
//			for(int i=1;i<5;i++) {
//				writer.write("\t"+i+". "+question.getAnswers()[i]+"\n");
//			}
//		}
//		writer.write("\n\nGood Luck!");
//		writer.close();
//		
//
//
//		return new WordHandler(path);
		
        File Dir = new File(System.getProperty("user.home"), "Desktop");
        if (!Dir.exists()) {
        	Dir.mkdir();
        }
        
        FileOutputStream out = new FileOutputStream(new File(Dir.toString() + fileName));
        document.write(out);
        document.close();
        
        out.close();
        return;
	}
}
