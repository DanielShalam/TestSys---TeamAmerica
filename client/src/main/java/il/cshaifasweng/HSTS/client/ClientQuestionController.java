/**
 * Sample Skeleton for 'QuestionMenu.fxml' Controller Class
 */

package il.cshaifasweng.HSTS.client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import il.cshaifasweng.HSTS.entities.Carrier;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import il.cshaifasweng.HSTS.entities.Question;
import javafx.scene.control.cell.PropertyValueFactory;


public class ClientQuestionController implements Initializable {
	
	private SimpleClient client;
	private Carrier localCarrier = null;
	private List <Question> question_list = null;
	ObservableList<Question> questionData = FXCollections.observableArrayList();
	private int originalQuestionId = -1;
	
	
    @FXML // fx:id="addNewQuestionsButton"
    private Button addNewQuestionsButton; // Value injected by FXMLLoader

    @FXML // fx:id="editQuestionsButton"
    private Button editQuestionsButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteQuestionsButton"
    private Button deleteQuestionsButton; // Value injected by FXMLLoader

    @FXML // fx:id="showMyQeuestionsButton"
    private Button showMyQeuestionsButton; // Value injected by FXMLLoader

    @FXML // fx:id="showAllQuestionsButton"
    private Button showAllQuestionsButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="questionTV"
    private TableView<Question> questionTV; // Value injected by FXMLLoader

    @FXML // fx:id="questionTC"
    private TableColumn<Question, String> questionTC; // Value injected by FXMLLoader

    @FXML // fx:id="instructionsTC"
    private TableColumn<Question, String> instructionsTC; // Value injected by FXMLLoader
    
    @FXML // fx:id="answersTC"
    private TableColumn<Question, String[]> answersTC; // Value injected by FXMLLoader

    @FXML // fx:id="correctAnswerTC"
    private TableColumn<Question, Integer> correctAnswerTC; // Value injected by FXMLLoader

    @FXML // fx:id="teacherIdTC"
    private TableColumn<Question, Integer> teacherIdTC; // Value injected by FXMLLoader

    @FXML // fx:id="courseIdTC"
    private TableColumn<Question, Integer> courseIDTC; // Value injected by FXMLLoader
    
    @FXML // fx:id="questionIdTC"
    private TableColumn<Question, Integer> questionIdTC; // Value injected by FXMLLoader

    @FXML // fx:id="courseCB"
    private ChoiceBox<String> courseCB; // Value injected by FXMLLoader
    
    @FXML // fx:id="saveButton"
    private Button saveButton; // Value injected by FXMLLoader

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

    @FXML // fx:id="answer2RB"
    private RadioButton answer2RB; // Value injected by FXMLLoader

    @FXML // fx:id="answer4RB"
    private RadioButton answer4RB; // Value injected by FXMLLoader

    @FXML // fx:id="answer3RB"
    private RadioButton answer3RB; // Value injected by FXMLLoader  
    
    @FXML // fx:id="setQuestionMenuAP"
    private AnchorPane setQuestionMenuAP; // Value injected by FXMLLoader
    
    @FXML // fx:id="manageQuestionAP"
    private AnchorPane manageQuestionAP; // Value injected by FXMLLoader
    
    @FXML // fx:id="viewQuestionButton"
    private Button viewQuestionButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="clearButton"
    private Button clearButton; // Value injected by FXMLLoader
    
    @FXML
    void createSetQuestionBoudary(ActionEvent event) {
    	manageQuestionAP.setVisible(false);
    	setQuestionMenuAP.setVisible(true);
    	
    }

    @FXML
    void deleteQuestion(ActionEvent event) throws IOException {
    	client = LoginController.client;
    	client.openConnection();
    	
    	Question question = questionTV.getSelectionModel().getSelectedItem();
    	
    	if (question != null) {
			if (question.getUsedInTest()) {
				Alert errorAlert = new Alert(AlertType.ERROR);
	    		errorAlert.setHeaderText("Question can not be deleted since it is already used in test");
	    		errorAlert.showAndWait();
			} else {
				String message = "delete question";
				int id = question.getQuestionNum();

				client.handleMessageFromClientQuestionController(message, id, question);

			} 
		} else {
			Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Question was not selected");
    		errorAlert.showAndWait();
		}
    }

