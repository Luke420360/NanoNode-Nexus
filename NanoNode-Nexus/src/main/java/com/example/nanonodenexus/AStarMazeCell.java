package com.example.nanonodenexus;
import com.almasb.fxgl.pathfinding.maze.MazeCell;

import java.util.Objects;

public class AStarMazeCell {
    private MazeCell mazeCell;
    private int gCost;
    private int hCost;
    private AStarMazeCell parent;

    public AStarMazeCell(MazeCell mazeCell) {
        this.mazeCell = mazeCell;
        this.gCost = Integer.MAX_VALUE; // Initialize with maximum value
        this.hCost = 0; // Will be set during pathfinding
        this.parent = null;
    }

    public MazeCell getMazeCell() {
        return mazeCell;
    }

    public int getGCost() {
        return gCost;
    }

    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public int getHCost() {
        return hCost;
    }

    public void setHCost(int hCost) {
        this.hCost = hCost;
    }

    public int getFCost() {
        return gCost + hCost;
    }

    public AStarMazeCell getParent() {
        return parent;
    }

    public void setParent(AStarMazeCell parent) {
        this.parent = parent;
    }

    // Add delegate methods for your MazeCell as needed, for example:
    public boolean hasTopWall() {
        return mazeCell.hasTopWall();
    }

    public boolean hasLeftWall() {
        return mazeCell.hasLeftWall();
    }

    public int getX() {
        return mazeCell.getX();
    }

    public int getY() {
        return mazeCell.getY();
    }

    public boolean equals(AStarMazeCell obj) {
        boolean isEqual = this.getX() == obj.getX() && this.getY() == obj.getY();
        if (isEqual) {
            System.out.println("isEqual");
        }
        return isEqual;
    }


    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
