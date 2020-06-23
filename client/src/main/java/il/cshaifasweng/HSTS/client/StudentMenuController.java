package il.cshaifasweng.HSTS.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import il.cshaifasweng.HSTS.client.utilities.WordHandler;
import il.cshaifasweng.HSTS.entities.Carrier;
import il.cshaifasweng.HSTS.entities.Exam;
import il.cshaifasweng.HSTS.entities.Examination;
import il.cshaifasweng.HSTS.entities.Question;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;


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
    private AnchorPane manualExamAP;

    @FXML
    private Button downloadExamBtn;

    @FXML
    private Button submitBtn;

    @FXML
    private Label manualLB;
    
    @FXML
    private Label timeLeftLB;

    @FXML
    private Label timeTagLB;

    @FXML
    private Label hourLb;

    @FXML
    private Label mintLB;

    @FXML
    private Label scndLB;
    
    @FXML
    private Label nameLB;
    
    @FXML
    void createStudentExamPageBoundary(ActionEvent event) {
    	mainMenuAP.setVisible(false);
    	instAP.setVisible(true);

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
		studentExamsTV.getItems().clear();

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
    void submitExam(ActionEvent event) {
    	// TODO need to handle cases where user uploaded number of exams.
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word doc(*.docx)", "*.docx"));
		File chosenfile = fileChooser.showOpenDialog(null);
        // create a Label 
        if (chosenfile != null) { 
        	manualLB.setText("Exam submitted succesfully. Good luck! "); 
        } 
        
        else {
        	manualLB.setText("No directory was chosen. ");
        }
    }
    

    @FXML
    void downloadExam(ActionEvent event) {
    	Examination examination = studentExamsTV.getSelectionModel().getSelectedItem();
    	manualLB.setText(""); 

    	try {
    		XWPFDocument manualExam = WordHandler.CreateWordFile(examination);
    		FileChooser fileChooser = new FileChooser();
    		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word doc(*.docx)", "*.docx"));
    		fileChooser.setInitialFileName("TestSys-Exam.docx");
    		File chosenDir = fileChooser.showSaveDialog(null);
    		if (chosenDir == null) {
            	manualLB.setText("No directory was chosen. "); 
            	return;
    		}
    		// Download file
    		if(!chosenDir.getName().contains(".")) {	// Extension validation
    			chosenDir = new File(chosenDir.getAbsolutePath() + "docx");
    		}
    		
			FileOutputStream out = new FileOutputStream(chosenDir);
			manualExam.write(out);
			manualExam.close();
        	manualLB.setText("Exam downloaded. Good luck! "); 
			out.close();
			    		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Alert fileIsOpenAlert = new Alert(AlertType.ERROR);
			fileIsOpenAlert.setHeaderText("File is open. Close it and try again.");
			fileIsOpenAlert.showAndWait();
		}
//    	File chosenDir = dirChooser.showDialog(primaryStage);
		 catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		}
    }
    
    @FXML
    void activateExam(ActionEvent event) {
    	String execCode = execCodeTF.getText();
    	Examination examination = studentExamsTV.getSelectionModel().getSelectedItem();
    	
    	if (examination == null){	// Examination selected validation
			Alert fileIsOpenAlert = new Alert(AlertType.ERROR);
			fileIsOpenAlert.setHeaderText("Exam not selected.");
			fileIsOpenAlert.showAndWait();
			return;
    	}
    	
    	if (!execCode.equals(examination.getExecutionCode())) {		// Execution code validation
			Alert fileIsOpenAlert = new Alert(AlertType.ERROR);
			fileIsOpenAlert.setHeaderText("Wrong Execution code. Try again. ");
			fileIsOpenAlert.showAndWait();
    		return;
    	}

        manualLB.setAlignment(Pos.CENTER);
        // Timer
		Timeline animation = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
	              @Override public void handle(ActionEvent actionEvent) {
	            	  long elapsedTime = java.time.Duration.between(LocalTime.now(), examination.getExamEndTime()).toSeconds();
	            	  
	            	  if (elapsedTime <= 0) {	// Time is up
		                  submitBtn.setDisable(true);
		                  downloadExamBtn.setDisable(true);
		                  manualLB.setText("Exam time is over. "); 
	            	  }
	            	  else {	// Every 1 second
	            		  int hours = (int) (elapsedTime / 3600);
	            		  int minutes = (int) ((elapsedTime % 3600) / 60);
	            		  int seconds = (int) (elapsedTime % 60);

	            		  hourLb.setText(String.format("%02d", hours));
	            		  mintLB.setText(String.format("%02d", minutes));
	            		  scndLB.setText(String.format("%02d", seconds));
	            	  }

	              }
	          	}));

		animation.setCycleCount((int) (Timeline.INDEFINITE)); // Running times
		animation.play();

    	instAP.setVisible(false);
    	manualExamAP.setVisible(true);
    	
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {   	
    	nameLB.setText("Hello " + LoginController.userReceviedfullName + ".");
    	instCourseTC.setCellValueFactory(new PropertyValueFactory<Examination,Integer>("courseId"));       
        instTeacherTC.setCellValueFactory(new PropertyValueFactory<Examination,Integer>("teacherId"));     
        instDateTC.setCellValueFactory(new PropertyValueFactory<Examination,LocalDate>("examDate")); 	
        
    	for(String course: (LoginController.userReceviedCourses).keySet()) {
    		courseCB.getItems().add(course);
    	}
    	courseCB.getSelectionModel().selectFirst();
    }
    
    
    void loadExaminationDataToSetInstAP(List<Examination> examinationList) {
    	
    	studentExamsTV.getItems().addAll(examinationList);
    }

}
