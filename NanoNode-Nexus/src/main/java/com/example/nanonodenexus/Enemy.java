package com.example.nanonodenexus;

public class Enemy extends MovableEntity{

    private int damage;
    private int droppedIron;

    protected Enemy(Point position) {
        super(position);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDroppedIron() {
        return droppedIron;
    }

    public void attack(Entity entity) {
        entity.takeDamage(this.damage);
    }
}
