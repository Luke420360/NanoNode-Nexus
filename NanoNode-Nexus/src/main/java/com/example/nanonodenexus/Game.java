package com.example.nanonodenexus;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;

public class Game {

    private List<Entity> entities;
    private int iron;
    public Game() {
        this.entities = new ArrayList<>();
        this.iron = 1000;
    }

    public Point getPointFromPos(Point2D pos) {
        int maceCellWidth = FXGL.geti("maceCellWidth");
        return new  Point(
                (int) Math.floor(pos.getX()) / maceCellWidth,
                (int) Math.floor(pos.getY()) / maceCellWidth
        );
    }

    public Point2D getPosFromPoint(int x, int y) {
        int maceCellWidth = FXGL.geti("maceCellWidth");
        return new Point2D(
                (x) * maceCellWidth,
                (y) *  maceCellWidth
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
        System.out.printf("Added " + iron + "iron. now total " + this.iron);
        return this.iron;
    }

    public int removeIron(int iron) {
        this.iron -= iron;
        System.out.printf("removed " + iron + "iron. now total " + this.iron);
        return this.iron;
    }

    public void update() {

    }
}
