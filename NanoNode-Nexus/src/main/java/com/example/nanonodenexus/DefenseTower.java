package com.example.nanonodenexus;

public abstract class DefenseTower extends Entity {
    private int damage;
    protected int cost;
    private int range;
    
    protected DefenseTower(Point position) {
        super(position);
    }

    /**
     * Shoots the given entity, causing damage.
     * @param entity The entity to shoot.
     */
    protected void shoot(Entity entity) {
        entity.takeDamage(damage);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
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
