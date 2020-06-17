/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

package il.cshaifasweng.HSTS.client;

import il.cshaifasweng.HSTS.entities.Carrier;
import il.cshaifasweng.HSTS.entities.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.event.ActionEvent;


public class LoginController {
	
	static public SimpleClient client;
	private Carrier localCarrier = null;
	static public Integer userReceviedID = null;
	Role userReceviedRole=  null;
	
    @FXML // fx:id="usernameTF"
    private TextField usernameTF; // Value injected by FXMLLoader

    @FXML // fx:id="passwordPF"
    private PasswordField passwordPF; // Value injected by FXMLLoader

    @FXML // fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="incorrectUsernamePasswordLabel"
    private Label incorrectUsernamePasswordLabel; // Value injected by FXMLLoader

    @FXML
    void validate(ActionEvent event) throws IOException {
    	
    	client = SimpleClient.getClient();
    	client.openConnection();
    	
    	client.handleMessageFromLogInController(usernameTF.getText(), passwordPF.getText());
    	System.out.println("meesage from LogInController Handled");
    	
    	while (true) {
    		System.out.println("running for ever");
    		if (client.isAnswerReturned==true){
    			
    			localCarrier = client.answerCarrier;
    			userReceviedRole = (Role) localCarrier.carrierMessageMap.get("Role");
    			userReceviedID = (Integer) localCarrier.carrierMessageMap.get("ID");
    			
    			System.out.println("data From SimpleServer :" +  userReceviedRole +" "+ userReceviedID + "");
    			
    			client.isAnswerReturned=false;
    			break;
    			
    		}	
    		
    	}
    			
		switch (userReceviedRole) {
		case STUDENT:
			App.setRoot("StudentMenu");
			break;
		case TEACHER:
			App.setRoot("TeacherMenu");
			break;
		case PRINCIPLE:
			App.setRoot("PrincipleMenu");
			break;
		case INVALID:
			incorrectUsernamePasswordLabel.setVisible(true);
			passwordPF.clear();
			break;
		}
    	
    }

}
