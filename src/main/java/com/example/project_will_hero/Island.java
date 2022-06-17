package com.example.project_will_hero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class Island extends Object implements java.io.Serializable{

    private ImageView skin;

    public Island(int index)
    {
        setSkin(index);
    }

    public void setSkin(int i) {
        skin = new ImageView();
        skin.setLayoutX(-44 + 550 * i);
        int horizontal_level = 375;
        skin.setLayoutY(horizontal_level + ThreadLocalRandom.current().nextDouble(-50,51));
        String path = "src/main/resources/Sprites/Islands2.png";
        File file = new File(path);
        skin.setImage(new Image(file.toURI().toString()));
        skin.setFitWidth(374 + ThreadLocalRandom.current().nextDouble(-50,51));
        skin.setFitHeight(125 + ThreadLocalRandom.current().nextDouble(-20,21));

    }

    public void setSkin() {
        this.skin = null;
    }


    @Override
    public void collision(Game_Controller cont, ImageView obj, ImageView hero) {
        cont.setFall_value(cont.getFall_value()*-1);
        cont.setUpward_bool(cont.getHeigh_count() < 150);

        if(cont.getHeigh_count() < 150)
            if(obj.getLayoutY() - ((ImageView)hero).getLayoutY() > 0 && obj.getLayoutY() - ((ImageView)hero).getLayoutY() < 300)
                hero.setLayoutY(obj.getLayoutY() - ((ImageView)hero).getFitHeight());
            else {
                cont.get_grativy_timeline().stop();
                cont.obj_die(hero,1000,-600,0);
//                cont.translate_animation(hero, 1000, -600, 100).play();
//                cont.getGrp().getChildren().remove(hero);
                cont.getGame().getHero().setIs_alive(false);
            }

        cont.setHeigh_count(0);
        cont.setInside_node(cont.getInside_node()+1);
    }

    @Override
    public void move() {
        super.move();
    }


    public ImageView getSkin() {
        return skin;
    }
}
