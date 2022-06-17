package com.example.project_will_hero;

import java.util.ArrayList;

public class Helmet implements java.io.Serializable{
    private String name;
    private ArrayList<Weapon> weapon_list;

    public Helmet()
    {
        this.name = "ICE";
        this.setWeapon_list();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Weapon> getWeapon_list() {
        return weapon_list;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeapon_list() {
        this.weapon_list = new ArrayList<Weapon>();
        this.weapon_list.add(new Weapon());
        this.weapon_list.add(new Weapon());

    }
}
