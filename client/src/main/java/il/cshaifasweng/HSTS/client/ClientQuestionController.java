/**
 * Sample Skeleton for 'QuestionMenu.fxml' Controller Class
 */

package il.cshaifasweng.HSTS.client;

import java.io.IOException;
import java.util.List;
import il.cshaifasweng.HSTS.entities.Carrier;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import il.cshaifasweng.HSTS.entities.Question;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;


public class ClientQuestionController {
	
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

    @FXML // fx:id="courseCB"
    private ChoiceBox<?> courseCB; // Value injected by FXMLLoader
    
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
    private ComboBox<?> courseComboBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="answer1RB"
    private RadioButton answer1RB; // Value injected by FXMLLoader

    @FXML // fx:id="answer2RB"
    private RadioButton answer2RB; // Value injected by FXMLLoader

    @FXML // fx:id="answer4RB"
    private RadioButton answer4RB; // Value injected by FXMLLoader

    @FXML // fx:id="answer3RB"
    private RadioButton answer3RB; // Value injected by FXMLLoader  
    
    
    @FXML
    void createSetQuestionBoudary(ActionEvent event) {
    	try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetQuestionMenu.fxml"));
            Parent root2 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root2));
            stage.setMaximized(true);
            stage.setTitle("Add/Edit Questions");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    	
    }

    @FXML
    void deleteQuestion(ActionEvent event) {

    }

    @FXML
    void getAllQuestions(ActionEvent event) throws IOException {
    	client = LoginController.client;
    	//client.openConnection();
    	
    	String message = "get all questions";
    	Question question = null;
    	int id = 0;
    	
    	courseIDTC.setCellValueFactory(new PropertyValueFactory<Question,Integer>("courseId"));
    	questionTC.setCellValueFactory(new PropertyValueFactory<Question,String>("question"));
    	answersTC.setCellValueFactory(new PropertyValueFactory<Question,String[]>("answers"));
    	instructionsTC.setCellValueFactory(new PropertyValueFactory<Question,String>("instructions"));
    	correctAnswerTC.setCellValueFactory(new PropertyValueFactory<Question,Integer>("correctAnswer"));
    	teacherIdTC.setCellValueFactory(new PropertyValueFactory<Question,Integer>("teacherId"));
    	
    	client.handleMessageFromClientQuestionController(message, id, question);
    	System.out.println("message from ClientQuestionController Handled");
    	
    	while (true) {
    		if (client.isAnswerReturned==true) {
    			
    			localCarrier = client.answerCarrier;
    			question_list = (List<Question>) localCarrier.carrierMessageMap.get("questions");
    			
    			System.out.println(question_list);
                for (Question questionItem : question_list)
                {
                	System.out.println(questionItem.getQuestion());
                	questionTV.getItems().addAll(questionItem);
                	
                }
    			//loadData(question_list);
    			
    			client.isAnswerReturned=false;
    			break;
    		}	
    	}
    }

    @FXML
    void getQuestionsByTeacherID(ActionEvent event)  throws IOException {
 
    	
    }

    @FXML
    void loadQuestionToSetQuestionBoudary(ActionEvent event) {

    }
    
    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void clearFields(ActionEvent event) {
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

    @FXML
    void commitQuestionToDB(ActionEvent event) {
    	if (isQuestionValid())
    	{
    		
    	}
    }
    
    @FXML
    boolean isQuestionValid() {
    	if (questionTA.getText().isBlank() || answer1TA.getText().isBlank() || answer2TA.getText().isBlank() ||
    			answer3TA.getText().isBlank() || answer4TA.getText().isBlank()) 
    			//correctAnswerChoiceBox.getSelectionModel().isBlank() || courseComboBox.getSelectionModel().isBlank())
    	{
    		return false;
    	} else {
    		return true;
    	}
    }
     
    //Receive message from scene 1
    public void transferMessage(SimpleClient client, Integer teacherID) {
        
    }
    
    //Load data to table
    void loadData(List<Question> question_list) {
    	
    	courseIDTC.setCellValueFactory(new PropertyValueFactory<Question,Integer>("courseId"));
    	questionTC.setCellValueFactory(new PropertyValueFactory<Question,String>("question"));
    	answersTC.setCellValueFactory(new PropertyValueFactory<Question,String[]>("answers"));
    	instructionsTC.setCellValueFactory(new PropertyValueFactory<Question,String>("instructions"));
    	correctAnswerTC.setCellValueFactory(new PropertyValueFactory<Question,Integer>("correctAnswer"));
    	teacherIdTC.setCellValueFactory(new PropertyValueFactory<Question,Integer>("teacherId"));
    	
        for (Question questionItem : question_list)
        {
        	questionTV.getItems().addAll(questionItem);
        }   
    }
    
}
