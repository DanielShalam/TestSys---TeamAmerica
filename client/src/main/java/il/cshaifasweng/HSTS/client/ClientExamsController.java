package il.cshaifasweng.HSTS.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Map.Entry;

import antlr.Utils;
import il.cshaifasweng.HSTS.entities.Carrier;
import il.cshaifasweng.HSTS.entities.Exam;
import il.cshaifasweng.HSTS.entities.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;

public class ClientExamsController implements Initializable{
	
	private SimpleClient client;
	private Carrier localCarrier = null;
	private List <Exam> examsList = null;
	
	ObservableList<Exam> examData = FXCollections.observableArrayList();
	
	
    @FXML // fx:id="setQuestionMenuAP"
    private AnchorPane setQuestionMenuAP; // Value injected by FXMLLoader

    @FXML // fx:id="saveButton"
    private Button saveButton; // Value injected by FXMLLoader

    @FXML // fx:id="editQuestionsButton1"
    private Button editQuestionsButton1; // Value injected by FXMLLoader

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
    private TableView<?> checkedExamsTV; // Value injected by FXMLLoader

    @FXML // fx:id="checkedExamsTC"
    private TableColumn<?, ?> checkedExamsTC; // Value injected by FXMLLoader

    @FXML // fx:id="checkedExamsDateTC"
    private TableColumn<?, ?> checkedExamsDateTC; // Value injected by FXMLLoader

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
    private ChoiceBox<?> courseExamInstigCB; // Value injected by FXMLLoader

    @FXML // fx:id="instigateExamsTV"
    private TableView<?> instigateExamsTV; // Value injected by FXMLLoader

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

    @FXML // fx:id="courseQuestionIdTVSetExamAP"
    private TableView<?> courseQuestionIdTVSetExamAP; // Value injected by FXMLLoader

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
    private ListView<Integer> scoreSetExamAP; // Value injected by FXMLLoader
    
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
    	saveButtonSetExamAP.setDisable(false);
		removeQuestionButtonSetExamAP.setDisable(false);
		
		// TODO - Handle updates of course question table view in case the selection of course 
		//choice box changes
    	
