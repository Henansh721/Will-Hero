package com.example.project_will_hero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends Character implements java.io.Serializable{

    private int coins;
    private ImageView skin;

    public Enemy(ImageView island)
    {
        this.coins = 5;
        setSkin(island);
    }

    public static void jump(Game_Controller cont, Node obj) {
        cont.jump_animation(obj,500,100,0).play();
    }

    public void setSkin(ImageView island) {
        skin = new ImageView();
        Coordinate island_cor = new Coordinate(island.getLayoutX(),island.getLayoutY());
//        String path = "src/main/resources/Sprites/Orc1.png";
//        File file = new File(path);
//        skin.setImage(new Image(file.toURI().toString()));
//        skin.setFitWidth(53);
//        skin.setFitHeight(70);
        skin.setLayoutX(island.getLayoutX() + ThreadLocalRandom.current().nextDouble(0,(island.getFitWidth() - 2*skin.getFitWidth())));
        skin.setLayoutY(island_cor.getY_cor() - 70);
    }

    public ImageView getSkin() {
        return skin;
    }

    public void setSkin() {
        this.skin = null;
    }

    public void collision(Game_Controller cont, ImageView obj, ImageView hero) {
        if (obj.getLayoutY() > obj.getLayoutY()) {

            cont.get_grativy_timeline().stop();
            cont.scale_transition(obj,500,-1,0).play();
            cont.getGame().getHero().setIs_alive(false);

        } else {

            cont.obj_die(obj,1000,-600,300);
        TranslateTransition animation = cont.translate_animation(obj,1000,-600,300);
        animation.setOnFinished(event -> {
            obj.setVisible(false);
            obj.setDisable(true);
            obj.setLayoutY(1000);
            obj.setLayoutX(1000);
        });
        animation.play();

            //cont.getGrp().getChildren().remove(obj);
//        cont.getEnemy_list().remove(obj);

            cont.get_chest_animation().stop();
            cont.get_tree_animation().stop();
            cont.get_enemy_animation().stop();
            cont.get_tnt_animation().stop();
            cont.get_island_animation().stop();

            for( Enemy e_temp : cont.getGame().getEnemy_list()) {
                if(e_temp.getSkin() == obj) {
                    cont.setCoins_val(cont.getCoins_val()+ e_temp.getCoins());
                    cont.inc_value(cont.getCoins(), cont.getCoins_val());
                    break;
                }
            }
        }
        cont.setKey_listener(true,200);
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

}
