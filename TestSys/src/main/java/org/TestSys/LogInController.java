package org.TestSys;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public class LogInController {
	
	 
	private SimpleClient client;
	
	

    @FXML
    private void switchToStudentMenu() throws IOException {
    	
    	client = SimpleClient.getClient();
    	client.openConnection();
    	
    	client.handleMessageFromLogInController("someUser", "somePass");
    	
    	//TODO to send message to simple client
        App.setRoot("StudentMenu");
        
        
    }
    
    
    
    
    
}
