/**
 * Sample Skeleton for 'PrincipleMenu.fxml' Controller Class
 */

package il.cshaifasweng.HSTS.client;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import il.cshaifasweng.HSTS.entities.AddTimeRequest;
import il.cshaifasweng.HSTS.entities.Carrier;
import il.cshaifasweng.HSTS.entities.Exam;
import il.cshaifasweng.HSTS.entities.Examination;
import il.cshaifasweng.HSTS.entities.ExaminationStudent;
import il.cshaifasweng.HSTS.entities.Question;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class PrincipleMenuController implements Initializable {
	
	private SimpleClient client;
	private Carrier localCarrier = null;

    @FXML // fx:id="viewQuestionsAP"
    private AnchorPane viewQuestionsAP; // Value injected by FXMLLoader

    @FXML // fx:id="viewQuestionsTV"
    private TableView<Question> viewQuestionsTV; // Value injected by FXMLLoader

    @FXML // fx:id="courseIdTCqTV"
    private TableColumn<Question, Integer> courseIdTCqTV; // Value injected by FXMLLoader

    @FXML // fx:id="questionTCqTV"
    private TableColumn<Question, Integer> questionTCqTV; // Value injected by FXMLLoader

    @FXML // fx:id="answer1TCqTV"
    private TableColumn<Question, String> answer1TCqTV; // Value injected by FXMLLoader

    @FXML // fx:id="answer2TCqTV"
    private TableColumn<Question, String> answer2TCqTV; // Value injected by FXMLLoader

    @FXML // fx:id="answer3TCqTV"
    private TableColumn<Question, String> answer3TCqTV; // Value injected by FXMLLoader

    @FXML // fx:id="answer4TCqTV"
    private TableColumn<Question, String> answer4TCqTV; // Value injected by FXMLLoader

    @FXML // fx:id="correctAnswerTCqTV"
    private TableColumn<Question, Integer> correctAnswerTCqTV; // Value injected by FXMLLoader

    @FXML // fx:id="instructionsTCqTV"
    private TableColumn<Question, String> instructionsTCqTV; // Value injected by FXMLLoader

    @FXML // fx:id="teacherIDTCqTV"
    private TableColumn<Question, Integer> teacherIDTCqTV; // Value injected by FXMLLoader

    @FXML // fx:id="viewQuestionsButton"
    private Button viewQuestionsButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewExamsButton"
    private Button viewExamsButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewPerformedExamsButton"
    private Button viewPerformedExamsButton; // Value injected by FXMLLoader

    @FXML // fx:id="showTimeAdditionRequestsButton"
    private Button showTimeAdditionRequestsButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewExamResultsAP"
    private AnchorPane viewExamResultsAP; // Value injected by FXMLLoader

    @FXML // fx:id="viewExamResultsTV"
    private TableView<ExaminationStudent> viewExamResultsTV; // Value injected by FXMLLoader

    @FXML // fx:id="courseIdTCresAP"
    private TableColumn<ExaminationStudent, Integer> courseIdTCresAP; // Value injected by FXMLLoader

    @FXML // fx:id="examinationIdTCresAP"
    private TableColumn<ExaminationStudent, Integer> examinationIdTCresAP; // Value injected by FXMLLoader

    @FXML // fx:id="gradeTCresAP"
    private TableColumn<ExaminationStudent, Integer> gradeTCresAP; // Value injected by FXMLLoader

    @FXML // fx:id="dateTCresAP"
    private TableColumn<ExaminationStudent, LocalDate> dateTCresAP; // Value injected by FXMLLoader

    @FXML // fx:id="studentIdTCresAP"
    private TableColumn<ExaminationStudent, Integer> studentIdTCresAP; // Value injected by FXMLLoader

    @FXML // fx:id="teacherIdTCresAP"
    private TableColumn<ExaminationStudent, Integer> teacherIdTCresAP; // Value injected by FXMLLoader

    @FXML // fx:id="viewExamsAP"
    private AnchorPane viewExamsAP; // Value injected by FXMLLoader

    @FXML // fx:id="viewExamsTVeAP"
    private TableView<Exam> viewExamsTVeAP; // Value injected by FXMLLoader

    @FXML // fx:id="examIdTCeAP"
    private TableColumn<Exam, Integer> examIdTCeAP; // Value injected by FXMLLoader

    @FXML // fx:id="courseIdTCeAP"
    private TableColumn<Exam, Integer> courseIdTCeAP; // Value injected by FXMLLoader

    @FXML // fx:id="teacherInstructionsTCeAP"
    private TableColumn<Exam, String> teacherInstructionsTCeAP; // Value injected by FXMLLoader

    @FXML // fx:id="durationTCeAP"
    private TableColumn<Exam, Duration> durationTCeAP; // Value injected by FXMLLoader

    @FXML // fx:id="teacherIdTCeAP"
    private TableColumn<Exam, Integer> teacherIdTCeAP; // Value injected by FXMLLoader

    @FXML // fx:id="viewExamButton"
    private Button viewExamButton; // Value injected by FXMLLoader

    @FXML // fx:id="goBackButton"
    private Button goBackButton; // Value injected by FXMLLoader

    @FXML // fx:id="timeRequestTVtrAP"
    private TableView<AddTimeRequest> timeRequestTVtrAP; // Value injected by FXMLLoader

    @FXML // fx:id="examIdTCtrAP"
    private TableColumn<AddTimeRequest, Integer> examIdTCtrAP; // Value injected by FXMLLoader

    @FXML // fx:id="durationTCtrAP"
    private TableColumn<AddTimeRequest, Duration> durationTCtrAP; // Value injected by FXMLLoader

    @FXML // fx:id="reasonTCtrAP"
    private TableColumn<AddTimeRequest, String> reasonTCtrAP; // Value injected by FXMLLoader

    @FXML // fx:id="approveRequestButton"
    private Button approveRequestButton; // Value injected by FXMLLoader

    @FXML // fx:id="declineRequestButton"
    private Button declineRequestButton; // Value injected by FXMLLoader

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

    @FXML // fx:id="examQuestionIdTCViewExamAP"
    private TableColumn<Question, Integer> examQuestionIdTCViewExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="questionTCViewExamAP"
    private TableColumn<Question, String> questionTCViewExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="answer1TCViewExamAP"
    private TableColumn<Question, Integer> answer1TCViewExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="answer2TCViewExamAP"
    private TableColumn<Question, Integer> answer2TCViewExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="answer3TCViewExamAP"
    private TableColumn<Question, Integer> answer3TCViewExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="answer4TCViewExamAP"
    private TableColumn<Question, Integer> answer4TCViewExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="correctAnswerTCViewExamAP"
    private TableColumn<Question, Integer> correctAnswerTCViewExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="instructionsTCViewExamAP"
    private TableColumn<Question, String> instructionsTCViewExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="teacherIdTCViewExamAP"
    private TableColumn<Question, Integer> teacherIdTCViewExamAP; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButtonViewExamAP"
    private Button cancelButtonViewExamAP; // Value injected by FXMLLoader
    
    @FXML // fx:id="timeAdditionRequestsAP"
    private AnchorPane timeAdditionRequestsAP; // Value injected by FXMLLoader
   
    @FXML // fx:id="mainAP"
    private GridPane mainAP; // Value injected by FXMLLoader

    @FXML
    void cancelViewExam(ActionEvent event) {

    }

    @FXML
    void getAllExamResults(ActionEvent event) {

    }

    @FXML
    void getAllExams(ActionEvent event) {

    }

    @FXML
    void getAllQuestions(ActionEvent event) {
    	
    }

    @FXML
    void getTimeRequests(ActionEvent event) {
    	mainAP.setVisible(false);
    	timeAdditionRequestsAP.setVisible(true);
    	goBackButton.setVisible(true);
    	
    	client = LoginController.client;
    	
    	String message = "principle ask for requests";
    	AddTimeRequest timeRequest = null;
    	
    	localCarrier = client.handleMessageFromClientTimeRequestController(message, timeRequest);
    	System.out.println("message from ClientTimeRequestController Handled");
    	
    	ObservableList<AddTimeRequest> aItems = timeRequestTVtrAP.getItems();
		if (!aItems.isEmpty()) {
			timeRequestTVtrAP.getItems().removeAll(aItems);
		}

		List <AddTimeRequest> addTimeRequestList = (List<AddTimeRequest>) localCarrier.carrierMessageMap.get("requests");
		
		if (!addTimeRequestList.isEmpty()) {
			loadTimeRequestData(addTimeRequestList);
		} else {
			Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("No time requests");
    		errorAlert.showAndWait();
		}
    	
    }

    void loadTimeRequestData(List<AddTimeRequest> addTimeRequestList) {
    	for (AddTimeRequest timeRquest : addTimeRequestList) {
    		timeRequestTVtrAP.getItems().add(timeRquest);
    	}
	}

	@Override
    public void initialize(URL url, ResourceBundle rb) {
        //view Questions Table
        courseIdTCqTV.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getCourseId()));
        questionTCqTV.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getQuestionId()));
        answer1TCqTV.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getAnswers()[0]));
        answer2TCqTV.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getAnswers()[1]));
        answer3TCqTV.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getAnswers()[2]));
        answer4TCqTV.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getAnswers()[3]));
        correctAnswerTCqTV.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getCorrectAnswer()));
        instructionsTCqTV.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getInstructions()));
        teacherIDTCqTV.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getTeacherId()));
        
        //time requests table
        examIdTCtrAP.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getExamination_id()));
        durationTCtrAP.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getRequestedDuration()));
        reasonTCtrAP.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getRequestReason()));
            
    }
	
    @FXML
    void goToMainAP(ActionEvent event) {
    	viewQuestionsAP.setVisible(false);
    	viewExamResultsAP.setVisible(false);
    	viewExamsAP.setVisible(false);
    	timeAdditionRequestsAP.setVisible(false);
    	goBackButton.setVisible(false);
    	
    	mainAP.setVisible(true);
    }
    
    @FXML
    void approveTimeRequest(ActionEvent event) {
    	AddTimeRequest timeRequest = timeRequestTVtrAP.getSelectionModel().getSelectedItem();
    	
    	if (timeRequest != null) {
    		timeRequest.setApproved(true);
    		timeRequest.setReviewed(true);
    		sendResponseToServer(timeRequest);
    	} else {
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Time request was not selected");
    		errorAlert.showAndWait();
    	}
    }
    
    private void sendResponseToServer(AddTimeRequest timeRequest) {
    	client = LoginController.client;
    	
    	String message = "principle answer for requests";
    	
    	localCarrier = client.handleMessageFromClientTimeRequestController(message, timeRequest);
    	System.out.println("message from ClientTimeRequestController Handled");
		
    	timeRequestTVtrAP.getItems().remove(timeRequest);
	}

	@FXML
    void declineTimeRequest(ActionEvent event) {
    	AddTimeRequest timeRequest = timeRequestTVtrAP.getSelectionModel().getSelectedItem();
    	
    	if (timeRequest != null) {
    		timeRequest.setApproved(false);
    		timeRequest.setReviewed(true);
    		sendResponseToServer(timeRequest);
    	} else {
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Time request was not selected");
    		errorAlert.showAndWait();
    	}
    }
    
    
}
