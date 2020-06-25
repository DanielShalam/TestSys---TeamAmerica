package il.cshaifasweng.HSTS.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import il.cshaifasweng.HSTS.client.utilities.WordHandler;
import il.cshaifasweng.HSTS.entities.Carrier;
import il.cshaifasweng.HSTS.entities.*;
import il.cshaifasweng.HSTS.entities.Examination;
import il.cshaifasweng.HSTS.entities.ExaminationStatus;
import il.cshaifasweng.HSTS.entities.ExaminationStudent;
import il.cshaifasweng.HSTS.entities.Question;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import net.bytebuddy.description.annotation.AnnotationDescription.Loadable;
import javafx.scene.control.TextArea;


public class StudentMenuController implements Initializable{
	
	private SimpleClient client;
	private Carrier localCarrier = null;
	private int questionIndex = 0;
	static Examination examination = null;
	private List<Question> qList;
	private Integer[] studentAnswers;	
	private boolean compExmnActivated = false;
	private boolean forcedToFinish = false;
    
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
    
    @FXML // fx:id="autoExamAP"
    private AnchorPane autoExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="question_num"
    private TextField question_num; // Value injected by FXMLLoader

    @FXML // fx:id="questionTA"
    private TextArea questionTA; // Value injected by FXMLLoader

    @FXML // fx:id="instructionTA"
    private TextArea instructionTA; // Value injected by FXMLLoader
    
    @FXML // fx:id="answerGroup"
    private ToggleGroup answerGroup; // Value injected by FXMLLoader
    
    @FXML // fx:id="answer1RB"
    private RadioButton answer1RB; // Value injected by FXMLLoader

    @FXML // fx:id="answer2RB"
    private RadioButton answer2RB; // Value injected by FXMLLoader

    @FXML // fx:id="answer3RB"
    private RadioButton answer3RB; // Value injected by FXMLLoader

    @FXML // fx:id="answer4RB"
    private RadioButton answer4RB; // Value injected by FXMLLoader

    @FXML // fx:id="answer1TF"
    private TextField answer1TF; // Value injected by FXMLLoader

    @FXML // fx:id="answer2TF"
    private TextField answer2TF; // Value injected by FXMLLoader

    @FXML // fx:id="answer3TF"
    private TextField answer3TF; // Value injected by FXMLLoader

    @FXML // fx:id="answer4TF"
    private TextField answer4TF; // Value injected by FXMLLoader

    @FXML // fx:id="timer"
    private TextField timer; // Value injected by FXMLLoader

    @FXML // fx:id="nextQuestion"
    private Button nextQuestion; // Value injected by FXMLLoader

    @FXML // fx:id="prevQuestiob"
    private Button prevQuestion; // Value injected by FXMLLoader

    @FXML // fx:id="startOrSubmitBtn"
    private Button startOrSubmitBtn; // Value injected by FXMLLoader
    
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
    private Label autoTimeLB;
    
    @FXML
    private Button autoRtrnBtn;

    @FXML
    private AnchorPane gradesAP;

    @FXML
    private TableView<ExaminationStudent> gradesTV;

    @FXML
    private TableColumn<ExaminationStudent, Integer> examCL;

    @FXML
    private TableColumn<ExaminationStudent, Integer> courseCl;

    @FXML
    private TableColumn<ExaminationStudent, Integer> gradeCL;

    @FXML
    private Button viewExamBtn;

    @FXML
    private Button gradeReturnBtn;

    @FXML
    private Label tStartLB;

    @FXML
    private Label tEndLB;

    @FXML
    private Label notesLB;

    @FXML
    private Button fullDeBtn;

    @FXML
    private TextArea notesTA;

    @FXML
    private Label tEndLB1;
    
    @FXML
    private Label courseLB1;