		setExamsMenuAP.setVisible(true);
    }

    @FXML
    void deleteExam(ActionEvent event) {

    }

    @FXML
    void editExam(ActionEvent event) {
    	Exam exam = viewExamsTV.getSelectionModel().getSelectedItem();
    	
    	if (exam == null)
    	{
    		System.out.println("No exam was selected!");
    	} else {
    		addQuestionsAPSetExamAP.setVisible(false);
    		manageExamsAP.setVisible(false);
    		
    		loadExamDataToSetExamAP(exam);
    		saveButtonSetExamAP.setDisable(false);
    		removeQuestionButtonSetExamAP.setDisable(false);
    		addQuestionsAPSetExamAP.setVisible(true);
    		
    		setExamsMenuAP.setVisible(true);
    		
    		
    	}
    }

    @FXML
    void getActiveExams(ActionEvent event) {

    }

    @FXML
    void getAutoCheckedExams(ActionEvent event) {

    }

    @FXML
    void getCourseExams(ActionEvent event) throws IOException {
    	client = LoginController.client;
//    	client.openConnection();
    	String message = "get all exams";
    	Exam exam = null;
    	
    	int id = 0;
    	if (courseViewExamsCB.getSelectionModel().getSelectedItem() != null) {
    		message = "get all course exams";
    		id = LoginController.userReceviedCourses.get(courseViewExamsCB.getSelectionModel().getSelectedItem());
    	}
    	
    	client.handleMessageFromClientExamController(message, id, exam);
    	System.out.println("message from ClientExamsController Handled");
		ObservableList<Exam> eItems = viewExamsTV.getItems();
		
		if (!eItems.isEmpty()) {
			viewExamsTV.getItems().removeAll(examsList);
		}
		
    	while (true) {
			System.out.println("Running");

    		if (client.isAnswerReturned==true) {
    			
    			localCarrier = client.answerCarrier;
    			examsList = (List<Exam>) localCarrier.carrierMessageMap.get("exams");
    			
    			loadData(examsList);
    			
    			client.isAnswerReturned=false;
    			break;
    		}	
    		
    	}
    }

    @FXML
    void getCourseExamsInst(ActionEvent event) {

    }

    @FXML
    void getExamsByTeacherID(ActionEvent event) throws IOException {
    	client = LoginController.client;
//    	client.openConnection();
    	
    	String message = "get all teacher exams";
    	System.out.println(message);
    	Exam exam = null;
    	int id = LoginController.userReceviedID;
    	
    	client.handleMessageFromClientExamController(message, id, exam);
    	System.out.println("message from ClientExamsController Handled");
		ObservableList<Exam> eItems = viewExamsTV.getItems();
		
		if (!eItems.isEmpty()) {
			viewExamsTV.getItems().removeAll(examsList);
		}
		
    	while (true) {
			System.out.println("Running");

    		if (client.isAnswerReturned==true) {
    			
    			localCarrier = client.answerCarrier;
    			examsList = (List<Exam>) localCarrier.carrierMessageMap.get("exams");
    			
    			loadData(examsList);
    			
    			client.isAnswerReturned=false;
    			break;
    		}	
    		
    	}
    }

    @FXML
    void getPerformedExams(ActionEvent event) {

    }

    @FXML
    void instigateExam(ActionEvent event) {

    }

    @FXML
    void requestAdditionTime(ActionEvent event) {

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
    	courseViewExamsCB.getSelectionModel().selectFirst();
    	viewCreateEditExamsAP.setVisible(true);
    }

    @FXML
    void viewExam(ActionEvent event) throws IOException {
    	Exam exam = viewExamsTV.getSelectionModel().getSelectedItem();
    	
    	if (exam == null)
    	{
    		System.out.println("No exam was selected!");
    	} else {
    		addQuestionsAPSetExamAP.setVisible(false);
    		manageExamsAP.setVisible(false);
    		
    		loadExamDataToSetExamAP(exam);
    		saveButtonSetExamAP.setDisable(true);
    		removeQuestionButtonSetExamAP.setDisable(true);
    		
    		setExamsMenuAP.setVisible(true);
    		
    		
    	}
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

    }
    
    @FXML
    void cancelSetExam(ActionEvent event) {
    	clearSetExam();
    	
		setExamsMenuAP.setVisible(false);
		addQuestionsAPSetExamAP.setVisible(true);
		manageExamsAP.setVisible(true);
    }
    
    @FXML
    void removeQuestionFromExam(ActionEvent event) {
    	Question question = examQuestionsTVsetExamAP.getSelectionModel().getSelectedItem();
    	
    	if (question != null) {
    		examQuestionsTVsetExamAP.getItems().remove(question);
    	}    	
    }
    
    @FXML
    void viewQuestion(ActionEvent event) {
    	Question question = examQuestionsTVsetExamAP.getSelectionModel().getSelectedItem();
    	loadQuestionData(question);  
    }
    
    @FXML
    void addQuestionToExam(ActionEvent event) {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	viewExamTC.setCellValueFactory(new PropertyValueFactory<Exam,Integer>("examId"));
    	examIDTC.setCellValueFactory(new PropertyValueFactory<Exam,Integer>("examId"));
    	examInstTC.setCellValueFactory(new PropertyValueFactory<Exam,String>("teacherInstructions"));
    	examQuestionIdTCSetExamAP.setCellValueFactory(new PropertyValueFactory<Question,Integer>("questionId"));
    	examQuestionTCSetExamAP.setCellValueFactory(new PropertyValueFactory<Question,String>("question"));
    	
    	for(String course: (LoginController.userReceviedCourses).keySet()) {
    		courseViewExamsCB.getItems().add(course);
    		courseCBSetExamAP.getItems().add(course);
    		courseComboBox.getItems().add(course);
    	}
    	
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
	    //examDurationTFSetExamAP.setText(exam.getAssignedDuration().toString());
	    examDurationTFSetExamAP.setText(dts(exam.getAssignedDuration())); 
	    
	    studentInstructionsTASetExamAP.setText(exam.getStudentInstructions());
	    teacherInstructionsTASetExamAP.setText(exam.getTeacherInstructions());
	    
	    Set<Question> questionList = exam.getQuestionList();
	    Integer[] scoringList = exam.getScoringList();
	    loadScoringData(scoringList, scoreTVSetExamAP);

	    loadQuestionData(questionList, examQuestionsTVsetExamAP);
	    
    }
    
    void clearSetExam() {
    	courseCBSetExamAP.getSelectionModel().clearSelection();
    	examIDSetExamAP.setText(null);
    	teacherIDSetExamAP.setText(null);
    	examDurationTFSetExamAP.setText(null);
    	studentInstructionsTASetExamAP.setText(null);
    	teacherInstructionsTASetExamAP.setText(null);
    	
    	ObservableList<Question> qItems = examQuestionsTVsetExamAP.getItems();
		
		if (!qItems.isEmpty()) {
			examQuestionsTVsetExamAP.getItems().clear();
		}
    	
    }
    
    void loadQuestionData(Question question) {
    	
    	if (question == null)
    	{
    		System.out.println("No question was selected!");
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
    
    public String dts(Duration duration) {
	    return duration.toString()
	            .substring(2)
	            .replaceAll("(\\d[HMS])(?!$)", "$1 ")
	            .toLowerCase();
	}
}
