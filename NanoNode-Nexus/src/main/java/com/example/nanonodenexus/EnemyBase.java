package com.example.nanonodenexus;

import static java.lang.System.*;

public class EnemyBase extends Entity {

    protected EnemyBase(Point position, int maxHP, Point dimensions) {
        super(position, maxHP, dimensions);
        this.dimensions = new Point(64,64);
        this.setImage("EnemyBase.png");
    }

}
