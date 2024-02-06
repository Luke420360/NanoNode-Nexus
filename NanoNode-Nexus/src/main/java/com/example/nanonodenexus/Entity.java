package com.example.nanonodenexus;

import javafx.scene.image.Image;

public abstract class Entity {

    private String image;
    private int hp;
    private int maxHp;

    public void setPosition(Point position) {
        this.position = position;
    }

    private Point position;
    private Point dimensions;

    protected Entity(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return this.position;
    }

    public int getMaxHp() {
        return this.maxHp;
    }

    public Point getDimensions() {
        return this.dimensions;
    }


    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void takeDamage (int damage) {
        this.hp -= damage;
    }

}
