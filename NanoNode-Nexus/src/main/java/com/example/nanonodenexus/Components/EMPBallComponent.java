package com.example.nanonodenexus.Components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.example.nanonodenexus.data.EMPBallData;
import javafx.geometry.Point2D;

public class EMPBallComponent extends Component {
    private Entity tower;
    private Entity target;
    private double speed  = 20;
    private int damage = 20;

    public EMPBallComponent(Entity tower, Entity target) {
        this.target = target;
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

        if (hp.isZero()) {
            FXGL.getGameWorld().removeEntity(target);
            target.removeFromWorld();
        }
    }
}
