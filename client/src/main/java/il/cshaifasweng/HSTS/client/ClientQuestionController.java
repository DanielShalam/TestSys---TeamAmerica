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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import il.cshaifasweng.HSTS.entities.Question;
import javafx.scene.control.cell.PropertyValueFactory;


public class ClientQuestionController implements Initializable {
	
	private SimpleClient client;
	private Carrier localCarrier = null;
	private List <Question> question_list = null;
	ObservableList<Question> questionData = FXCollections.observableArrayList();
	
	
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
    	if (question.getUsedInTest()) {
    		System.out.println("you CANNOT delete me!");
    	}
    	else {
    		String message = "delete question";
        	int id = question.getQuestionId();
        	
        	client.handleMessageFromClientQuestionController(message, id, question);
        	
        	
        	/*while (true) {
    			System.out.println("Running");

        		if (client.isAnswerReturned==true) {
        			System.out.println("you deleted me!");
        			
        			client.isAnswerReturned=false;
        			break;
        		}	
        		
        	}*/
    	}
    }

    @FXML
    void getAllQuestions(ActionEvent event) throws IOException {
    	client = LoginController.client;
    	client.openConnection();
    	
    	String message = "get all questions";
    	Question question = null;
    	int id = 0;
    	if (courseCB.getSelectionModel().getSelectedItem() != null) {
    		message = "get all course questions";
    		id = LoginController.userReceviedCourses.get(courseCB.getSelectionModel().getSelectedItem());
    	}
    	
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
    void loadQuestionToSetQuestionBoudary(ActionEvent event) {
    	Question question = questionTV.getSelectionModel().getSelectedItem();
    	if (question == null)
    	{
    		System.out.println("No question was selected!");
    	}
    	else {
    		manageQuestionAP.setVisible(false);
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
    	clearData();
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
        	localCarrier = client.handleMessageFromClientQuestionController(message, 0, question);
        	System.out.println("message from ClientQuestionController Handled");
        	
			String status = (String) localCarrier.carrierMessageMap.get("status");
			System.out.println(status);
        	setQuestionMenuAP.setVisible(false);
        	manageQuestionAP.setVisible(true);
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
    	answersTC.setCellValueFactory(new PropertyValueFactory<Question,String[]>("answers"));
    	instructionsTC.setCellValueFactory(new PropertyValueFactory<Question,String>("instructions"));
    	correctAnswerTC.setCellValueFactory(new PropertyValueFactory<Question,Integer>("correctAnswer"));
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
    	questionTA.setText(null);
    	instructionsTA.setText(null);
    	answer1TA.setText(null);
    	answer2TA.setText(null);
    	answer3TA.setText(null);
    	answer4TA.setText(null);
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
    
}
