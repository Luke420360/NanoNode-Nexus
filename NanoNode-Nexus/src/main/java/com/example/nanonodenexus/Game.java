package com.example.nanonodenexus;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Entity> entities;
    private int iron;
    // private Renderer renderer;
    public Game() {
        this.entities = new ArrayList<>();
        this.iron = 0;
        // this.renderer = new Renderer();
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

    public int removeIron(int iron) {
        this.iron -= iron;
        return this.iron;
    }

    public void update() {
        // updating all entities
    }
}
