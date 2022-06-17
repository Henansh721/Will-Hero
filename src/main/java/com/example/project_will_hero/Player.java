package com.example.project_will_hero;

public class Player implements java.io.Serializable{

    private double progress;
    private  String name;
    private  String email;
    private  int score = 0;
    private  int coin;

    public Player(String name, String email)
    {
        this.name = name;
        this.email = email;
        this.score = 0;
        this.coin = 0;
    }

    public String getName() {
        return name;
    }

    public int getCoin() {
        return coin;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public double getProgress() {
        return progress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
