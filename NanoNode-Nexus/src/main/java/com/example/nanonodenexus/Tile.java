package com.example.nanonodenexus;

public class Tile {
    private final Wall wallNorth;
    private final Wall wallEast;
    private final Wall wallSouth;
    private final Wall wallWest;
    private final int posX;
    private final int posY;

    public Tile(int posX, int posY, boolean wallNorth, boolean wallEast, boolean wallSouth, boolean wallWest) {
        this.posX = posX;
        this.posY = posY;
        this. wallNorth = new Wall("North", wallNorth);
        this. wallEast = new Wall("East", wallEast);
        this. wallSouth = new Wall("South", wallSouth);
        this. wallWest = new Wall("West", wallWest);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Wall[] getWalls () {
        return new Wall[]{ wallNorth, wallEast, wallSouth, wallWest };
    }

}
