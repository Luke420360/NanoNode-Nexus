package com.example.nanonodenexus;

public class Wall {
    private String direction;
    private final boolean passable;

    public Wall(String direction, boolean passable) {
        this.direction = direction;
        this.passable = passable;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isPassable() {
        return passable;
    }
}
