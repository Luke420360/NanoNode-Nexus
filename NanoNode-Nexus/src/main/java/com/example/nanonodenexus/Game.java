package com.example.nanonodenexus;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Entity> entities;
    private int iron;
    private final Renderer renderer;
    public Game(Renderer renderer) {
        this.entities = new ArrayList<>();
        this.iron = 1000;
        this.renderer = new Renderer();
    }

    public Entity getEntity(int x, int y) {
        for (Entity entity : entities) {
            int x2 = entity.getPosition().x();
            int y2 = entity.getPosition().y();
            int dimX = entity.getDimensions().x();
            int dimY = entity.getDimensions().y();
            if ((x2 <= x && x <= x2+dimX) && (y2 <= y && y <= y2+dimY)) {
                return entity;
            }
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
