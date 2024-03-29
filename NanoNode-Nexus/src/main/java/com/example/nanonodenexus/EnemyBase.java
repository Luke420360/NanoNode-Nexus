package com.example.nanonodenexus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.time.LocalTimer;
import com.example.nanonodenexus.data.EnemyBaseData;
import com.example.nanonodenexus.data.EnemyData;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static java.lang.System.*;

public class EnemyBase extends Entity {

    private LocalTimer timer = newLocalTimer();
    private Duration interval = Duration.seconds(5);

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

    @Override
    public void onUpdate(double tpf) {


        if (timer.elapsed(interval)) {
            Game game = FXGL.geto("gameInstance");
            int duration = 5 -  geti("level") / 5;
            this.interval = Duration.seconds(duration);
            EnemyData enemyData = FXGL.geto("enemyData");
            com.almasb.fxgl.entity.Entity FXGLEntity = spawn(
                    "Enemy",
                    new SpawnData().put("enemyData", enemyData)
            );
            Enemy enemy = (Enemy) game.getEntityFromFXGL(FXGLEntity);
            game.addLevelModifier(enemy);
            timer.capture();
        }
//        if(this.getHp() <= 0) gameEnd();

    }
}
