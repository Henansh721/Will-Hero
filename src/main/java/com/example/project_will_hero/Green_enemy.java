package com.example.project_will_hero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Green_enemy extends Enemy {
    private String type;

    public Green_enemy(ImageView skin) {
        super(skin);
        this.type = "GREEN";
        this.setskinprop(skin);
    }

    private void setskinprop(ImageView skin) {
        String path = "src/main/resources/Sprites/Orc1.png";
        File file = new File(path);
        super.getSkin().setImage(new Image(file.toURI().toString()));
        super.getSkin().setFitWidth(53);
        super.getSkin().setFitHeight(53);
        super.getSkin().setLayoutY(skin.getLayoutY() - 53);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
