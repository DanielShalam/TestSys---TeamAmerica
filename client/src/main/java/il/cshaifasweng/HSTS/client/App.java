package il.cshaifasweng.HSTS.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.image.Image;

import java.io.IOException;
/**
 * JavaFX App
 */

public class App extends Application {

	private static Scene scene;
	private static Stage myStage;

    @Override
    public void start(Stage stage) throws IOException {
    	myStage = stage;
    	stage.setTitle("TestSys");
        scene = new Scene(loadFXML("LoginMenu"));
        stage.setScene(scene);
        stage.show();
        //stage.setMaximized(true);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                LoginController.logMeOut();
                System.exit(0);
            }
        });
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        scene.getWindow().sizeToScene();
        
        if (fxml == "PrincipleMenu") {
        	myStage.setMaximized(true);
        } else {
        	myStage.setMaximized(false);
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}