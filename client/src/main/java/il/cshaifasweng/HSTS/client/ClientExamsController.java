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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Map.Entry;

import il.cshaifasweng.HSTS.entities.Carrier;
import il.cshaifasweng.HSTS.entities.Exam;
import il.cshaifasweng.HSTS.entities.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

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
    private ChoiceBox<?> courseComboBox; // Value injected by FXMLLoader

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

    
    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void clearFields(ActionEvent event) {

    }

    @FXML
    void commitQuestionToDB(ActionEvent event) {

    }

    @FXML
    void createExam(ActionEvent event) {

    }

    @FXML
    void deleteExam(ActionEvent event) {

    }

    @FXML
    void editExam(ActionEvent event) {

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
    void getCourseExamsInst(ActionEvent event) {
    	client = LoginController.client;
//    	client.openConnection();
    	String message = "get all exams";
    	Exam exam = null;
    	
    	int id = 0;
    	if (courseExamInstigCB.getSelectionModel().getSelectedItem() != null) {
    		message = "get all course exams";
    		id = LoginController.userReceviedCourses.get(courseExamInstigCB.getSelectionModel().getSelectedItem());
    	}
    	
    	localCarrier = client.handleMessageFromClientExamController(message, id, exam);
    	System.out.println("message from ClientExamsController Handled");
		ObservableList<Exam> eItems = instigateExamsTV.getItems();
		
		if (!eItems.isEmpty()) {
			instigateExamsTV.getItems().removeAll(examsList);
		}

		examsList = (List<Exam>) localCarrier.carrierMessageMap.get("exams");
		
		loadInstigateData(examsList);
    }

    @FXML
    void getExamsByTeacherID(ActionEvent event) throws IOException {
    	client = LoginController.client;
//    	client.openConnection();
    	
    	String message = "get all teacher exams";
    	System.out.println(message);
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
    		System.out.println("No exam was selected!");
    	} else {
    		examInstigationAP.setVisible(false);
    		setExamsMenuAP.setVisible(true);
    		loadExamDataToSetExamAP(exam);
    	}
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
    	for(String course: (LoginController.userReceviedCourses).keySet()) {
    		courseExamInstigCB.getItems().add(course);
    	}
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
    		setExamsMenuAP.setVisible(true);
    		loadExamDataToSetExamAP(exam);
    		
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

    }
    
    @FXML
    void removeQuestionFromExam(ActionEvent event) {

    }
    
    @FXML
    void viewQuestion(ActionEvent event) {

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
    	scoreTCSetExamAP.setCellValueFactory(new PropertyValueFactory<Integer[],Integer>("valueOf"));
    	
    	/*scoreTCSetExamAP.setCellValueFactory(cellData -> {
            Integer score = cellData.getValue();
            return score;
        });*/
    	
    	for(String course: (LoginController.userReceviedCourses).keySet()) {
    		courseViewExamsCB.getItems().add(course);
    	}
    	
//    	for(String course: (LoginController.userReceviedCourses).keySet()) {
//    		courseComboBox.getItems().add(course);
//    	}
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
    void loadInstigateData(List<Exam> exam_list) {

        for (Exam examItem : exam_list)
        {
        	System.out.println(examItem.getExamId());
        	instigateExamsTV.getItems().addAll(examItem);
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
    	
		manageExamsAP.setVisible(false);
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
	    examDurationTFSetExamAP.setText(exam.getAssignedDuration().toString());
	    studentInstructionsTASetExamAP.setText(exam.getStudentInstructions());
	    teacherInstructionsTASetExamAP.setText(exam.getTeacherInstructions());
	    
	    Set<Question> questionList = exam.getQuestionList();
	    Integer[] scoringList = exam.getScoringList();
	    loadScoringData(scoringList, scoreTVSetExamAP);
	    loadQuestionData(questionList, examQuestionsTVsetExamAP);
	    
    }
    
    void loadScoringData(Integer[] scoringList, TableView TV) {
    	 for (Integer score : scoringList)
         {
         	TV.getItems().addAll(score);
         }
    }
    	
}
