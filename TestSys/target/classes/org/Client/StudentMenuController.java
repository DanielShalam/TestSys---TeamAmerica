package org.TestSys;

import java.io.IOException;
import javafx.fxml.FXML;

public class StudentMenuController {

    @FXML
    private void switchToLogInMenu() throws IOException {
        App.setRoot("LogInMenu");
    }
}