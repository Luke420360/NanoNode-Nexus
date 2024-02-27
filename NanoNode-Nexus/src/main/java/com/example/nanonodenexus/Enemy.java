package com.example.nanonodenexus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.component.Component;
import com.example.nanonodenexus.data.EnemyData;
import javafx.geometry.Point2D;

import java.util.*;

public class Enemy extends Entity {

    private int damage;
    private int droppedIron;
    private Entity player; // Reference to the player entity
    private double speed = 200;
    private ArrayList<Point2D> destination;

    protected Enemy(EnemyData data) {
        super(new Point(250, 250), data.hp(), new Point(48, 48), EntityType.ENEMY);
        this.setImage("Enemy.png");
        this.destination = new ArrayList<Point2D>();
    }

    public int getDamage() {
        return damage;
    }

    public void addEntity(com.almasb.fxgl.entity.Entity entity) {
        this.setEntity(entity);
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

    public void setDestination(Point2D destination) {
        this.destination.add(destination);
    }

    @Override
    public void onAdded() {

        Point enemyBasePos = FXGL.geto("enemyBasePos");
        Point playerPos = FXGL.geto("playerPos");
        MazePathfinding pathFinding = FXGL.geto("pathFinding");
        int maceCellWidth = FXGL.geti("maceCellWidth");
        Game game = FXGL.geto("gameInstance");

        entity.setPosition(enemyBasePos.x(), enemyBasePos.y());
        List<AStarMazeCell> path = pathFinding.findPath(
                enemyBasePos.x() / maceCellWidth,
                enemyBasePos.y() / maceCellWidth,
                playerPos.x(),
                playerPos.y());
        for (AStarMazeCell cell : path) {
            this.setDestination(game.getPosFromPoint(cell.getX(), cell.getY()));
        }
    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
        if (this.destination.isEmpty()) return;
        // Get the position component of the enemy and player
        Point2D myPos = this.getGameEntity().getPosition();


        // Calculate the direction vector from the enemy to the player
        Point2D direction = new Point2D(this.destination.getFirst().getX() - myPos.getX(), this.destination.getFirst().getY() - myPos.getY()).normalize();
        if (
                myPos.getX() - 5 < this.destination.getFirst().getX() &&
                myPos.getY() - 5 < this.destination.getFirst().getY() &&
                myPos.getX() + 5 > this.destination.getFirst().getX() &&
                myPos.getY() + 5 > this.destination.getFirst().getY()
        ) {
            Game game = FXGL.geto("gameInstance");
            if (this.destination.size() > 1) {
                Point2D nextDest = this.destination.get(1);
                com.almasb.fxgl.entity.Entity tower = game.getEntity((int)nextDest.getX(), (int)nextDest.getY(), EntityType.TOWER);
                if (!Objects.isNull(tower)) {
                    tower.getComponent(HealthIntComponent.class).damage(1);
                    return;
                }
            }
            this.destination.removeFirst();
            return;
        }

        // Move the enemy towards the player
        this.getGameEntity().translate(direction.multiply(tpf * speed)); // speed is the movement speed of the enemy
    }

}
