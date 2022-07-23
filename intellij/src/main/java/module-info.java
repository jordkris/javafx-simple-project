module com.example.intellij {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.intellij to javafx.fxml;
    exports com.example.intellij;
}