package il.cshaifasweng.HSTS.client;

import java.util.List;
import java.util.Map.Entry;

import il.cshaifasweng.HSTS.entities.Carrier;
import il.cshaifasweng.HSTS.entities.Exam;
import il.cshaifasweng.HSTS.entities.Examination;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class StudentMenuController {
	
	private SimpleClient client;
	private Carrier localCarrier = null;
	
    @FXML
    private AnchorPane instAP;
    
    @FXML
    private GridPane mainMenuAP;

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
    private TableView<Examination> studentExamsTV;

    @FXML
    private TableColumn<?, ?> instCourseTC;
    
    @FXML
    private TableColumn<?, ?> instTeacherTC;
    
    @FXML
    private TableColumn<?, ?> instDateTC;

    @FXML
    private Button cancelButton;

    @FXML
    private ChoiceBox<String> courseCB;

    @FXML
    private Label courseLB;
    
    @FXML
    private Button viewExamsBtn;
    
    @FXML
    void createStudentExamPageBoundary(ActionEvent event) {
    	mainMenuAP.setVisible(false);
    	instAP.setVisible(true);
    	
    	for(String course: (LoginController.userReceviedCourses).keySet()) {
    		courseCB.getItems().add(course);
    	}
    	courseCB.getSelectionModel().selectFirst();
    }

    @FXML
    void getGrades(ActionEvent event) {

    }
    
    @FXML
    void cancel(ActionEvent event) {
    	instAP.setVisible(false);
    	mainMenuAP.setVisible(true);
    }
    
    @FXML
    void viewCourseExaminations(ActionEvent event) {
    	client = LoginController.client;
		int courseId = LoginController.userReceviedCourses.get(courseCB.getSelectionModel().getSelectedItem());
		localCarrier = client.handleMessageFromClientStudentController("get course examinations", courseId, null);
		List<Examination> examinationsList = (List<Examination>) localCarrier.carrierMessageMap.get("examinations");
		if (examinationsList == null) {
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("No examinations are ready for this course. ");
    		errorAlert.showAndWait();
		}
		loadExaminationDataToSetInstAP(examinationsList);
    }

    @FXML
    void viewExams(ActionEvent event) {

    }
    
    void loadExaminationDataToSetInstAP(List<Examination> examinationList) {
    	
        for (Examination examinationItem : examinationList)
        {
        	studentExamsTV.getItems().addAll(examinationItem);
        }
    }

}
