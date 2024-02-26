package com.example.nanonodenexus;

import com.example.nanonodenexus.data.EnemyBaseData;
import javafx.geometry.Point2D;

import static java.lang.System.*;

public class EnemyBase extends Entity {

    protected EnemyBase(EnemyBaseData data, Point position) {
        super(position, data.hp(), new Point(48, 48), EntityType.ENEMY_BASE);
        this.dimensions = new Point(48,48);
        this.setPosition(position);
        this.setImage("EnemyBase.png");
    }

    @Override
    public void onAdded() {
        Point2D initPosition = new Point2D(getPosition().x(), getPosition().y());
        out.println(initPosition);
        entity.setPosition(initPosition);
    }
}
