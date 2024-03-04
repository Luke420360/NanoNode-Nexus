package com.example.nanonodenexus.Components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.example.nanonodenexus.data.EMPBallData;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.newLocalTimer;

public class EMPBallComponent extends Component {
    private Point2D initPoint;
    private Entity target;
    private double speed  = 500;
    private int damage = 70;

    public EMPBallComponent(Point2D initPoint, Entity target) {
        this.target = target;
        this.initPoint = initPoint;
    }

    @Override
    public void onAdded() {
        entity.setPosition(initPoint);

    }

    @Override
    public void onUpdate(double tpf){
        if (!target.isActive()) {
            entity.removeFromWorld();
            return;
        }

        if (entity.distanceBBox(target) < speed* tpf) {
            onTargetHit();
            return;
        }

        entity.translateTowards(target.getCenter(), speed * tpf);

    }

    private void onTargetHit() {
        entity.removeFromWorld();
        var hp = target.getComponent(HealthIntComponent.class);

        hp.damage(damage);
    }
}
