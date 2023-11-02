module com.example.wordle {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens com.example.wordle to javafx.fxml;
    exports com.example.wordle;
}