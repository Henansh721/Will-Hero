package com.example.project_will_hero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Boss_enemy extends Enemy {
    private String type;

    public Boss_enemy(ImageView skin) {
        super(skin);
        super.setCoins(20);
        this.type = "BOSS";
        this.setskinprop(skin);
    }
    private void setskinprop(ImageView skin) {
        String path = "src/main/resources/Sprites/Orc5.png";
        File file = new File(path);
        super.getSkin().setImage(new Image(file.toURI().toString()));
        super.getSkin().setFitWidth(100);
        super.getSkin().setFitHeight(100);
        super.getSkin().setLayoutY(skin.getLayoutY() - 100);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
