package il.cshaifasweng.HSTS.client;

import java.io.IOException;

import il.cshaifasweng.HSTS.entities.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class connectToServerController {

    @FXML // fx:id="portTF"
    private TextField portTF; // Value injected by FXMLLoader

    @FXML // fx:id="ipTF"
    private TextField ipTF; // Value injected by FXMLLoader

    @FXML // fx:id="portLB"
    private Label portLB; // Value injected by FXMLLoader

    @FXML // fx:id="ipLB"
    private Label ipLB; // Value injected by FXMLLoader
    

    @FXML // fx:id="connectBtn"
    private Button connectBtn; // Value injected by FXMLLoader

    @FXML
    void connect(ActionEvent event) {
    	String port = portTF.getText();
    	String ip = ipTF.getText();
    	
    	SimpleClient client = SimpleClient.getClient();
    	if(client.isConnected()) {
			try {
				App.setRoot("LogInMenu");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

}