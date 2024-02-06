package com.example.nanonodenexus;

public class LevelBuilder {
    private final int dimensionX;
    private final int dimensionY;
    private Tile[][] grid;

    public LevelBuilder(int dimensionX, int dimensionY) {
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        grid = new Tile[dimensionY][dimensionX];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int y = 0; y < dimensionY; y++) {
            for (int x = 0; x < dimensionX; x++) {
                grid[y][x] = new Tile(x,y,false,false,false,false);
            }
        }
    }

    public void getMapFromDB() {
        return;
    }

    public void saveMapToDB() {
        return;
    }
}
