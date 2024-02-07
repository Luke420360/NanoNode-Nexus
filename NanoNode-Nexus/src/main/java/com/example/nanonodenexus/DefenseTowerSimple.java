package com.example.nanonodenexus;

import javafx.scene.image.Image;

public class DefenseTowerSimple extends DefenseTower{
    protected DefenseTowerSimple(Point position) {
        super(position);
        this.cost = 200;
        this.dimensions = new Point(10, 10);
        this.setImage("defenseTowerSimple.png");
    }
}
