package com.example.nanonodenexus;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class Enemy extends Entity {

    private int damage;
    private int droppedIron;
    private Entity player; // Reference to the player entity
    private double speed = 20;

    protected Enemy(Point position, int maxHP, Point dimensions, Player player) {
        super(position, maxHP, dimensions);
        this.setImage("Enemy.png");
        this.player = player;
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

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
        // Get the position component of the enemy and player
        Point2D enemyPosition = this.getGameEntity().getPosition();
        Point2D playerPosition = player.getGameEntity().getPosition();

        // Calculate the direction vector from the enemy to the player
        Point2D direction = new Point2D(playerPosition.getX() - enemyPosition.getX(), playerPosition.getY() - enemyPosition.getY()).normalize();

        // Move the enemy towards the player
        this.getGameEntity().translate(direction.multiply(tpf * speed)); // speed is the movement speed of the enemy
    }

}
