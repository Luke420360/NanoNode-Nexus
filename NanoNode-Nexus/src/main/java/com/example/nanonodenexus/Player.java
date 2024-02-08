package com.example.nanonodenexus;

public class Player extends Entity {
    protected Player(Point position) {
        super(position);
        this.dimensions = new Point(48, 64);
        this.setImage("Player.png");
    }
}