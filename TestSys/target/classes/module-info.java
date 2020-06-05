module org.TestSys {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.TestSys to javafx.fxml;
    exports org.TestSys;
}