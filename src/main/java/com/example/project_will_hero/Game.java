package com.example.project_will_hero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Game implements java.io.Serializable{

    private static final long serialVersionUID = 1l;
    private Player player;
    private Hero hero;
    private  ArrayList<Enemy> enemy_list;
    private  ArrayList<Island> island_list;
    private  ArrayList<TNT> tnt_list;
    private  ArrayList<Chest> chest_list;
    private  ArrayList<Helmet> helmet_list;

    private Random random_var = new Random();


    public Game(Player player,int helmet_index)
    {
        this.player = player;
        this.chest_list = new ArrayList<>();
        this.enemy_list = new ArrayList<>();
        this.island_list = new ArrayList<>();
        this.helmet_list = new ArrayList<>();
        this.tnt_list = new ArrayList<>();
        initialize();
    }

    public void initialize(){
        this.setIsland_list();
        this.setEnemy_list();
        this.setChest_list();
        this.setHelmet_list();
        this.setTnt_list();
        this.hero = new Hero(helmet_list.get(0));
        System.out.println("Game initialization done");
    }

    public void play(){}
    public void use_collision(){}

    public ArrayList<Enemy> getEnemy_list() {
        return enemy_list;
    }

    public void setEnemy_list() {
        for(int i=2;i<50;i++)
        {
            ImageView island = island_list.get(i).getSkin();
            if( i > 47 ) {
                enemy_list.add(new Boss_enemy(island));
                System.out.println("MADE AT 49");
            }
            else if(random_var.nextBoolean())
            {
                Enemy enemy = null;

                if(i < 25)
                    enemy = new Green_enemy(island);
                else enemy = new Red_enemy(island);

                enemy_list.add(enemy);
            }

        }
    }

    private void setChest_list() {

        for(int i=2;i<50;i++)
        {
            if(( random_var.nextBoolean() && random_var.nextBoolean()))
            {
                ImageView island = island_list.get(i).getSkin();
                chest_list.add(new Chest(island));
            }
        }
    }

    private void setIsland_list() {
        for(int i=0;i<50;i++)
            island_list.add(new Island(i));
    }

    private void setTnt_list() {
        for(int i=2;i<50;i++)
        {
            if((random_var.nextBoolean() && random_var.nextBoolean() && random_var.nextBoolean()))
            {
                ImageView island = island_list.get(i).getSkin();
                tnt_list.add(new TNT(island));
            }
        }
    }

    private void setHelmet_list() {

        helmet_list.add(new Helmet());
        helmet_list.add(new Helmet());

    }

    public Player getPlayer() {
        return player;
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<Chest> getChest_list() {
        return chest_list;
    }

    public ArrayList<Helmet> getHelmet() {
        return helmet_list;
    }

    public ArrayList<Island> getIsland_list() {
        return island_list;
    }

    public ArrayList<TNT> getTnt_list() {
        return tnt_list;
    }

    public void setHelmet(ArrayList<Helmet> helmet) {
        this.helmet_list = helmet;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
