package com.example.nanonodenexus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.example.nanonodenexus.data.EnemyBaseData;
import com.example.nanonodenexus.data.EnemyData;
import com.example.nanonodenexus.data.PlayerData;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Game {

    private List<Entity> entities;
    private int iron;

    public Game() {
        this.entities = new ArrayList<>();
        this.iron = 1000;
    }

    public Enemy addLevelModifier(Enemy enemy) {
        int level = geti("level");
        enemy.setModifier(
                enemy.getDamage() + level * 5,
                enemy.getSpeed() + level * 25,
                enemy.getHp() + level * 25
        );
        return enemy;
    }

    public void levelUp () {
        int level = geti("level");

        set("level",level+1);
        FXGL.<MainApp>getAppCast().setLevelCountLabel("level " + geti("level"));
        FXGL.<MainApp>getAppCast().setInfo("LEVEL " + geti("level"), 3);

        List<Entity> toBeRemoved = new ArrayList<>();
        for (Entity entity : this.entities) {
            entity.setActive(false);
            toBeRemoved.add(entity);
        }
        for (Entity entity : toBeRemoved) {
            if (entity.getGameEntity() != null) entity.getGameEntity().removeFromWorld();
            this.removeEntity(entity);
        }

        FXGL.set("iron", 2000 + (500 * level));
        Random random = new Random();
        FXGL.set("enemyBasePos", new Point(
                (random.nextInt(10) + 10) * 48,
                (random.nextInt(10) + 10) * 48
        ));

        PlayerData playerData = FXGL.geto("playerData");
        EnemyBaseData enemyBaseData = FXGL.geto("enemyBaseData");
        spawn(
                "EnemyBase",
                new SpawnData()
                        .put("enemyBaseData", enemyBaseData)
                        .put("position", FXGL.geto("enemyBasePos"))
        );

        spawn(
                "Player",
                new SpawnData()
                        .put("playerData", playerData)
        );
        FXGL.set("killedRobots", 0);
    }

    public Point getPointFromPos(Point2D pos) {
        int maceCellWidth = FXGL.geti("maceCellWidth");
        return new  Point(
                (int) Math.floor(pos.getX()) / maceCellWidth,
                (int) Math.floor(pos.getY()) / maceCellWidth
        );
    }

    public Entity getEntityFromFXGL(com.almasb.fxgl.entity.Entity ent) {
        for (Entity enti : this.entities) {
            if (enti.getGameEntity() == ent) {
                return enti;
            }
        }
        return null;
    }

    public void removeEntityFromFXGLEntity(com.almasb.fxgl.entity.Entity entityToRemove) {
        Entity ent = this.getEntityFromFXGL(entityToRemove);
        ent.setActive(false);
        this.removeEntity(ent);
    }

    public Point2D getPosFromPoint(int x, int y) {
        int maceCellWidth = FXGL.geti("maceCellWidth");
        return new Point2D(
                (x) * maceCellWidth,
                (y) * maceCellWidth
        );
    }

    public com.almasb.fxgl.entity.Entity getEntity(int x, int y, EntityType et) {
        int cellDimension = 48;
        Point2D cell = new Point2D(Math.floorDiv(x ,cellDimension) + 1 , Math.floorDiv(y, cellDimension) + 1);
        Rectangle2D rect = new Rectangle2D(cell.getX() * 48,cell.getY() *48,48,48);
         var foundEntity =  getGameWorld().getEntitiesInRange(rect);
        for (com.almasb.fxgl.entity.Entity entity : foundEntity) {
            if(entity.isType(et)) return entity;
        }
        return null;
    }

    public List<Entity> getAllEntity() {
        return entities;
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }

    public int getIron() {
        return this.iron;
    }

    public int addIron(int iron) {
        this.iron += iron;
        return this.iron;
    }

    public void addKilledRobot() {
        int killedRobots = geti("killedRobots");
        set("killedRobots", killedRobots +1);
        FXGL.<MainApp>getAppCast().onEnemyKilled();
    }

    public int removeIron(int iron) {
        this.iron -= iron;
        return this.iron;
    }

}