    @FXML
    void getFullDetails(ActionEvent event) {
    	ExaminationStudent eStudent = gradesTV.getSelectionModel().getSelectedItem();
    	if (eStudent == null) {
    		return;
    	}
    	else {
        	String course = extractCourseName(eStudent.getExamination().getCourseId());
        	courseLB1.setText("Course: " + course);
        	tStartLB.setText("Time Started: " + eStudent.getActualExamStartTime());
        	tEndLB.setText("Time Ended: " + eStudent.getActualExamEndTime());
        	notesLB.setText("Teacher notes: ");
        	notesTA.setVisible(true);
        	if(eStudent.getNotesToStudent().isBlank()) {
            	notesTA.setText("Teacher did not left any notes. ");
        	}
        	else {
            	notesTA.setText(eStudent.getNotesToStudent());
        	}
        	tEndLB.setText("Time Ended: " + eStudent.getActualExamEndTime());	
    	}
    }
    
    private String extractCourseName(int courseId) {
	    for (Entry<String, Integer> entry : LoginController.userReceviedCourses.entrySet()) {
	        if (courseId == entry.getValue()) {
	        	String courseName = entry.getKey();
	        	return courseName;
	        }
	    }
	    return null;
    }
    
    @FXML
    void getGrades(ActionEvent event) {
    	mainMenuAP.setVisible(false);
    	gradesAP.setVisible(true);
    	courseLB1.setText("");
    	tStartLB.setText("");
    	tEndLB.setText("");
    	notesLB.setText("");
    	notesTA.setVisible(false);
    	notesTA.setText("");
    	client = LoginController.client;
    	int studentId = LoginController.userReceviedID;
    	String msg = "get all teacher student examinations";
    	ExaminationStatus status = ExaminationStatus.FINALIZED;
    	Carrier localCarrier = client.handleMessageStudentExaminationsFromClientExamsController(msg, studentId, status, -1, null);
    	List<ExaminationStudent> studentList = (List<ExaminationStudent>) localCarrier.carrierMessageMap.get("studentExamination");
    	loadGradesTable(studentList);
    }
    
    @FXML
    void gradesReturn(ActionEvent event) {
    	mainMenuAP.setVisible(false);
    	gradesAP.setVisible(true);
    }
    
    @FXML
    void viewExam(ActionEvent event) {

    }
    
    
    private void loadGradesTable(List<ExaminationStudent> studentList) {
    	gradesTV.getItems().addAll(studentList);
    }

    
  /************  mainMenuAP *******************/
    @FXML
    void createStudentExamPageBoundary(ActionEvent event) {
    	mainMenuAP.setVisible(false);
    	instAP.setVisible(true);
    }
        
    
    @FXML
    void viewExams(ActionEvent event) {
    	
    }
  
   
    
    /************  instAP	***************/
    