    @FXML
    void getAllQuestions(ActionEvent event) throws IOException {
    	client = LoginController.client;
    	client.openConnection();
    	
    	if (courseCB.getSelectionModel().getSelectedItem() != null) {
    		String message = "get all course questions";
    		int id = LoginController.userReceviedCourses.get(courseCB.getSelectionModel().getSelectedItem());
    		Question question = null;
    		
    		localCarrier = client.handleMessageFromClientQuestionController(message, id, question);
        	System.out.println("message from ClientQuestionController Handled");
    		ObservableList<Question> qItems = questionTV.getItems();
    		
    		if (!qItems.isEmpty()) {
    			questionTV.getItems().removeAll(question_list);
    		}
    		question_list = (List<Question>) localCarrier.carrierMessageMap.get("questions");
    		loadData(question_list);
    	} else {
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Course was not selected");
    		errorAlert.showAndWait();
    	}
    	
    	
    }

    @FXML
    void getQuestionsByTeacherID(ActionEvent event)  throws IOException {
    	client = LoginController.client;
    	client.openConnection();
    	
    	String message = "get all teacher questions";
    	System.out.println(message);
    	Question question = null;
    	int id = LoginController.userReceviedID;
    	
    	localCarrier = client.handleMessageFromClientQuestionController(message, id, question);
    	System.out.println("message from ClientQuestionController Handled");
		ObservableList<Question> qItems = questionTV.getItems();
		
		if (!qItems.isEmpty()) {
			questionTV.getItems().removeAll(question_list);
		}

		question_list = (List<Question>) localCarrier.carrierMessageMap.get("questions");
		
		loadData(question_list);	
    }

    @FXML
    void editQuestion(ActionEvent event) {
    	loadQuestionToSetQuestionBoudary();
    }
    
