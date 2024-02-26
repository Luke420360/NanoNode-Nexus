package com.example.nanonodenexus;

import com.almasb.fxgl.entity.component.Component;
import com.example.nanonodenexus.data.EnemyData;
import javafx.geometry.Point2D;

public class Enemy extends Entity {

    private int damage;
    private int droppedIron;
    private double speed = 20;

    protected Enemy(EnemyData data) {
        super(new Point(250,250), data.hp(), new Point(48,48), EntityType.ENEMY);
        this.setImage("enemy.png");
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
    public void onAdded() {
        entity.setPosition(200, 200);
    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
//        Point2D enemyPosition = this.getGameEntity().getPosition();
//        Point2D playerPosition = player.getGameEntity().getPosition();
//        Point2D direction = new Point2D(playerPosition.getX() - enemyPosition.getX(), playerPosition.getY() - enemyPosition.getY()).normalize();
//        this.getGameEntity().translate(direction.multiply(tpf * speed)); // speed is the movement speed of the enemy
    }

}
