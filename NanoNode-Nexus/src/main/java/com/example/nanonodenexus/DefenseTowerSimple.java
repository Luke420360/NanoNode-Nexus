package com.example.nanonodenexus;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.image.Image;

public class DefenseTowerSimple extends DefenseTower{

    public static int cost = 200;
    protected DefenseTowerSimple(Point position) {
        super(position, 100,  new Point((int) (FXGL.geti("maceCellWidth") / 1.5), (int) (FXGL.geti("maceCellWidth") / 1.5)));
        this.setImage("tower.png");
    }
}
