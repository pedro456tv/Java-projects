module com.example.reflextest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.reflextest to javafx.fxml;
    exports com.example.reflextest;
}