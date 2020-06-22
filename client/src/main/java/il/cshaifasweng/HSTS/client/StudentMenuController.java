package il.cshaifasweng.HSTS.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class StudentMenuController {

    @FXML
    private AnchorPane execTF;

    @FXML
    private Button startExamButton;

    @FXML
    private Button viewGradesButton;

    @FXML
    private Button viewPerformedExamsButton;

    @FXML
    private TableView<?> viewGradesTV;

    @FXML
    private TableColumn<?, ?> subjectTC;

    @FXML
    private TableColumn<?, ?> courseTC;

    @FXML
    private TableColumn<?, ?> dateTC;

    @FXML
    private TableColumn<?, ?> gradeTC;

    @FXML
    private TableView<?> examCopyTV;

    @FXML
    private TextField execCodeTF;

    @FXML
    private TableView<?> studentExamsTV;

    @FXML
    private TableColumn<?, ?> instCourseTC;
    
    @FXML
    private TableColumn<?, ?> instTeacherTC;
    
    @FXML
    private TableColumn<?, ?> instDateTC;

    @FXML
    void createStudentExamPageBoundary(ActionEvent event) {

    }

    @FXML
    void getGrades(ActionEvent event) {

    }

    @FXML
    void viewExams(ActionEvent event) {

    }

}
