package com.example.project_will_hero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Chest extends Object implements java.io.Serializable{

    private Weapon weapon;
    private String item;
    private int coin;
    private Boolean coll_count;

    private ImageView skin ;

    public Chest(ImageView island)
    {
        Random random_var = new Random();
        if(random_var.nextBoolean())
        {
            item = "coin";
            weapon = null;
            coin = 5;
        }
        else
        {
            item = "weapon";
            weapon = new Weapon();
            coin = 0;
        }
        this.coll_count = true;
        setSkin(island);

    }

    public ImageView getSkin() {
        return skin;
    }

    public void setSkin(ImageView island) {
        skin = new ImageView();
        Coordinate island_cor = new Coordinate(island.getLayoutX(),island.getLayoutY());
        String path = "src/main/resources/Sprites/ChestClosed.png";
        File file = new File(path);
        skin.setImage(new Image(file.toURI().toString()));
        skin.setFitWidth(100);
        skin.setFitHeight(70);
        skin.setLayoutX(island.getLayoutX() + ThreadLocalRandom.current().nextDouble(0,(island.getFitWidth() - 2*skin.getFitWidth())));
        skin.setLayoutY(island_cor.getY_cor() - 70);
    }

    public void setSkin() {
        this.skin = null;
    }

    @Override
    public void collision(Game_Controller cont, ImageView obj, ImageView hero) {

        if(coll_count) {
            File img = new File("src/main/resources/Sprites/ChestOpen.png");
            obj.setImage(new Image(img.toURI().toString()));
            if (check_item())
                coin_display(cont, obj, hero);
            else
                weapon_display(cont, obj, hero);

            cont.get_chest_animation().stop();
            cont.get_tree_animation().stop();
            cont.get_enemy_animation().stop();
            cont.get_tnt_animation().stop();
            cont.get_island_animation().stop();
            cont.setKey_listener(true, 200);
            coll_count = false;
        }

    }

    private void coin_display(Game_Controller cont, ImageView obj, ImageView hero) {
        ImageView image = new ImageView();
        File img = new File("src/main/resources/Sprites/Coin.png");
        image.setImage(new Image(img.toURI().toString()));
        image.setFitHeight(50);
        image.setFitWidth(50);
        image.setLayoutX(500);
        image.setLayoutY(100);

        cont.getAnchor_pane().getChildren().add(image);
        KeyFrame coin_keyframe = new KeyFrame(Duration.millis(400));
        Timeline coin_timeline = new Timeline(coin_keyframe);
        coin_timeline.setCycleCount(1);
        coin_timeline.setOnFinished(event -> {
            cont.getAnchor_pane().getChildren().remove(image);
            cont.setCoins_val(cont.getCoins_val()+ this.coin);
            cont.inc_value(cont.getCoins(), cont.getCoins_val());
        });
        coin_timeline.play();
    }

    private void weapon_display(Game_Controller cont, ImageView obj, ImageView hero)
    {
        ImageView image = new ImageView();
        File img = new File(weapon.getName());
        image.setImage(new Image(img.toURI().toString()));
        image.setFitHeight(50);
        image.setFitWidth(50);
        image.setLayoutX(500);
        image.setLayoutY(100);
        cont.set_weapon();
        cont.getAnchor_pane().getChildren().add(image);
        KeyFrame weapon_keyframe = new KeyFrame(Duration.millis(200));
        Timeline weapon_timeline = new Timeline(weapon_keyframe);
        weapon_timeline.setCycleCount(1);
        weapon_timeline.setOnFinished(event -> {
            cont.getAnchor_pane().getChildren().remove(image);
        });
        weapon_timeline.play();
    }

    @Override
    public void move() {
        super.move();
    }

    public Boolean check_item(){
        return this.item.equals("coin");
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


    public Weapon getWeapon() {
        return weapon;
    }
}
