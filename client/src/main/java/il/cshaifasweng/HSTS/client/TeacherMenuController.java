/**
 * Sample Skeleton for 'TeacherMenu.fxml' Controller Class
 */

package il.cshaifasweng.HSTS.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class TeacherMenuController {

    @FXML // fx:id="manageQuestionsButton"
    private Button manageQuestionsButton; // Value injected by FXMLLoader

    @FXML // fx:id="manageExamsButton"
    private Button manageExamsButton; // Value injected by FXMLLoader

    @FXML
    void createExamMenuBoudary(ActionEvent event) {
        try {
        	System.out.println("exam menu");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ExamMenu.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setMaximized(true);
            stage.setTitle("Manage Exams");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    	
    }

    @FXML
    void createQuestionMenuBoudary(ActionEvent event) {
        try {
        	System.out.println("question menu");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("QuestionMenu.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setMaximized(true);
            stage.setTitle("Manage Questions");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
