package com.example.laberinto.Models;

public class Coordenadas implements Cloneable{

    private int x;
    private int y;

    public Coordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
