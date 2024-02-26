package com.example.nanonodenexus;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.time.LocalTimer;
import com.example.nanonodenexus.data.EMPBallData;
import com.example.nanonodenexus.data.TowerData;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class DefenseTowerSimple extends DefenseTower{

    private LocalTimer shootTimer = newLocalTimer();
    public static int cost = 200;
    private Point2D position;

    protected DefenseTowerSimple(TowerData data, Point position) {
        super(position, data.hp(),  new Point((int) (FXGL.geti("maceCellWidth") / 1.5), (int) (FXGL.geti("maceCellWidth") / 1.5)),EntityType.TOWER);
        this.position = new Point2D(position.x(), position.y());
        this.setImage("tower.png");
    }

    @Override
    public void onAdded() {
        entity.setPosition(position);
        shootTimer = newLocalTimer();
        shootTimer.capture();
    }

    @Override
    public void onUpdate(double tpf) {
        if (shootTimer.elapsed(Duration.seconds(1.5))) {
            if (entity != null) {
                getGameWorld().getClosestEntity(entity, e -> e.isType(EntityType.ENEMY))
                        .ifPresent(nearestEnemy -> {
                            shoot(nearestEnemy);
                        });
            }

        }
    }

    public void shoot(Entity enemy) {
        EMPBallData empBallData = getAssetLoader().loadJSON("empBalls/empBall.json" , EMPBallData.class).orElse(null);
       spawn(
                "EmpBall",
                new SpawnData()
                        .put("empBallData", empBallData)
                        .put("target",  enemy)
        );
    }

}
