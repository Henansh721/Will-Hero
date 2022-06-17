package com.example.project_will_hero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Red_enemy extends Enemy {
    private String type;

    public Red_enemy(ImageView skin) {
        super(skin);
        super.setCoins(10);
        this.type = "RED";
        this.setskinprop(skin);
    }

    private void setskinprop(ImageView skin) {
        String path = "src/main/resources/Sprites/RedOrc1.png";
        File file = new File(path);
        super.getSkin().setImage(new Image(file.toURI().toString()));
        super.getSkin().setFitWidth(70);
        super.getSkin().setFitHeight(70);
        super.getSkin().setLayoutY(skin.getLayoutY() - 70);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
