package com.example.project_will_hero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;

public class Hero extends Character implements java.io.Serializable{

    private Helmet helmet;
    private Weapon weapon;
    private boolean is_alive;

    @FXML
    private ImageView skin;

    public Hero(Helmet helmet) {

        this.is_alive = true;
        this.helmet = helmet;
        this.weapon = new Weapon();
        setSkin();
    }

    public void setSkin() {
        skin = new ImageView();
        String path = "src/main/resources/Sprites/Hero.png";
        File file = new File(path);
        skin.setImage(new Image(file.toURI().toString()));
        skin.setFitWidth(53);
        skin.setFitHeight(70);
        skin.setLayoutX(200);
        skin.setLayoutY(225);
    }

    public void set_null_Skin() {
        this.skin = null;
    }

    public static void jump(Game_Controller cont, Node obj) {
        KeyFrame gravity_keyframe = new KeyFrame(Duration.millis(3), e -> cont.free_fall(obj));
        cont.set_grativy_timeline(new Timeline(gravity_keyframe));
        cont.get_grativy_timeline().setCycleCount(Timeline.INDEFINITE);
        cont.get_grativy_timeline().play();
    }

    public ImageView getSkin() {
        return skin;
    }

    public void collision(Game_Controller cont, ImageView obj, ImageView hero) {

    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setHelmet(Helmet helmet) {
        this.helmet = helmet;
    }

    public boolean Is_alive() {
        return is_alive;
    }

    public void setIs_alive(boolean is_alive) {
        this.is_alive = is_alive;
    }
}