    void loadQuestionToSetQuestionBoudary() {
    	Question question = questionTV.getSelectionModel().getSelectedItem();
    	if (question == null)
    	{
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Question was not selected");
    		errorAlert.showAndWait();
    	}
    	else {
    		manageQuestionAP.setVisible(false);
            
    		if (!question.getUsedInTest()) {
            	originalQuestionId = question.getQuestionNum();
            	if (LoginController.userReceviedID == question.getTeacherId()) {
            		originalQuestionId = -1;
            	}
            }
    		
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
    
    @FXML
    void cancel(ActionEvent event) {
        disableSetQuestionMenu(false);
    	clearData();
    	originalQuestionId = -1;
    	setQuestionMenuAP.setVisible(false);
    	manageQuestionAP.setVisible(true);
    }

    @FXML
    void clearFields(ActionEvent event) {
    	clearData();
    }

    @FXML
    void commitQuestionToDB(ActionEvent event) {
    	if (isQuestionValid())
    	{
    		int courseId = LoginController.userReceviedCourses.get(courseComboBox.getSelectionModel().getSelectedItem());
    		int correct_answer = 0;
        	if (answer1RB.isSelected()) {
        		correct_answer = 1;
        	}
        	else if (answer2RB.isSelected()) {
        		correct_answer = 2;
        	}
        	else if (answer3RB.isSelected()) {
        		correct_answer = 3;
        	}
        	else if (answer4RB.isSelected()) {
        		correct_answer = 4;
        	}
    		String[] answers = {answer1TA.getText(), answer2TA.getText(), answer3TA.getText(), answer4TA.getText()};

    		Question question = new Question(courseId, questionTA.getText(), answers, instructionsTA.getText(), correct_answer, LoginController.userReceviedID);
    		
        	client = LoginController.client;
        	
        	String message = "create question";   	 
        	
        	localCarrier = client.handleMessageFromClientQuestionController(message, originalQuestionId, question);
        	System.out.println("message from ClientQuestionController Handled");
        	
			String status = (String) localCarrier.carrierMessageMap.get("status");
			System.out.println(status);
			
			originalQuestionId = -1;
        	setQuestionMenuAP.setVisible(false);
        	manageQuestionAP.setVisible(true);
    	} else {
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("Question could not be saved");
    		errorAlert.setContentText("Make sure you filled in all fields or performed changes to the question");
    		errorAlert.showAndWait();
    	}
    }
    
    @FXML
    boolean isQuestionValid() {
    	//check if question has empty fields
    	if (questionTA.getText().isBlank() || answer1TA.getText().isBlank() || answer2TA.getText().isBlank() ||
    			answer3TA.getText().isBlank() || answer4TA.getText().isBlank() || 
    			(answer1RB.isSelected() == false && answer2RB.isSelected() == false &&
    			answer3RB.isSelected() == false && answer4RB.isSelected() == false) ||
    			courseComboBox.getSelectionModel().isEmpty()) 
    	{
    		return false;
    	} 
    	else 
    	{
    		Question question = questionTV.getSelectionModel().getSelectedItem();
    		if (question != null) {
	    		//check if question was changed
	    		if (questionTA.getText() == question.getQuestion() &&
	    				answer1TA.getText() == question.getAnswers()[0] &&
	    				answer2TA.getText() == question.getAnswers()[1] &&
	    				answer3TA.getText() == question.getAnswers()[2] &&
	    				answer4TA.getText() == question.getAnswers()[3] &&
	    				LoginController.userReceviedID == question.getTeacherId() &&
	    				LoginController.userReceviedCourses.get(courseComboBox.getSelectionModel().getSelectedItem()) == question.getCourseId() &&
	    				((answer1RB.isSelected() && question.getCorrectAnswer() == 1) ||
	    						(answer2RB.isSelected() && question.getCorrectAnswer() == 2) ||
	    						(answer3RB.isSelected() && question.getCorrectAnswer() == 3) ||
	    						(answer4RB.isSelected() && question.getCorrectAnswer() == 4))) {
	    			return false;
	    		}
	    		return true;
    		}
    		return true;
    	}
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	courseIDTC.setCellValueFactory(new PropertyValueFactory<Question,Integer>("courseId"));
    	questionTC.setCellValueFactory(new PropertyValueFactory<Question,String>("question"));
    	instructionsTC.setCellValueFactory(new PropertyValueFactory<Question,String>("instructions"));
    	teacherIdTC.setCellValueFactory(new PropertyValueFactory<Question,Integer>("teacherId"));
    	questionIdTC.setCellValueFactory(new PropertyValueFactory<Question,Integer>("questionId"));
    	
    	for(String course: (LoginController.userReceviedCourses).keySet()) {
    		courseCB.getItems().add(course);
    	}
    	
    	for(String course: (LoginController.userReceviedCourses).keySet()) {
    		courseComboBox.getItems().add(course);
    	}
    }
    
    //Load data to table
    void loadData(List<Question> question_list) {

        for (Question questionItem : question_list)
        {
        	questionTV.getItems().addAll(questionItem);
        }
        
    }
    
    void clearData() {
    	questionTA.setText("");
    	instructionsTA.setText("");
    	answer1TA.setText("");
    	answer2TA.setText("");
    	answer3TA.setText("");
    	answer4TA.setText("");
    	courseComboBox.getSelectionModel().clearSelection();
    	courseComboBox.setValue(null);
    	if (answer1RB.isSelected()) {
    		answer1RB.setSelected(false);
    	}
    	else if (answer2RB.isSelected()) {
    		answer2RB.setSelected(false);
    	}
    	else if (answer3RB.isSelected()) {
    		answer3RB.setSelected(false);
    	}
    	else if (answer4RB.isSelected()) {
    		answer4RB.setSelected(false);
    	}
    }
    
    @FXML
    void viewQuestion(ActionEvent event) {
    	disableSetQuestionMenu(true);
        loadQuestionToSetQuestionBoudary();
    }
    
    void disableSetQuestionMenu (Boolean disable) {
    	saveButton.setDisable(disable);
        clearButton.setDisable(disable);
        questionTA.setDisable(disable);
        instructionsTA.setDisable(disable);
        answer1TA.setDisable(disable);
        answer2TA.setDisable(disable);
        answer3TA.setDisable(disable);
        answer4TA.setDisable(disable);
        courseComboBox.setDisable(disable);
        answer1RB.setDisable(disable);
        answer2RB.setDisable(disable);
        answer3RB.setDisable(disable);
        answer4RB.setDisable(disable);
    }
}
