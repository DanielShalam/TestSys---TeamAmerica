/**
 * Sample Skeleton for 'TeacherMenu.fxml' Controller Class
 */

package il.cshaifasweng.HSTS.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TeacherMenuController {

    @FXML // fx:id="manageQuestionsButton"
    private Button manageQuestionsButton; // Value injected by FXMLLoader

    @FXML // fx:id="manageExamsButton"
    private Button manageExamsButton; // Value injected by FXMLLoader

    @FXML
    void createExamMenuBoudary(ActionEvent event) {
    	System.out.println("Exam menu");
    	//App.setRoot("ExamMenu");
    }

    @FXML
    void createQuestionMenuBoudary(ActionEvent event) {
    	System.out.println("Question menu");
    	//App.setRoot("QuestionMenu");
    }

}
