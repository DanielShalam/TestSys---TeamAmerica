package Server.ServerMain;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public class SeverMenuController {
	private static SimpleServer server;
	
	@FXML
    private void LunchServer() throws IOException {
    	
		server = new SimpleServer(3002);
        server.listen();
        
    }

}


