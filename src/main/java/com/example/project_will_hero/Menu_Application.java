package com.example.project_will_hero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Menu_Application extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Menu_Application.class.getResource("Main_menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1000,600);
        stage.setTitle("Will Hero");
        stage.setResizable(false);
        String path = "src/main/resources/Sprites/Hero.png";
        File fileIcon = new File(path);
        Image applicationIcon = new Image(fileIcon.toURI().toString());
        stage.getIcons().add(applicationIcon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}