    @FXML
    void viewCourseExaminations(ActionEvent event) {
		studentExamsTV.getItems().clear();

    	client = LoginController.client;
		int courseId = LoginController.userReceviedCourses.get(courseCB.getSelectionModel().getSelectedItem());

		localCarrier = client.handleMessageFromClientStudentController("get course examinations", courseId, null, null);

		Set<Examination> examinationsList = (Set<Examination>) localCarrier.carrierMessageMap.get("examinations");

		if (examinationsList.isEmpty()) {
			studentExamsTV.getItems().clear();
			Alert errorAlert = new Alert(AlertType.INFORMATION);
    		errorAlert.setHeaderText("No examinations are ready for this course. ");
    		errorAlert.showAndWait();
		}
		else {
			System.out.println("Loading");
			loadExaminationDataToSetInstAP(examinationsList);			
		}
    }
    
   
    public void loadExaminationDataToSetInstAP(Set<Examination> examinationList) {
    	
    	for(Examination examination: examinationList) {
    		if(LocalTime.now().isBefore(examination.getExamEndTime()) && 
    		   LocalTime.now().isAfter(examination.getExamStartTime()) &&
    		   LocalDate.now().equals(examination.getExamDate())	 ) {
    	    	studentExamsTV.getItems().add(examination);
    		}
    	}
    }
    
    
    @FXML
    void cancel(ActionEvent event) {
    	instAP.setVisible(false);
    	mainMenuAP.setVisible(true);
    }
    
    
    @FXML
    void activateExam(ActionEvent event) {
    	String execCode = execCodeTF.getText();
    	examination = studentExamsTV.getSelectionModel().getSelectedItem();
    	
    	if (examination == null){	// Examination selected validation
			Alert fileIsOpenAlert = new Alert(AlertType.ERROR);
			fileIsOpenAlert.setHeaderText("Exam not selected.");
			fileIsOpenAlert.showAndWait();
			return;
    	}
    	
    	if (LocalTime.now().isBefore(examination.getExamStartTime())) {
			Alert fileIsOpenAlert = new Alert(AlertType.ERROR);
			fileIsOpenAlert.setHeaderText("Exam starting at " + examination.getExamStartTime());
			fileIsOpenAlert.showAndWait();
			return;
    	}

    	if (execCode.equals(examination.getExecutionCode())) {
			
	    	switch (examination.getExamType()) {
	    	case MANUAL:
	    		ActivateManualExam();
	    		break;
	    		
	    	case COMPUTERIZED:
	    		activateCompExmn();
	    		break;
	    			
	    	default:
	    		System.out.println("ERROR: exam type not defined - please contact the assigning teacher");
	    	}
    	}
    	else
    	{
	    	Alert errorAlert = new Alert(AlertType.WARNING);
			errorAlert.setHeaderText("Wrong execution code. Please try again. ");
			errorAlert.showAndWait();
			execCodeTF.clear();
			return;
    	}
    }
    
    
    public void ActivateManualExam()  {
    	instAP.setVisible(false);
    	manualExamAP.setVisible(true);
        manualLB.setAlignment(Pos.CENTER);
        startManualExam();
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

	              }}
	          	));

		animation.setCycleCount((int) (Timeline.INDEFINITE)); // Running times
		animation.play();

    }
    
    
    public void activateCompExmn() {
    	
    	instAP.setVisible(false);
    	autoExamAP.setVisible(true);
    	submitBtn.setDisable(true);
    	startBtn.setDisable(false);
    	qList = new ArrayList<Question>(examination.getExam().getQuestionList());
    	studentAnswers = new Integer[qList.size()];
    	// init studentAnswers to 0
    	for (int i = 0; i < qList.size(); i++) {
    		studentAnswers[i]= 0; 
    	}
    	prevQuestion.setDisable(true);
    	answer1RB.setDisable(true);
    	answer2RB.setDisable(true);
    	answer3RB.setDisable(true);
    	answer4RB.setDisable(true);
    	showQuestion();
    	
        // Timer
		Timeline animation = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
	              @Override public void handle(ActionEvent actionEvent) {
	            	  long elapsedTime = java.time.Duration.between(LocalTime.now(), examination.getExamEndTime()).toSeconds();

	            	  if (elapsedTime <= 0) {	// Time is up
	            		  startOrSubmitBtn.setDisable(true);
	            		  forceSubmitCompExam();
	            	  }
	            	  else {	// Every 1 second
	            		  int hours = (int) (elapsedTime / 3600);
	            		  int minutes = (int) ((elapsedTime % 3600) / 60);
	            		  int seconds = (int) (elapsedTime % 60);

	            		  autoTimeLB.setText(String.format("%02d : %02d : %02d", hours, minutes, seconds));
	            	  }
	              }}
	          	));

		animation.setCycleCount((int) (Timeline.INDEFINITE)); // Running times
		animation.play();
    }
    
    void forceSubmitCompExam() {
		forcedToFinish = true;
		submitCompExam();
	}
    
    
    /*************  manualExamAP	****************/
    
    void startManualExam() {
    	// check if test already submitted
    	localCarrier = client.handleMessageFromClientStudentController("start student examination", 
				LoginController.userReceviedID,  examination, null);
		if (localCarrier.carrierMessageMap.get("exmnStudent") == null) {
			informationDialog("Attention",null,"Test already submitted");
			returnFromManualExam();
		}
    }
    
    @FXML
    void downloadExam(ActionEvent event) {
    	examination = studentExamsTV.getSelectionModel().getSelectedItem();
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
    void submitManualExam(ActionEvent event) throws IOException {	
    	
    	FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word doc(*.docx)", "*.docx"));
		File chosenfile = fileChooser.showOpenDialog(null);
        // create a Label 
        if (chosenfile != null) { 
        	
        	manualLB.setText("Exam submitted succesfully. Good luck! "); 
        	byte[] fileContent = Files.readAllBytes(chosenfile.toPath());
        	ExaminationStudent exmnStudent = (ExaminationStudent) localCarrier.carrierMessageMap.get("exmnStudent");
         	exmnStudent.setActualExamEndTime(LocalTime.now()); 	
         	exmnStudent.setExaminationStatus(ExaminationStatus.FINISHED);
         	exmnStudent.setSavedExamination(fileContent);
         	
         	localCarrier.carrierMessageMap.clear();
         	localCarrier.carrierMessageMap.put("message", "submit student examination");
         	localCarrier.carrierMessageMap.put("exmnStudent",exmnStudent);
         	
         	client.handleMessageFromClientStudentController("submit student examination", 
     				LoginController.userReceviedID,  examination, localCarrier);
         	
         	informationDialog(null,null,"Test submmited succesfully - good luck!");
         	returnFromManualExam(); 	
        
        } 
        
        else {
        	manualLB.setText("No directory was chosen. ");
        }
    }
    

    void returnFromManualExam() {
    	manualExamAP.setVisible(false);
	  	instAP.setVisible(true);
    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {   	
    	nameLB.setText("Hello " + LoginController.userReceviedfullName + ".");
    	instCourseTC.setCellValueFactory(new PropertyValueFactory<Examination,Integer>("courseId"));       
        instTeacherTC.setCellValueFactory(new PropertyValueFactory<Examination,Integer>("teacherId"));     
        instDateTC.setCellValueFactory(new PropertyValueFactory<Examination,LocalDate>("examDate")); 	
        courseCl.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getExamination().getCourseId()));
        gradeCL.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getGrade()));
        examCL.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getExamination().getExamination_id()));
        
    	for(String course: (LoginController.userReceviedCourses).keySet()) {
    		courseCB.getItems().add(course);
    	}
    	courseCB.getSelectionModel().selectFirst();

    }
    
    /*************  autoExamAP	*****************/
    

    
    @FXML		// press "start exam" button
    void startCompExam(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog("id here");
    	dialog.setTitle("ID required");
    	dialog.setContentText("Please enter your ID:");
    	Optional<String> input = dialog.showAndWait();
    	
    	if (input.isPresent()) {	// check user didn't exit the dialog window
    		
	    	String strInput = input.get();
	    	String id = Integer.toString(LoginController.userReceviedID);
	    	if (id.equals(strInput)) {
	    	    
	    		localCarrier = client.handleMessageFromClientStudentController("start student examination", 
	    				LoginController.userReceviedID,  examination, null);
	    		if (localCarrier.carrierMessageMap.get("exmnStudent") == null) {
	    			informationDialog("Attention",null,"Test already submitted");
	    			returnToInstAP();
	    		}
	    		startBtn.setDisable(true);
	    		submitBtn.setDisable(false);
	    		answer1RB.setDisable(false);
	        	answer2RB.setDisable(false);
	        	answer3RB.setDisable(false);
	        	answer4RB.setDisable(false);
	    		System.out.println("Id matches");
	    		compExmnActivated = true;
	    		
	    	}
	    	else {
	    		informationDialog("Attention",null,"wrong ID number");
	    	}
	    }
    }
    
    void informationDialog(String title, String header, String content) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle(title);
    	alert.setHeaderText(header);
    	alert.setContentText(content);

    	alert.showAndWait();
    }
    
    @FXML	// press "submit exam" button
    void submitCompExam() {
    	
    	// casting studentAnswers to ArrayList and saving to ExaminationStudent object
    	ArrayList<Integer> answersList = new ArrayList<Integer>();  
        Collections.addAll(answersList, studentAnswers); 
        
        ExaminationStudent exmnStudent = (ExaminationStudent) localCarrier.carrierMessageMap.get("exmnStudent");
    	exmnStudent.setActualExamEndTime(LocalTime.now()); 	
    	exmnStudent.setForcedToFinish(forcedToFinish);
    	exmnStudent.setExaminationStatus(ExaminationStatus.FINISHED);
    	exmnStudent.setStudentsAnswers(answersList);
    	
    	localCarrier.carrierMessageMap.clear();
    	localCarrier.carrierMessageMap.put("message", "submit student examination");
    	localCarrier.carrierMessageMap.put("exmnStudent",exmnStudent);
    	
    	client.handleMessageFromClientStudentController("submit student examination", 
				LoginController.userReceviedID,  examination, localCarrier);
    	
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle(null);
    	alert.setHeaderText(null);
    	alert.setContentText("Test submmited succesfully - good luck!");
    	alert.showAndWait();
    	
    	// need to go back to main screen
    	returnToInstAP();
    	
    }
   
	
	void returnToInstAP() {
		
		autoExamAP.setVisible(false);
		instAP.setVisible(true);
	}


	@FXML
    void showPrevQuestion(ActionEvent event) {
    	questionIndex--;
    	if (questionIndex == 0) {
    		prevQuestion.setDisable(true);
    	}
    	nextQuestion.setDisable(false);
    	if (compExmnActivated) {
    		markAnswerBtn();
    	}
    	showQuestion();
    }
	
	
	 @FXML
    void showNextQuestion(ActionEvent event) {
    	questionIndex++;
    	if (questionIndex == qList.size()-1) {
    		nextQuestion.setDisable(true);
    	}
    	prevQuestion.setDisable(false);
    	if (compExmnActivated) {
    		markAnswerBtn();
    	}
    	showQuestion();
    }
	 
	 
	 void markAnswerBtn() {
    	switch (studentAnswers[questionIndex]) {
    	case 1:
    		System.out.println("check 2");
    		answer1RB.setSelected(true);
    		break;
    		
    	case 2:
    		answer2RB.setSelected(true);
    		break;
    		
    	case 3:
    		answer3RB.setSelected(true);
    		break;
    		
    	case 4:
    		answer4RB.setSelected(true);
    		break;
    		
    	default:
    		answer1RB.setSelected(false);
    		answer2RB.setSelected(false);
    		answer3RB.setSelected(false);
    		answer4RB.setSelected(false);
    	}
    } 
	 
	void showQuestion() {
    	int qNum = questionIndex + 1;
    	Question question = qList.get(questionIndex);
    	question_num.setText("Question "+qNum);
    	questionTA.setText(question.getQuestion());
    	instructionTA.setText(question.getInstructions());
    	answer1TF.setText(question.getAnswers()[0]);
    	answer2TF.setText(question.getAnswers()[1]);
    	answer3TF.setText(question.getAnswers()[2]);
    	answer4TF.setText(question.getAnswers()[3]);
    }
   
	@FXML
    void chooseAnswerOne(ActionEvent event) {
    	studentAnswers[questionIndex] = 1;	
    }

    @FXML
    void chooseAnswerTwo(ActionEvent event) {
    	studentAnswers[questionIndex] = 2;
    }
    
    @FXML
    void chooseAnswerThree(ActionEvent event) {
    	studentAnswers[questionIndex] = 3;
    }
    
    @FXML
    void chooseAnswerFour(ActionEvent event) {
    	studentAnswers[questionIndex] = 4;
    }
	
   
    /*********   OTHERS    ***************/ 
   
    // ????
    @FXML
    void returnMainAuto(ActionEvent event) {
    	autoExamAP.setVisible(false);
    	mainMenuAP.setVisible(true);
    }

	// after we press submit 
	// startBtn  -> startCompExam
	// submitBtn -> submitCompExam
   
}
