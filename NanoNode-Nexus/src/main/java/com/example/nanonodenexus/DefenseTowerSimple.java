package com.example.nanonodenexus;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.time.LocalTimer;
import com.example.nanonodenexus.data.EMPBallData;
import com.example.nanonodenexus.data.TowerData;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class DefenseTowerSimple extends DefenseTower{

    private LocalTimer shootTimer = newLocalTimer();
    private Duration shootInterval = Duration.seconds(2);
    public static int cost = 200;
    private Point2D position;

    protected DefenseTowerSimple(TowerData data, Point position) {
        super(position, data.hp(),  position, EntityType.TOWER);
        this.position = new Point2D(position.x(), position.y());
        this.setImage("tower.png");
    }

    @Override
    public void onAdded() {
        entity.setPosition(position);
        shootTimer = newLocalTimer();
        shootTimer.capture();
        Circle rangeCircle = new Circle(48 * 4, Color.TRANSPARENT);
        rangeCircle.setStroke(Color.GHOSTWHITE);
        rangeCircle.setStrokeWidth(1);
        rangeCircle.setCenterX(24);
        rangeCircle.setCenterY(24);
        entity.getViewComponent().addChild(rangeCircle);
    }

    @Override
    public void onUpdate(double tpf) {
        if (entity != null) {
            if (shootTimer.elapsed(shootInterval)) {
                getGameWorld().getClosestEntity(entity, e -> e.isType(EntityType.ENEMY) || e.isType(EntityType.ENEMY_BASE))
                        .ifPresent(nearestEnemy -> {
                            var enemyPosition = nearestEnemy.getPosition();
                            if(enemyPosition.distance(this.position) > 4*48) return;
                            shoot(nearestEnemy);
                        });
                shootTimer.capture();
            }
        }
    }

    public void shoot(Entity enemy) {
        EMPBallData empBallData = getAssetLoader().loadJSON("empBalls/empBall.json" , EMPBallData.class).orElse(null);
        spawn(
                "EmpBall",
                new SpawnData()
                        .put("initPoint", position)
                        .put("empBallData", empBallData)
                        .put("target",  enemy)
        );
    }

}
