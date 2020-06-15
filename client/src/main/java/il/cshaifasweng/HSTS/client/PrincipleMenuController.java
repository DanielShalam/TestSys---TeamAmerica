/**
 * Sample Skeleton for 'PrincipleMenu.fxml' Controller Class
 */

package il.cshaifasweng.HSTS.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PrincipleMenuController {

    @FXML // fx:id="viewQuestionsButton"
    private Button viewQuestionsButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewExamsButton"
    private Button viewExamsButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewPerformedExamsButton"
    private Button viewPerformedExamsButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="showTimeAdditionRequestsButton"
    private Button showTimeAdditionRequestsButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewExamResultsTV"
    private TableView<?> viewExamResultsTV; // Value injected by FXMLLoader

    @FXML // fx:id="subjectTC"
    private TableColumn<?, ?> subjectTC; // Value injected by FXMLLoader

    @FXML // fx:id="courseTC"
    private TableColumn<?, ?> courseTC; // Value injected by FXMLLoader

    @FXML // fx:id="dateTC"
    private TableColumn<?, ?> dateTC; // Value injected by FXMLLoader

    @FXML // fx:id="gradeTC"
    private TableColumn<?, ?> gradeTC; // Value injected by FXMLLoader

    @FXML // fx:id="dateTC1"
    private TableColumn<?, ?> dateTC1; // Value injected by FXMLLoader

    @FXML // fx:id="dateTC11"
    private TableColumn<?, ?> dateTC11; // Value injected by FXMLLoader

    @FXML // fx:id="viewQuestionsTV"
    private TableView<?> viewQuestionsTV; // Value injected by FXMLLoader

    @FXML // fx:id="viewExamsTV"
    private TableView<?> viewExamsTV; // Value injected by FXMLLoader

    @FXML
    void getAllExamResults(ActionEvent event) {
    	//getAllExamResults(); TODO - implement in ? (didn't mention implementation in class diagram)
    	// TODO - implement loading of data from getAllExamResults to viewExamResultsTV
    	viewExamResultsTV.setVisible(true);
    	viewExamResultsTV.toFront();
    }

    @FXML
    void getAllExams(ActionEvent event) {
    	//getAll(); TODO - implement in Client Exam Controller
    	// TODO - implement loading of data from getAll to viewExamsTV
    	viewExamsTV.setVisible(true);
    	viewExamsTV.toFront();
    }

    @FXML
    void getAllQuestions(ActionEvent event) {
    	//getAll(); TODO - implement in Client Question Controller
    	// TODO - implement loading of data from getAll to viewQuestionsTV
    	viewQuestionsTV.setVisible(true);
    	viewQuestionsTV.toFront();
    }

    @FXML
    void getTimeRequests(ActionEvent event) {
    	//App.setRoot("PrincipleRequests");
    	System.out.println("Principle Requests");
    }

}
