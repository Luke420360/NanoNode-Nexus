package com.example.nanonodenexus;

public class MovableEntity extends Entity {

    private int dirX;
    private int dirY;
    private int speed;

    protected MovableEntity(int posX, int posY) {
        super(posX, posY);
    }

    public void move() {
        this.setPosX(this.getPosX() + this.dirX);
        this.setPosY(this.getPosY() + this.dirY);
    }
    public int getDirX() {
        return dirX;
    }

    public void setDirX(int dirX) {
        this.dirX = dirX;
    }

    public int getDirY() {
        return dirY;
    }

    public void setDirY(int dirY) {
        this.dirY = dirY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
