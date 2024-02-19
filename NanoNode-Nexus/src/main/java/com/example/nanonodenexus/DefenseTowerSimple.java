package com.example.nanonodenexus;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class DefenseTowerSimple extends DefenseTower{

    public static int cost = 200;

    protected DefenseTowerSimple(Point position) {
        super(position, 100,  new Point((int) (FXGL.geti("maceCellWidth") / 1.5), (int) (FXGL.geti("maceCellWidth") / 1.5)));
        this.setImage("tower.png");
    }

    public void shoot() {
        Point2D center = this.getGameEntity().getCenter().subtract(37/2.0, 13/2.0);
        Vec2 dir = Vec2.fromAngle(this.getGameEntity().getRotation() - 90);
        spawn("bullet", new SpawnData(center.getX(), center.getY()).put("dir", dir.toPoint2D()));
        System.out.println("Shooted" + "direction:" + center + dir);
    }
}
