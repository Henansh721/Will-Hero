package com.example.project_will_hero;

public class Coordinate {
    private double x_cor;
    private double y_cor;

    Coordinate(double x_cor, double y_cor){
        this.x_cor=x_cor;
        this.y_cor=y_cor;

    }

    public double getX_cor() {
        return x_cor;
    }

    public double getY_cor() {
        return y_cor;
    }

    public void setX_cor(double x_cor) {
        this.x_cor = x_cor;
    }

    public void setY_cor(double y_cor) {
        this.y_cor = y_cor;
    }
}

