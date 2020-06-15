
package il.cshaifasweng.HSTS.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;

public class StudentMenuController {

    @FXML // fx:id="startExamButton"
    private Button startExamButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewGradesButton"
    private Button viewGradesButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewPerformedExamsButton"
    private Button viewPerformedExamsButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewGradesTV"
    private TableView<?> viewGradesTV; // Value injected by FXMLLoader

    @FXML // fx:id="subjectTC"
    private TableColumn<?, ?> subjectTC; // Value injected by FXMLLoader

    @FXML // fx:id="courseTC"
    private TableColumn<?, ?> courseTC; // Value injected by FXMLLoader

    @FXML // fx:id="dateTC"
    private TableColumn<?, ?> dateTC; // Value injected by FXMLLoader

    @FXML // fx:id="gradeTC"
    private TableColumn<?, ?> gradeTC; // Value injected by FXMLLoader

    @FXML // fx:id="examCopyTV"
    private TableView<?> examCopyTV; // Value injected by FXMLLoader

    @FXML
    void createStudentExamPageBoundary(ActionEvent event) {
    	
    	//App.setRoot("StudentExamPage");
    }

    @FXML
    void getGrades(ActionEvent event) {
    	//getGradesByStudent(student_ID); TODO - implement in Client Data Controller
    	// TODO - implement loading of data from getGradesByStudent to viewGradesTV
    	viewGradesTV.setVisible(true);
    	viewGradesTV.toFront();
    }

    @FXML
    void viewExams(ActionEvent event) {
    	//getExamCopy(student_ID); TODO - implement in Client Data Controller
    	// TODO - implement loading of data from getExamCopy to examCopyTV
    	examCopyTV.setVisible(true);
    	examCopyTV.toFront();
    }
    
}
