package il.cshaifasweng.HSTS.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.Map.Entry;

import il.cshaifasweng.HSTS.entities.AddTimeRequest;
import il.cshaifasweng.HSTS.entities.Carrier;
import il.cshaifasweng.HSTS.entities.Exam;
import il.cshaifasweng.HSTS.entities.ExamType;
import il.cshaifasweng.HSTS.entities.Examination;
import il.cshaifasweng.HSTS.entities.ExaminationStudent;
import il.cshaifasweng.HSTS.entities.Question;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class ClientExamsController implements Initializable{
	
	static final int EXECUTION_CODE_LEN = 4;
	
	private SimpleClient client;
	private Carrier localCarrier = null;
	private List <Exam> examsList = null;
	private List <Examination> activeExamList = null;
	
	
	
	@FXML // fx:id="setQuestionMenuAP"
    private AnchorPane setQuestionMenuAP; // Value injected by FXMLLoader

    @FXML // fx:id="saveButton"
    private Button saveButton; // Value injected by FXMLLoader

    @FXML // fx:id="clearButton"
    private Button clearButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteQuestionsButton1"
    private Button deleteQuestionsButton1; // Value injected by FXMLLoader

    @FXML // fx:id="questionTA"
    private TextArea questionTA; // Value injected by FXMLLoader

    @FXML // fx:id="instructionsTA"
    private TextArea instructionsTA; // Value injected by FXMLLoader

    @FXML // fx:id="answer1TA"
    private TextArea answer1TA; // Value injected by FXMLLoader

    @FXML // fx:id="answer2TA"
    private TextArea answer2TA; // Value injected by FXMLLoader

    @FXML // fx:id="answer3TA"
    private TextArea answer3TA; // Value injected by FXMLLoader

    @FXML // fx:id="answer4TA"
    private TextArea answer4TA; // Value injected by FXMLLoader

    @FXML // fx:id="courseComboBox"
    private ChoiceBox<String> courseComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="answer1RB"
    private RadioButton answer1RB; // Value injected by FXMLLoader
    
    @FXML // fx:id="answerGroup"
    private ToggleGroup answerGroup; // Value injected by FXMLLoader

    @FXML // fx:id="answer2RB"
    private RadioButton answer2RB; // Value injected by FXMLLoader

    @FXML // fx:id="answer4RB"
    private RadioButton answer4RB; // Value injected by FXMLLoader

    @FXML // fx:id="answer3RB"
    private RadioButton answer3RB; // Value injected by FXMLLoader

    @FXML // fx:id="manageExamsAP"
    private AnchorPane manageExamsAP; // Value injected by FXMLLoader

    @FXML // fx:id="gradesAndStatisticsButton"
    private Button gradesAndStatisticsButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewCreateEditButton"
    private Button viewCreateEditButton; // Value injected by FXMLLoader

    @FXML // fx:id="examInstigationButton"
    private Button examInstigationButton; // Value injected by FXMLLoader

    @FXML // fx:id="gradeExamsAP"
    private AnchorPane gradeExamsAP; // Value injected by FXMLLoader

    @FXML // fx:id="shoeAutoCheckedExamsButton"
    private Button shoeAutoCheckedExamsButton; // Value injected by FXMLLoader

    @FXML // fx:id="checkExamButton"
    private Button checkExamButton; // Value injected by FXMLLoader

    @FXML // fx:id="checkedExamsTV"
    private TableView<ExaminationStudent> checkedExamsTV; // Value injected by FXMLLoader

    @FXML // fx:id="checkedExamsTC"
    private TableColumn<ExaminationStudent, Integer> checkedExamsTC; // Value injected by FXMLLoader

    @FXML // fx:id="checkedExamsDateTC"
    private TableColumn<ExaminationStudent, LocalDate> checkedExamsDateTC; // Value injected by FXMLLoader

    @FXML // fx:id="gradeExamsButton"
    private Button gradeExamsButton; // Value injected by FXMLLoader

    @FXML // fx:id="gradesStatisticsAP"
    private AnchorPane gradesStatisticsAP; // Value injected by FXMLLoader

    @FXML // fx:id="performedExamsTV"
    private TableView<?> performedExamsTV; // Value injected by FXMLLoader

    @FXML // fx:id="performedExamsTC"
    private TableColumn<?, ?> performedExamsTC; // Value injected by FXMLLoader

    @FXML // fx:id="performedExamsDateTC"
    private TableColumn<?, ?> performedExamsDateTC; // Value injected by FXMLLoader

    @FXML // fx:id="viewGradesButton"
    private Button viewGradesButton; // Value injected by FXMLLoader

    @FXML // fx:id="showPerformedExamsButton"
    private Button showPerformedExamsButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewCreateEditExamsAP"
    private AnchorPane viewCreateEditExamsAP; // Value injected by FXMLLoader

    @FXML // fx:id="courseViewExamsCB"
    private ChoiceBox<String> courseViewExamsCB; // Value injected by FXMLLoader

    @FXML // fx:id="showCourseExamsButton"
    private Button showCourseExamsButton; // Value injected by FXMLLoader

    @FXML // fx:id="addNewExamButton"
    private Button addNewExamButton; // Value injected by FXMLLoader

    @FXML // fx:id="editExamButton"
    private Button editExamButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteExamButton"
    private Button deleteExamButton; // Value injected by FXMLLoader

    @FXML // fx:id="showMyExamsButton"
    private Button showMyExamsButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewExamButton"
    private Button viewExamButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewExamsTV"
    private TableView<Exam> viewExamsTV; // Value injected by FXMLLoader

    @FXML // fx:id="viewExamTC"
    private TableColumn<Exam, Integer> viewExamTC; // Value injected by FXMLLoader

    @FXML // fx:id="examInstigationAP"
    private AnchorPane examInstigationAP; // Value injected by FXMLLoader

    @FXML // fx:id="instigateExamButton"
    private Button instigateExamButton; // Value injected by FXMLLoader

    @FXML // fx:id="requestTimeButton"
    private Button requestTimeButton; // Value injected by FXMLLoader

    @FXML // fx:id="courseExamInstigCB"
    private ChoiceBox<String> courseExamInstigCB; // Value injected by FXMLLoader

    @FXML // fx:id="instigateExamsTV"
    private TableView<Exam> instigateExamsTV; // Value injected by FXMLLoader

    @FXML // fx:id="examIDTC"
    private TableColumn<Exam, Integer> examIDTC; // Value injected by FXMLLoader

    @FXML // fx:id="examInstTC"
    private TableColumn<Exam, String> examInstTC; // Value injected by FXMLLoader

    @FXML // fx:id="showCourseExamsInstButton"
    private Button showCourseExamsInstButton; // Value injected by FXMLLoader

    @FXML // fx:id="showActiveExamsButton"
    private Button showActiveExamsButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="courseCBSetExamAP"
    private ChoiceBox<String> courseCBSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="studentInstructionsTASetExamAP"
    private TextArea studentInstructionsTASetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="teacherInstructionsTASetExamAP"
    private TextArea teacherInstructionsTASetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="examIDSetExamAP"
    private TextField examIDSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="examDurationTFSetExamAP"
    private TextField examDurationTFSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="teacherIDSetExamAP"
    private TextField teacherIDSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="examQuestionsTVsetExamAP"
    private TableView<Question> examQuestionsTVsetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="examQuestionIdTCSetExamAP"
    private TableColumn<Question, Integer> examQuestionIdTCSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="examQuestionTCSetExamAP"
    private TableColumn<Question, String> examQuestionTCSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="scoreTVSetExamAP"
    private TableView<Integer[]> scoreTVSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="scoreTCSetExamAP"
    private TableColumn<Integer[], Integer> scoreTCSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="addQuestionsAPSetExamAP"
    private AnchorPane addQuestionsAPSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="courseQuestionTVSetExamAP"
    private TableView<Question> courseQuestionTVSetExamAP; // Value injected by FXMLLoader
    
    @FXML // fx:id="courseQuestionIdTCSetExamAP"
    private TableColumn<Question, Integer> courseQuestionIdTCSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="courseQuestionTCSetExamAP"
    private TableColumn<Question, String> courseQuestionTCSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="addQuestionButtonSetExamAP"
    private Button addQuestionButtonSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="courseViewQuestionButtonSetExamAP"
    private Button courseViewQuestionButtonSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="removeQuestionButtonSetExamAP"
    private Button removeQuestionButtonSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="viewQuestionButtonSetExamAP"
    private Button viewQuestionButtonSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButtonSetExamAP"
    private Button cancelButtonSetExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="saveButtonSetExamAP"
    private Button saveButtonSetExamAP; // Value injected by FXMLLoader
    
    @FXML // fx:id="setExamsMenuAP"
    private AnchorPane setExamsMenuAP; // Value injected by FXMLLoader
    
    @FXML // fx:id="scoreSetExamAP"
    private ListView<String> scoreSetExamAP; // Value injected by FXMLLoader
    
    @FXML // fx:id="fillInExamDataButtonSetExamAP"
    private Button fillInExamDataButtonSetExamAP; // Value injected by FXMLLoader
    
    @FXML // fx:id="instigateExamAP"
    private AnchorPane instigateExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="examTypeCBInstExamAP"
    private ChoiceBox<String> examTypeCBInstExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="execCodeTFInstExamAP"
    private TextField execCodeTFInstExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="startTimeTFInstExamAP"
    private TextField startTimeTFInstExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="instigateButtonInstExamAP"
    private Button instigateButtonInstExamAP; // Value injected by FXMLLoader
    
    @FXML // fx:id="dateDPInstExamAP"
    private DatePicker dateDPInstExamAP; // Value injected by FXMLLoader
    
    @FXML // fx:id="orgDurationTF"
    private TextField orgDurationTF; // Value injected by FXMLLoader

    @FXML // fx:id="requestedExamDurationTF"
    private TextField requestedExamDurationTF; // Value injected by FXMLLoader

    @FXML // fx:id="reasonTimeAdditionTA"
    private TextArea reasonTimeAdditionTA; // Value injected by FXMLLoader

    @FXML // fx:id="submitTimeAdditionButton"
    private Button submitTimeAdditionButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancelTimeAdditionButton"
    private Button cancelTimeAdditionButton; // Value injected by FXMLLoader

    @FXML // fx:id="activeExamsTV"
    private TableView<Examination> activeExamsTV; // Value injected by FXMLLoader

    @FXML // fx:id="examinationIDTCTimeAddition"
    private TableColumn<Examination, Integer> examinationIDTCTimeAddition; // Value injected by FXMLLoader

    @FXML // fx:id="courseIDTCTimeAddition"
    private TableColumn<Examination, Integer> courseIDTCTimeAddition; // Value injected by FXMLLoader

    @FXML // fx:id="examTypeTCTimeAddition"
    private TableColumn<Examination, ExamType> examTypeTCTimeAddition; // Value injected by FXMLLoader

    @FXML // fx:id="dateTCTimeAddition"
    private TableColumn<Examination, LocalDate> dateTCTimeAddition; // Value injected by FXMLLoader

    @FXML // fx:id="startTimeTCTimeAddition"
    private TableColumn<Examination, LocalTime> startTimeTCTimeAddition; // Value injected by FXMLLoader

    @FXML // fx:id="endTimeTCTimeAddition"
    private TableColumn<Examination, LocalTime> endTimeTCTimeAddition; // Value injected by FXMLLoader
    
    @FXML // fx:id="timeAdditionRequestAP"
    private AnchorPane timeAdditionRequestAP; // Value injected by FXMLLoader
    
    @FXML // fx:id="viewExamTeacherInstructionsTC"
    private TableColumn<Exam, String> viewExamTeacherInstructionsTC; // Value injected by FXMLLoader
    
    @FXML
    void viewExam(ActionEvent event) throws IOException {
    	Exam exam = viewExamsTV.getSelectionModel().getSelectedItem();
    	
    	if (exam == null)
    	{
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Exam was not selected");
    		errorAlert.showAndWait();
    	} else {
    		manageExamsAP.setVisible(false);
    		saveButtonSetExamAP.setDisable(true);
    		setExamAPToViewOnly(exam);
    		setExamsMenuAP.setVisible(true);
    	}
    }
    
    @FXML
    void cancel(ActionEvent event) {
    	setQuestionMenuAP.setVisible(false);
    	setExamsMenuAP.setVisible(true);
    }

    @FXML
    void createExam(ActionEvent event) {
    	addQuestionsAPSetExamAP.setVisible(false);
		manageExamsAP.setVisible(false);
    	
    	addQuestionsAPSetExamAP.setVisible(true);
    	saveButtonSetExamAP.setDisable(true);
		removeQuestionButtonSetExamAP.setDisable(true);
		examDurationTFSetExamAP.setDisable(true);
		courseCBSetExamAP.setDisable(false);
		studentInstructionsTASetExamAP.setDisable(true);
		teacherInstructionsTASetExamAP.setDisable(true);
		fillInExamDataButtonSetExamAP.setVisible(true);
		courseViewQuestionButtonSetExamAP.setDisable(true);
		addQuestionButtonSetExamAP.setDisable(true);
		viewQuestionButtonSetExamAP.setDisable(true);
		examIDSetExamAP.setText("TBD");
		teacherIDSetExamAP.setText(LoginController.userReceviedID.toString());
		scoreSetExamAP.setEditable(true);
		
		setExamsMenuAP.setVisible(true);
    }

    @FXML
    void deleteExam(ActionEvent event) {
    	client = LoginController.client;
    	
    	Exam exam = viewExamsTV.getSelectionModel().getSelectedItem();
    	
    	if (exam == null) {
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Exam was not selected");
    		errorAlert.showAndWait();
    	} else {
    		if (exam.isUsedInExamination()) {
    			Alert errorAlert = new Alert(AlertType.ERROR);
        		errorAlert.setHeaderText("This exam cannot be delete since it was already performed");
        		errorAlert.showAndWait();
        	}
        	else {
        		String message = "delete exam";
            	int id = exam.getExamId();
            	
            	localCarrier = client.handleMessageFromClientExamController(message, id, exam);
            	
            	String status = (String) localCarrier.carrierMessageMap.get("status");
            	
//            	if (status == "Exam deleted successfully. ") {
//            		viewExamsTV.getItems().remove(exam);
//            	}
        	}
    	}
    	
    }

    @FXML
    void editExam(ActionEvent event) {
    	Exam exam = viewExamsTV.getSelectionModel().getSelectedItem();
    	
    	if (exam == null)
    	{
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Exam was not selected");
    		errorAlert.showAndWait();
    	} else {
    		addQuestionsAPSetExamAP.setVisible(false);
    		manageExamsAP.setVisible(false);
    		
    		loadExamDataToSetExamAP(exam);
    		
    		saveButtonSetExamAP.setDisable(false);
    		removeQuestionButtonSetExamAP.setDisable(false);
    		courseCBSetExamAP.setDisable(true);
    		studentInstructionsTASetExamAP.setDisable(false);
    		teacherInstructionsTASetExamAP.setDisable(false);
    		examDurationTFSetExamAP.setDisable(false);
    		courseViewQuestionButtonSetExamAP.setDisable(false);
    		addQuestionButtonSetExamAP.setDisable(false);
    		viewQuestionButtonSetExamAP.setDisable(false);
    		scoreSetExamAP.setDisable(false);
    		scoreSetExamAP.setEditable(true);
    		
    		Integer courseId = LoginController.userReceviedCourses.get(
					courseCBSetExamAP.getSelectionModel().getSelectedItem());
			getCourseQuestions(courseId);
    		
    		addQuestionsAPSetExamAP.setVisible(true);
    		setExamsMenuAP.setVisible(true);
    	}
    }
    
    @FXML
    void fillInExamData(ActionEvent event) {
    	
		if (!courseCBSetExamAP.getSelectionModel().isEmpty()) {
			courseCBSetExamAP.setDisable(true);
			fillInExamDataButtonSetExamAP.setDisable(true);
			
			saveButtonSetExamAP.setDisable(false);
			removeQuestionButtonSetExamAP.setDisable(false);
			studentInstructionsTASetExamAP.setDisable(false);
			teacherInstructionsTASetExamAP.setDisable(false);
			examDurationTFSetExamAP.setDisable(false);
			courseViewQuestionButtonSetExamAP.setDisable(false);
			addQuestionButtonSetExamAP.setDisable(false);
			viewQuestionButtonSetExamAP.setDisable(false);
			
			Integer courseId = LoginController.userReceviedCourses.get(
					courseCBSetExamAP.getSelectionModel().getSelectedItem());
			getCourseQuestions(courseId);
			
			addQuestionsAPSetExamAP.setVisible(true);
		} else {
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Course was not selected");
    		errorAlert.showAndWait();
		}
    }

    @FXML
    void getActiveExams(ActionEvent event) {
    	instigateExamsTV.setVisible(false);
    	instigateExamButton.setVisible(false);
    	activeExamsTV.setVisible(true);
    	requestTimeButton.setVisible(true);
    	
    	client = LoginController.client;

    	String message = "get teacher examinations";
    	int id = LoginController.userReceviedID;
    	Examination examination = null;
    
    	localCarrier = client.handleMessageFromClientExaminationController(message, id, examination);
    	System.out.println("message from ClientExaminationController Handled");
		
    	ObservableList<Examination> aItems = activeExamsTV.getItems();
		if (!aItems.isEmpty()) {
			activeExamsTV.getItems().removeAll(aItems);
		}

		List <Examination> activeExamList = (List<Examination>) localCarrier.carrierMessageMap.get("examinations");
		
		if (activeExamList != null) {
			loadActiveExamData(activeExamList);
		} else {
			Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("No active exams");
    		errorAlert.showAndWait();
		}
		
    	
    }

    void loadActiveExamData(List<Examination> activeExamList) {
		for (Examination activeExamination : activeExamList) {
			activeExamsTV.getItems().add(activeExamination);
		}
	}

	@FXML
    void getAutoCheckedExams(ActionEvent event) {
		//TODO
		client = LoginController.client;
//    	client.openConnection();
    	
    	String message = "get all teacher exams";
    	Exam exam = null;
    	int id = LoginController.userReceviedID;
    	
    	localCarrier = client.handleMessageFromClientExamController(message, id, exam);
    	System.out.println("message from ClientExamsController Handled");
		ObservableList<Exam> eItems = viewExamsTV.getItems();
		
		if (!eItems.isEmpty()) {
			viewExamsTV.getItems().removeAll(examsList);
		}

		examsList = (List<Exam>) localCarrier.carrierMessageMap.get("exams");
		
		loadData(examsList);
    }

    @FXML
    void getCourseExams(ActionEvent event) throws IOException {    	
    	examsList = getCourseExams(viewExamsTV,courseViewExamsCB);
    	
    	if (examsList != null) {
    		loadData(examsList);
    	}
				
    }

    @FXML
    void getCourseExamsInst(ActionEvent event) {
    	activeExamsTV.setVisible(false);
    	instigateExamsTV.setVisible(true);
    	instigateExamButton.setVisible(true);
    	requestTimeButton.setVisible(false);
    	
    	examsList = getCourseExams(instigateExamsTV,courseExamInstigCB);
    	
    	if (examsList != null) {
    		loadInstigateData(examsList);
    	}
    }

    @FXML
    void getExamsByTeacherID(ActionEvent event) throws IOException {
    	client = LoginController.client;
//    	client.openConnection();
    	
    	String message = "get all teacher exams";
    	Exam exam = null;
    	int id = LoginController.userReceviedID;
    	
    	localCarrier = client.handleMessageFromClientExamController(message, id, exam);
    	System.out.println("message from ClientExamsController Handled");
		ObservableList<Exam> eItems = viewExamsTV.getItems();
		
		if (!eItems.isEmpty()) {
			viewExamsTV.getItems().removeAll(examsList);
		}

		examsList = (List<Exam>) localCarrier.carrierMessageMap.get("exams");
		
		loadData(examsList);

    }

    @FXML
    void getPerformedExams(ActionEvent event) {

    }

    @FXML
    void instigateExam(ActionEvent event) {
    	Exam exam = instigateExamsTV.getSelectionModel().getSelectedItem();
    	
    	if (exam == null)
    	{
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Exam was not selected");
    		errorAlert.showAndWait();
    	} else {
    		manageExamsAP.setVisible(false);
    		setExamAPToViewOnly(exam);
    		instigateButtonInstExamAP.setVisible(true);
    		saveButtonSetExamAP.setVisible(false);
    		setExamsMenuAP.setVisible(true);
    		instigateExamAP.setVisible(true);
    	}
    }

    @FXML
    void requestAdditionTime(ActionEvent event) {
    	Examination activeExamination = activeExamsTV.getSelectionModel().getSelectedItem();
    	if (activeExamination != null) {
    		instigateExamButton.setVisible(false);
        	requestTimeButton.setVisible(false);
        	showActiveExamsButton.setDisable(true);
        	showCourseExamsInstButton.setDisable(true);
        	timeAdditionRequestAP.setVisible(true);
        	orgDurationTF.setText(dtm(activeExamination.getExam().getAssignedDuration()));
    	} else {
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Examination was not selected");
    		errorAlert.showAndWait();
    	}
    	
    	
    }

    @FXML
    void showCheckExam(ActionEvent event) {

    }

    @FXML
    void showExamInstigation(ActionEvent event) {
    	hideCurrentAP();
    	examInstigationAP.setVisible(true);
    }

    @FXML
    void showGradeExams(ActionEvent event) {
    	hideCurrentAP();
    	gradeExamsAP.setVisible(true);
    }

    @FXML
    void showGradesAndStatistis(ActionEvent event) {
    	hideCurrentAP();
    	gradesStatisticsAP.setVisible(true);
    }

    @FXML
    void showViewCreateEdit(ActionEvent event) {
    	hideCurrentAP();
    	//courseViewExamsCB.getSelectionModel().selectFirst();
    	viewCreateEditExamsAP.setVisible(true);
    }

    @FXML
    void viewGrades(ActionEvent event) {

    }

    void hideCurrentAP() {
    	if (viewCreateEditExamsAP.isVisible()) {
    		viewCreateEditExamsAP.setVisible(false);
    	}
    	else if (examInstigationAP.isVisible()) {
    		examInstigationAP.setVisible(false);
    	}
    	else if (gradeExamsAP.isVisible()) {
    		gradeExamsAP.setVisible(false);
    	}
    	else if (gradesStatisticsAP.isVisible()) {
    		gradesStatisticsAP.setVisible(false);
    	}
    }
    
    @FXML
    void saveExam(ActionEvent event) {
    	
    	if (isExamValid()) {
    		int teacherId = LoginController.userReceviedID;
    		int courseId = LoginController.userReceviedCourses.get(courseCBSetExamAP.getSelectionModel().getSelectedItem());
    		
    		List<Question> qList = examQuestionsTVsetExamAP.getItems();
			Set<Question> questionSet = new HashSet<>();;
			for (Question q : qList) {
				questionSet.add(q);
			}
			
			Integer[] scoringList = createScoringIntArr();
			String studentInstructions = studentInstructionsTASetExamAP.getText();
			String teacherInstructions = teacherInstructionsTASetExamAP.getText();
			Duration assignedDuration = Duration.ofMinutes(Integer.parseInt(examDurationTFSetExamAP.getText()));
			
			Exam exam = new Exam(teacherId, courseId, questionSet, scoringList, studentInstructions,
					teacherInstructions, assignedDuration);
			client = LoginController.client;
        	
        	String message = "create exam";   	
        	localCarrier = client.handleMessageFromClientExamController(message, 0, exam);
        	System.out.println("message from ClientExamController Handled");
        	
			String status = (String) localCarrier.carrierMessageMap.get("status");
			System.out.println(status);
			
    		clearSetExam();
    		
    		setExamsMenuAP.setVisible(false);
    		manageExamsAP.setVisible(true);
    	} else {
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Exam could not be saved");
    		errorAlert.setContentText("Make sure you filled in all fields or performed changes to the exam");
    		errorAlert.showAndWait();
    	}
    }
    
    @FXML
    void cancelSetExam(ActionEvent event) {
    	clearSetExam();
    	
    	setExamsMenuAP.setVisible(false);
    	fillInExamDataButtonSetExamAP.setDisable(false);
    	instigateButtonInstExamAP.setVisible(false);
    	instigateExamAP.setVisible(false);
    	saveButtonSetExamAP.setVisible(true);
    	addQuestionsAPSetExamAP.setVisible(true);
    	manageExamsAP.setVisible(true);
    }
    
    @FXML
    void removeQuestionFromExam(ActionEvent event) {
    	Question question = examQuestionsTVsetExamAP.getSelectionModel().getSelectedItem();
    	
    	if (question != null) {
    		int index = examQuestionsTVsetExamAP.getSelectionModel().getSelectedIndex();
    		examQuestionsTVsetExamAP.getItems().remove(question);
    		scoreSetExamAP.getItems().remove(index);
    	} else {
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Question was not selected");
    		errorAlert.showAndWait();
    	}

    }
    
    @FXML
    void viewQuestion(ActionEvent event) {
    	Question question = examQuestionsTVsetExamAP.getSelectionModel().getSelectedItem();
    	loadQuestionData(question);  
    }
    
    @FXML
    void viewCourseQuestion(ActionEvent event) {
    	Question question = courseQuestionTVSetExamAP.getSelectionModel().getSelectedItem();
    	loadQuestionData(question);  
    }
    
    @FXML
    void addQuestionToExam(ActionEvent event) {
    	Question question = courseQuestionTVSetExamAP.getSelectionModel().getSelectedItem();
    	
    	if (question != null) {
    		Boolean questionExists = false;
    		
    		List<Question> qList = examQuestionsTVsetExamAP.getItems();
        	for (Question q : qList) {
        		if (q.getQuestionId() == question.getQuestionId()) {
        			questionExists = true;
        			break;
        		}
        	}
        	if (!questionExists) {
        		examQuestionsTVsetExamAP.getItems().add(question);
        		scoreSetExamAP.getItems().add("0");
        	} else {
        		Alert errorAlert = new Alert(AlertType.ERROR);
        		errorAlert.setHeaderText("Question already exists");
        		//errorAlert.setContentText("Please select different question");
        		errorAlert.showAndWait();
        	}
    	} else {
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Question was not selected");
    		errorAlert.showAndWait();
    	}
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	viewExamTC.setCellValueFactory(new PropertyValueFactory<Exam,Integer>("examId"));
    	viewExamTeacherInstructionsTC.setCellValueFactory(new PropertyValueFactory<Exam,String>("teacherInstructions"));
    	examIDTC.setCellValueFactory(new PropertyValueFactory<Exam,Integer>("examId"));
    	examInstTC.setCellValueFactory(new PropertyValueFactory<Exam,String>("teacherInstructions"));
    	examQuestionIdTCSetExamAP.setCellValueFactory(new PropertyValueFactory<Question,Integer>("questionId"));
    	examQuestionTCSetExamAP.setCellValueFactory(new PropertyValueFactory<Question,String>("question"));
    	courseQuestionIdTCSetExamAP.setCellValueFactory(new PropertyValueFactory<Question,Integer>("questionId"));
    	courseQuestionTCSetExamAP.setCellValueFactory(new PropertyValueFactory<Question,String>("question"));
    	examinationIDTCTimeAddition.setCellValueFactory(new PropertyValueFactory<Examination, Integer>("Examination_id"));
    	courseIDTCTimeAddition.setCellValueFactory(new PropertyValueFactory<Examination, Integer>("courseId"));
    	examTypeTCTimeAddition.setCellValueFactory(new PropertyValueFactory<Examination, ExamType>("examType"));
    	dateTCTimeAddition.setCellValueFactory(new PropertyValueFactory<Examination, LocalDate>("examDate"));
    	startTimeTCTimeAddition.setCellValueFactory(new PropertyValueFactory<Examination, LocalTime>("examStartTime"));
    	endTimeTCTimeAddition.setCellValueFactory(new PropertyValueFactory<Examination, LocalTime>("examEndTime"));
    	checkedExamsTC.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getExamination().getExamination_id()));
    	checkedExamsDateTC.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getExamination().getExamDate()));
    	
    	scoreSetExamAP.setCellFactory(TextFieldListCell.forListView());
    	
    	for(String course: (LoginController.userReceviedCourses).keySet()) {
    		courseViewExamsCB.getItems().add(course);
    		courseCBSetExamAP.getItems().add(course);
    		courseComboBox.getItems().add(course);
    		courseExamInstigCB.getItems().add(course);
    	}
    	
    	UnaryOperator<Change> modifyChange = c -> {
    	    if (c.isContentChange()) {
    	        int newLength = c.getControlNewText().length();
    	        if (newLength > EXECUTION_CODE_LEN) {
    	            // replace the input text with the first EXECUTION_CODE_LEN chars
    	            String tail = c.getControlNewText().substring(0, EXECUTION_CODE_LEN);
    	            c.setText(tail);
    	            // replace the range to complete text
    	            // valid coordinates for range is in terms of old text
    	            int oldLength = c.getControlText().length();
    	            c.setRange(0, oldLength);
    	        }
    	    }
    	    if (c.getText().matches("[a-zA-Z0-9]*")) {
                return c;
            }
    	    return null;
    	};
    	UnaryOperator<Change> modifyDurationChange = c -> {
    	    if (c.getText().matches("[0-9]*")) {
                return c;
            }
    	    return null;
    	};
    	execCodeTFInstExamAP.setTextFormatter(new TextFormatter<Change>(modifyChange));
    	requestedExamDurationTF.setTextFormatter(new TextFormatter<Change>(modifyDurationChange));
    	examDurationTFSetExamAP.setTextFormatter(new TextFormatter<Change>(modifyDurationChange));
    	
    	TextFormatter<LocalTime> timeFieldFormatter =
    	        new TextFormatter<>(new LocalTimeStringConverter());
    	startTimeTFInstExamAP.setTextFormatter(timeFieldFormatter);
    	timeFieldFormatter.setValue(LocalTime.now());
    	
    }
    
    //Load data to table
    void loadData(List<Exam> exam_list) {

        for (Exam examItem : exam_list)
        {
        	System.out.println(examItem.getExamId());
        	viewExamsTV.getItems().addAll(examItem);
        }
    }
    
    //Load data to table
    void loadQuestionData(Set<Question> question_list, TableView<Question> TV) {

        for (Question questionItem : question_list)
        {
        	TV.getItems().addAll(questionItem);
        }
        
    }

    //Load data to table
    void loadInstigateData(List<Exam> exam_list) {

        for (Exam examItem : exam_list)
        {
        	instigateExamsTV.getItems().addAll(examItem);
        }
        
    }
    
    //Load data to table
    void loadQuestionData(List<Question> question_list, TableView<Question> TV) {

        for (Question questionItem : question_list)
        {
        	TV.getItems().addAll(questionItem);
        }
        
    }
   
    void loadExamDataToSetExamAP (Exam exam) {

		int courseId = exam.getCourseId();
		String courseName = null;
	    for (Entry<String, Integer> entry : LoginController.userReceviedCourses.entrySet()) {
	        if (courseId == entry.getValue()) {
	        	courseName = entry.getKey();
	        }
	    }
	    courseCBSetExamAP.getSelectionModel().select(courseName);
	    examIDSetExamAP.setText(String.valueOf(exam.getExamId()));
	    teacherIDSetExamAP.setText(String.valueOf(exam.getTeacherId()));
	    examDurationTFSetExamAP.setText(dtm(exam.getAssignedDuration()));

	    studentInstructionsTASetExamAP.setText(exam.getStudentInstructions());
	    teacherInstructionsTASetExamAP.setText(exam.getTeacherInstructions());
	    
	    Set<Question> questionList = exam.getQuestionList();
	    Integer[] scoringList = exam.getScoringList();
	    String[] scoringListString = new String[scoringList.length];
	    
	    int i;
	    for (i = 0; i < scoringList.length ; i++) {
	    	scoringListString[i] = String.valueOf(scoringList[i]);
	    }
	    
	    scoreSetExamAP.getItems().addAll(scoringListString);

	    loadQuestionData(questionList, examQuestionsTVsetExamAP);
	    
    }
    
    void clearSetExam() {
    	courseCBSetExamAP.getSelectionModel().clearSelection();
    	examTypeCBInstExamAP.getSelectionModel().clearSelection();
    	examIDSetExamAP.setText("");
    	teacherIDSetExamAP.setText("");
    	examDurationTFSetExamAP.setText("");
    	studentInstructionsTASetExamAP.setText("");
    	teacherInstructionsTASetExamAP.setText("");
    	execCodeTFInstExamAP.setText("");
    	startTimeTFInstExamAP.setText("");
    	
    	ObservableList<Question> qItems = examQuestionsTVsetExamAP.getItems();
		
		if (!qItems.isEmpty()) {
			examQuestionsTVsetExamAP.getItems().clear();
		}
    	
		qItems = courseQuestionTVSetExamAP.getItems();
		
		if (!qItems.isEmpty()) {
			courseQuestionTVSetExamAP.getItems().clear();
		}
		
		if (!scoreSetExamAP.getItems().isEmpty()) {
			scoreSetExamAP.getItems().clear();
		}
    }
    
    void loadQuestionData(Question question) {
    	
    	if (question == null)
    	{
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Question was not selected");
    		errorAlert.showAndWait();
    	}
    	else {
    		setExamsMenuAP.setVisible(false);
    	  	
    		questionTA.setText(question.getQuestion());
    		instructionsTA.setText(question.getInstructions());
    		answer1TA.setText(question.getAnswers()[0]);
    		answer2TA.setText(question.getAnswers()[1]);
    		answer3TA.setText(question.getAnswers()[2]);
    		answer4TA.setText(question.getAnswers()[3]);
    		
    		switch(question.getCorrectAnswer()) {
    		case 1:
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
    		}
    		
    		int courseId = question.getCourseId();
    		String courseName = null;
    	    for (Entry<String, Integer> entry : LoginController.userReceviedCourses.entrySet()) {
    	        if (courseId == entry.getValue()) {
    	        	courseName = entry.getKey();
    	        }
    	    }
    		courseComboBox.getSelectionModel().select(courseName);
    		setQuestionMenuAP.setVisible(true);
    	}
    }
    
    void getCourseQuestions (Integer courseId) {
    	List<Question> questionList = null;
    	client = LoginController.client;    	
    	String message = "get all course questions";
    	Question question = null;
    	int id = courseId;
    	
    	localCarrier = client.handleMessageFromClientQuestionController(message, id, question);
    	System.out.println("message from ClientQuestionController Handled");
		
		questionList = (List<Question>) localCarrier.carrierMessageMap.get("questions");
		
		loadQuestionData(questionList, courseQuestionTVSetExamAP);
    }
    
    Boolean isExamValid() {
    	if (!emptyFieldsExist()) {
    		if (!examExists()) {
    			return true;
    		} else {
    			return false;
    		}
    	} else {
    		return false;
    	}
    }
    
    Boolean emptyFieldsExist() {
    	if (examDurationTFSetExamAP.getText().isBlank() ||
    			studentInstructionsTASetExamAP.getText().isBlank() ||
    			teacherInstructionsTASetExamAP.getText().isBlank() ||
    			examQuestionsTVsetExamAP.getItems().isEmpty()) {
    		return true;
    	}
    	return false;
    }
    
    Boolean examExists() {
    	Exam exam = viewExamsTV.getSelectionModel().getSelectedItem();;
    	
		if (exam != null) {
			//compare original questions with current questions
			List<Question> qList = examQuestionsTVsetExamAP.getItems();
			Set<Question> qSet = new HashSet<>();;
			for (Question q : qList) {
				qSet.add(q);
			}
			Set<Question> originalQuestionSet = exam.getQuestionList();
			
			Boolean qSetsEqual = questionSetsEqual(qSet,originalQuestionSet);
			if (qSetsEqual) {
				qSetsEqual = questionSetsEqual(originalQuestionSet, qSet);
			}
			
			//check if exam was changed
    		if (examDurationTFSetExamAP.getText().equals(dtm(exam.getAssignedDuration())) &&
    				studentInstructionsTASetExamAP.getText().equals(exam.getStudentInstructions()) &&
    				teacherInstructionsTASetExamAP.getText().equals(exam.getTeacherInstructions()) && 
    				qSetsEqual && Arrays.equals(createScoringIntArr(), exam.getScoringList())) {
    			return true;
    		}
    		return false;
		}
		return false;
    }
    
    Boolean questionSetsEqual(Set<Question> qSet1, Set<Question> qSet2) {
    	
    	Boolean equals = false;
    	
    	for (Question q1 : qSet1) {
    		for (Question q2 : qSet2) {
    			if (q1.getQuestionId() == q2.getQuestionId()) {
    				equals = true;
    			}
    		}
    		if (!equals) { // q1 not found in qSet2
    			return false;
    		}
    		equals = false;
    	}
    	return true; // all qSet1 questions exist in qSet2
    }
    	
    public String dts(Duration duration) {
	    return duration.toString()
	            .substring(2)
	            .replaceAll("(\\d[HMS])(?!$)", "$1 ")
	            .toLowerCase();
	}

    public String dtm(Duration duration) {
    	return String.valueOf(duration.abs().toMinutes());
    }
    
    Integer[] createScoringIntArr() {
    	
    	int j = 0;
    	List<String> sList = scoreSetExamAP.getItems();
		Integer[] scoringList = new Integer[sList.size()];
		
		for (String score : sList) {
			scoringList[j] = Integer.parseInt(score);
			j++;
		}
		return scoringList;		
    }
    
    @FXML

    void instigate(ActionEvent event) {    	
		int teacherId = LoginController.userReceviedID;
		Exam exam = instigateExamsTV.getSelectionModel().getSelectedItem();
		LocalTime examStartTime = LocalTime.parse(startTimeTFInstExamAP.getText(), DateTimeFormatter.ofPattern("H[H]:MM"));
		
		String examTypeCB = examTypeCBInstExamAP.getSelectionModel().getSelectedItem();
		ExamType examType = null;
		switch (examTypeCB) {
			case "MANUAL":
				examType = ExamType.MANUAL;
				break;
			case "COMPUTERIZED":
				examType = ExamType.COMPUTERIZED;
				break;
		} 
		
    	if (isIntigationValid()) {
    		String executionCode = execCodeTFInstExamAP.getText();
    		LocalDate examDate = dateDPInstExamAP.getValue();
    		Examination examination = new Examination(executionCode, teacherId, examType, 
    				examDate, examStartTime, exam);		
    		
    		String message = "create examination";
    		int id = 0;
    		
    		localCarrier = client.handleMessageFromClientExaminationController(message, id, examination);
    		System.out.println("message from ClientExaminationController Handled");
        	
    		String status = (String) localCarrier.carrierMessageMap.get("status");
        	System.out.println(status);
        	
        	manageExamsAP.setVisible(true);
        	clearSetExam();
    		instigateButtonInstExamAP.setVisible(false);
    		saveButtonSetExamAP.setVisible(true);
    		setExamsMenuAP.setVisible(false);
    		instigateExamAP.setVisible(false);
        	
    	}
    }
    
    private boolean isIntigationValid() {
    	if (execCodeTFInstExamAP.getText() != "" && dateDPInstExamAP.getValue() != null) {
    		int executionCode = Integer.parseInt(execCodeTFInstExamAP.getText());
  
    		if (String.valueOf(executionCode).length() == 4) {
        		return true;
        	}
    		return false;
    	}
    	return false;
	}

	void setExamAPToViewOnly(Exam exam) {
		loadExamDataToSetExamAP(exam);
		removeQuestionButtonSetExamAP.setDisable(true);
		courseCBSetExamAP.setDisable(true);
		studentInstructionsTASetExamAP.setDisable(true);
		teacherInstructionsTASetExamAP.setDisable(true);
		examDurationTFSetExamAP.setDisable(true);
		viewQuestionButtonSetExamAP.setDisable(false);
		addQuestionsAPSetExamAP.setVisible(false);
		scoreSetExamAP.setEditable(false);
    }
	

    @FXML
    void submitTimeAdditionRequest(ActionEvent event) {
    	if (requestedExamDurationTF.getText().isBlank() ||
    			reasonTimeAdditionTA.getText().isBlank()) {
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Fill in all the fields and retry");
    		errorAlert.showAndWait();
    	} else {
    		int examinationId = activeExamsTV.getSelectionModel().getSelectedItem().getExamination_id();
    		Duration additionalExamDuration = Duration.ofMinutes(Integer.parseInt(requestedExamDurationTF.getText()));
    		
    		AddTimeRequest timeRequest = new AddTimeRequest(reasonTimeAdditionTA.getText(), 
    				additionalExamDuration, examinationId);
    		String message = "ask for time request";
    		
    		localCarrier = client.handleMessageFromClientTimeRequestController(message, timeRequest);
        	System.out.println("message from ClienttTimeRequestController Handled");
        	clearTimeAdditionRequestData();
    	}
    	showCourseExamsInstButton.setDisable(false);
    	showActiveExamsButton.setDisable(false);
    }
    
    @FXML
    void cancelTimeAdditionRequest(ActionEvent event) {
    	clearTimeAdditionRequestData();   	
    }
    
    void clearTimeAdditionRequestData() {
    	requestedExamDurationTF.setText("");
    	reasonTimeAdditionTA.setText("");
    	orgDurationTF.setText("");
    	timeAdditionRequestAP.setVisible(false);
    	
    	showCourseExamsInstButton.setDisable(false);
    	showActiveExamsButton.setDisable(false);
    	requestTimeButton.setVisible(true);
    }
    
    List <Exam> getCourseExams (TableView<Exam> TV, ChoiceBox<String> CB) {
    	client = LoginController.client;
    	
    	if (CB.getSelectionModel().getSelectedItem() != null) {
    		String message = "get all course exams";
    		int id = LoginController.userReceviedCourses.get(CB.getSelectionModel().getSelectedItem());
    		Exam exam = null;
    		
    		localCarrier = client.handleMessageFromClientExamController(message, id, exam);
        	System.out.println("message from ClientExamsController Handled");
    		ObservableList<Exam> eItems = TV.getItems();
    		
    		if (!eItems.isEmpty()) {
    			TV.getItems().removeAll(examsList);
    		}
    		
    		examsList = (List<Exam>) localCarrier.carrierMessageMap.get("exams");
    	} else {
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Course was not selected");
    		errorAlert.showAndWait();
    		examsList = null;
    	}
    	return examsList;
    }
}
