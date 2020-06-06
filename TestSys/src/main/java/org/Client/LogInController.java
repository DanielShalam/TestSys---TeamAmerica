package org.TestSys;
import Server.ServerMain.Carrier;
import java.io.IOException;
import javafx.fxml.FXML;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;




public class LogInController {
	
	 
	private SimpleClient client;
	private Carrier localCarrier = null;
	String userReceviedName = null;
	String userReceviedPass = null;
	String userReceviedRole=  null;
	


	
	

    @FXML
    private void switchToStudentMenu() throws IOException {
    	
    	client = SimpleClient.getClient();
    	client.openConnection();
    	
    	client.handleMessageFromLogInController("someUser", "somePass");
    	System.out.println("meesage from LogInController Handaled");
    	while (true) {
    		System.out.println("running for ever");
    		if (client.isAnswerReturned==true){
    			
    			localCarrier = client.answerCarrier;
    			userReceviedName = localCarrier.carrierMessageMap.get("userName");
    			userReceviedPass = localCarrier.carrierMessageMap.get("pass");
    			userReceviedRole = localCarrier.carrierMessageMap.get("role");
    			
    			System.out.println("data From SimpleServer :" +  userReceviedName +" "+ userReceviedPass +" "+ userReceviedRole);
    			break;
    			
    		}
    		
    	}
    	if (Integer.parseInt(userReceviedRole)==1) {
    		
    		
    		App.setRoot("StudentMenu");
    	}
    	
    	
        
        
    }
    
    
    
    
    
    
    
    
}
