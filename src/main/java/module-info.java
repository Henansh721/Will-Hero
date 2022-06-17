module com.example.project_will_hero {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project_will_hero to javafx.fxml;
    exports com.example.project_will_hero;
}