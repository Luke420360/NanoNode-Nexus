package com.example.nanonodenexus;

import javafx.scene.image.Image;

public class DefenseTowerSimple extends DefenseTower{
    protected DefenseTowerSimple(Point position) {
        super(position, 100,  new Point(32,32));
        this.cost = 200;
        this.setImage("tower.png");
    }
}
