package com.example.nanonodenexus;

public abstract class DefenseTower extends Entity {
    private int damage;
    private int range;
    
    protected DefenseTower(Point position, int maxHp, Point dimensions) {
        super(position, maxHp, dimensions);
    }

    /**
     * Shoots the given entity, causing damage.
     * @param entity The entity to shoot.
     */
    protected void shoot(Entity entity) {
        entity.takeDamage(damage);
    }

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
