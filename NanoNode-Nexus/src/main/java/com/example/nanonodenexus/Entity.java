package com.example.nanonodenexus;

import javafx.scene.image.Image;

public abstract class Entity {

    public Image image;
    public int hp;
    public int maxHp;
    public int posX;

    public Entity(int posX, int posY) {
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

    public int posY;
    public int dimensionsX;
    public int dimensionsY;
}
