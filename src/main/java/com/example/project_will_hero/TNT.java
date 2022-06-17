package com.example.project_will_hero;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class TNT extends Object implements java.io.Serializable{

    private static double radius;

    private ImageView skin;

    public TNT(ImageView island)
    {
        radius = 2.0;
        setSkin(island);
    }

    public void setSkin() {
        this.skin = null;
    }

    public void setSkin(ImageView island) {
        skin = new ImageView();
        Coordinate island_cor = new Coordinate(island.getLayoutX(),island.getLayoutY());
        String path = "src/main/resources/Sprites/TNT.png";
        File file = new File(path);
        skin.setImage(new Image(file.toURI().toString()));
        skin.setFitWidth(70);
        skin.setFitHeight(70);
        skin.setLayoutX(island.getLayoutX() + ThreadLocalRandom.current().nextDouble(2*skin.getFitWidth(),(island.getFitWidth() - 2*skin.getFitWidth())));
        skin.setLayoutY(island_cor.getY_cor() - 70);
    }

    public ImageView getSkin() {
        return skin;
    }

    public static double getRadius() { return radius;}

    @Override
    public void collision(Game_Controller cont, ImageView obj,ImageView hero) {
        File img = new File("src/main/resources/Sprites/Explosion.png");
        obj.setImage(new Image(img.toURI().toString()));

        ScaleTransition explosion = cont.scale_transition(obj,300,(int)TNT.getRadius(),(int)TNT.getRadius());
        explosion.setAutoReverse(true);
        explosion.setOnFinished(event -> {
            cont.getGrp().getChildren().remove(obj);
//            cont.getTnt_list().remove(obj);
        });
        explosion.play();

        cont.get_chest_animation().stop();
        cont.get_tree_animation().stop();
        cont.get_enemy_animation().stop();
        cont.get_tnt_animation().stop();
        cont.get_island_animation().stop();

        cont.obj_die(hero,1000,-600,0);

        cont.setKey_listener(true,400);
        cont.getGame().getHero().setIs_alive(false);
    }

    @Override
    public void move() {
        super.move();
    }
}
