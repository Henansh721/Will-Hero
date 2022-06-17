package com.example.project_will_hero;

import javafx.scene.image.ImageView;

abstract class Object {

    public abstract void collision(Game_Controller cont, ImageView obj, ImageView hero);

    public void move(){}
    private Coordinate coordinate;


}
