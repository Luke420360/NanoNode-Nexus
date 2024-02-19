package com.example.nanonodenexus;

public class Player extends Entity {
    protected Player(Point position) {
        super(position, 400, new Point(28, 32));
        this.dimensions = new Point(28, 32);
        this.setImage("Player.png");
    }
}