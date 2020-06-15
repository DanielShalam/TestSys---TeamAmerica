/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

package il.cshaifasweng.HSTS.client;

import il.cshaifasweng.HSTS.entities.Carrier;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import javafx.event.ActionEvent;

enum role {
	STUDENT,
	TEACHER,
	PRINCIPLE
}

public class LoginController {
	
	private SimpleClient client;
	private Carrier localCarrier = null;
	String userReceviedName = null;
	String userReceviedPass = null;
	String userReceviedRole=  null;
	
	

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
    	
    	client.handleMessageFromLogInController("Sonny", "chickenPie");
    	System.out.println("meesage from LogInController Handled");
    	String userValid;
    	
    	/*userValid = authentication(usernameTF.getText(), passwordPF.getText());  TODO - function to check user in DB*/
    	userValid = usernameTF.getText().toUpperCase(); // TODO - delete after implementation of authentication
    	
    	while (true) {
    		System.out.println("running for ever");
    		if (client.isAnswerReturned==true){
    			
    			localCarrier = client.answerCarrier;
    			userReceviedName = (String) localCarrier.carrierMessageMap.get("userName");
    			userReceviedPass = (String) localCarrier.carrierMessageMap.get("pass");
    			userReceviedRole = (String) localCarrier.carrierMessageMap.get("role");
    			
    			System.out.println("data From SimpleServer :" +  userReceviedName +" "+ userReceviedPass +" "+ userReceviedRole);
    			
    			client.isAnswerReturned=false;
    			break;
    			
    		}	
    		
    	}
    	//if (Integer.parseInt(userReceviedRole)==1) {
    		//some set root
    	//}
    	
    	
    		
    		
    	try {
    		switch (userValid) {
    		case "STUDENT":
    			App.setRoot("StudentMenu");
    			break;
    		case "TEACHER":
    			App.setRoot("TeacherMenu");
    			break;
    		case "PRINCIPLE":
    			App.setRoot("PrincipleMenu");
    			break;
    		default:
    			incorrectUsernamePasswordLabel.setVisible(true);
    			passwordPF.clear();
    			break;
    		}
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} 
			
		
    	
    }

}
