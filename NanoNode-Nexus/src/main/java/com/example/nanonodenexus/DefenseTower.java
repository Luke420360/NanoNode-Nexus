package com.example.nanonodenexus;

import javafx.geometry.Point2D;

import java.util.Objects;

public abstract class DefenseTower extends Entity {
    private int damage;
    private int range;
    
    protected DefenseTower(Point position, int maxHp, Point dimensions, EntityType en) {
        super(position, maxHp, dimensions, en);
    }

    /**
     * Shoots the given entity, causing damage.
     * @param entity The entity to shoot.y
     */

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getDamage() {
        return damage;
    }
}
