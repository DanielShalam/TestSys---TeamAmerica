package il.cshaifasweng.HSTS.client;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import il.cshaifasweng.HSTS.entities.Carrier;
import il.cshaifasweng.HSTS.entities.Exam;
import il.cshaifasweng.HSTS.entities.Examination;
import il.cshaifasweng.HSTS.entities.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


public class StudentMenuController implements Initializable{
	
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

    @FXML	// fx:id="studentExamsTV"
    private TableView<Examination> studentExamsTV;

    @FXML
    private TableColumn<Examination,Integer> instCourseTC;
    
    @FXML
    private TableColumn<Examination,Integer> instTeacherTC;
    
    @FXML
    private TableColumn<Examination,LocalDate> instDateTC;

    @FXML
    private Button cancelButton;

    @FXML
    private ChoiceBox<String> courseCB;

    @FXML
    private Label courseLB;
    
    @FXML
    private Button viewExamsBtn;
    
    @FXML
    private Button startBtn;
    
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
		if (examinationsList.isEmpty()) {
			studentExamsTV.getItems().clear();
			Alert errorAlert = new Alert(AlertType.INFORMATION);
    		errorAlert.setHeaderText("No examinations are ready for this course. ");
    		errorAlert.showAndWait();
		}
		loadExaminationDataToSetInstAP(examinationsList);
    }

    @FXML
    void viewExams(ActionEvent event) {

    }
    
    @FXML
    void activateExam(ActionEvent event) {
    	String execCode = execCodeTF.getText();
    	Examination examination = studentExamsTV.getSelectionModel().getSelectedItem();
    	if (!execCode.equals(examination.getExecutionCode())) {
			Alert errorAlert = new Alert(AlertType.WARNING);
    		errorAlert.setHeaderText("Wrong execution code. Please try again. ");
    		errorAlert.showAndWait();
    	}
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {   	
    	instCourseTC.setCellValueFactory(new PropertyValueFactory<Examination,Integer>("courseId"));       
        instTeacherTC.setCellValueFactory(new PropertyValueFactory<Examination,Integer>("teacherId"));     
        instDateTC.setCellValueFactory(new PropertyValueFactory<Examination,LocalDate>("examDate")); 	
    }
    
    
    void loadExaminationDataToSetInstAP(List<Examination> examinationList) {
    	
    	studentExamsTV.getItems().addAll(examinationList);
    }

}
