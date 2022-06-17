package com.example.project_will_hero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Weapon extends Object implements java.io.Serializable{

    private int ammo;
    private String name;
    private ImageView skin;
    private Random random_var = new Random();

    public Weapon()
    {
        ammo = 5;
        if(random_var.nextBoolean())
            name = "src/main/resources/Sprites/WeaponAxe.png";
        else
            name = "src/main/resources/Sprites/WeaponShuriken.png";
        setSkin();
    }

    private void setSkin() {
        skin = new ImageView();
        File file = new File(name);
        skin.setImage(new Image(file.toURI().toString()));
        skin.setFitWidth(50);
        skin.setFitHeight(50);
        skin.setLayoutX(0);
        skin.setLayoutY(0);
    }

    public void set_null_Skin(){
        this.skin = null;
    }


    public String getName() {
        return name;
    }

    public int getAmmo() {
        return ammo;
    }

    @Override
    public void collision(Game_Controller cont, ImageView obj, ImageView hero) {
        cont.obj_die(obj,1000,-600,300);
        cont.get_chest_animation().stop();
        cont.get_tree_animation().stop();
        cont.get_enemy_animation().stop();
        cont.get_tnt_animation().stop();
        cont.get_island_animation().stop();
        for( Enemy e_temp : cont.getGame().getEnemy_list())
        {
            if(e_temp.getSkin() == obj)
            {
                cont.setCoins_val(cont.getCoins_val()+ e_temp.getCoins());
                cont.inc_value(cont.getCoins(), cont.getCoins_val());
                System.out.println("found");
                break;
            }
        }

        cont.setKey_listener(true,200);

    }

    @Override
    public void move() {
        super.move();
    }

    public ImageView getSkin() {
        return skin;
    }
}
