package com.example.nanonodenexus;

import javafx.scene.image.Image;

public abstract class Entity {

    private Image image;
    private int hp;
    private int maxHp;
    private int posX;
    private int posY;
    private int dimensionsX;
    private int dimensionsY;

    protected Entity(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getDimensionsX() {
        return dimensionsX;
    }

    public int getDimensionsY() {
        return dimensionsY;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void takeDamage (int damage) {
        this.hp -= damage;
    }

}
