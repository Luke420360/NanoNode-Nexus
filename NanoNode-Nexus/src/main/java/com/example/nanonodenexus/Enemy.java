package com.example.nanonodenexus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import com.example.nanonodenexus.data.EnemyData;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.*;

import static com.almasb.fxgl.dsl.FXGL.newLocalTimer;
import static com.almasb.fxgl.dsl.FXGL.spawn;

public class Enemy extends Entity {

    private int damage = 20;
    private int droppedIron;
    private Entity player; // Reference to the player entity
    private double speed = 150;
    private ArrayList<Point2D> destination;
    private LocalTimer timer = newLocalTimer();
    private Duration interval = Duration.seconds(2);

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

        List<AStarMazeCell> path = FXGL.geto("path");
        if (path.isEmpty()) {
            path = pathFinding.findPath(
                    enemyBasePos.x() / maceCellWidth,
                    enemyBasePos.y() / maceCellWidth,
                    playerPos.x(),
                    playerPos.y());
            FXGL.set("path", path);
        }

        entity.setPosition(enemyBasePos.x(), enemyBasePos.y());
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
                com.almasb.fxgl.entity.Entity player = game.getEntity((int)nextDest.getX(), (int)nextDest.getY(), EntityType.PLAYER);
                if (!Objects.isNull(tower)) {
                    if (timer.elapsed(interval)) {
                        tower.getComponent(HealthIntComponent.class).damage(this.damage);
                        timer.capture();
                    }
                    return;
                }
                else if (!Objects.isNull(player)) {
                    if (timer.elapsed(interval)) {
                        player.getComponent(HealthIntComponent.class).damage(this.damage);
                        timer.capture();
                    }
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
