package il.cshaifasweng.HSTS.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
/**
 * JavaFX App
 */

enum boundaryType {
	LOGIN_MENU,
	STUDENT_MENU,
	TEACHER_MENU,
	PRINCIPLE_MENU,
	STUDENT_EXAM_PAGE,
	QUESTION_MENU,
	SET_QUESTION_MENU,
	EXAM_MENU,
	SET_EXAM,
	PRINCIPLE_REQUESTS
}
public class App extends Application {

	private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
    	stage.setTitle("TestSys");
        scene = new Scene(loadFXML("LoginMenu"));
        stage.setScene(scene);
        stage.show();
        
      
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        scene.getWindow().sizeToScene();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}