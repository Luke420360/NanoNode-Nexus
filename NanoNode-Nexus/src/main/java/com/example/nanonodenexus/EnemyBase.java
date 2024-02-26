package com.example.nanonodenexus;

import com.example.nanonodenexus.data.EnemyBaseData;

import static java.lang.System.*;

public class EnemyBase extends Entity {

    protected EnemyBase(EnemyBaseData data) {
        super(new Point(400,400), data.hp(), new Point(64, 64), EntityType.ENEMY_BASE);
        this.dimensions = new Point(64,64);
        this.setImage("EnemyBase.png");
    }

}
