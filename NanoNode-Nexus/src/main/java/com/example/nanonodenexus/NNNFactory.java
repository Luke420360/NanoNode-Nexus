package com.example.nanonodenexus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.AutoRotationComponent;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.TimeComponent;
import com.example.nanonodenexus.Components.EMPBallComponent;
import com.example.nanonodenexus.Components.HealthbarComponent;
import com.example.nanonodenexus.data.*;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.*;

public class NNNFactory implements EntityFactory {

    @Spawns("Enemy")
    public Entity spawnEnemy(SpawnData data) {
        EnemyData enemyData = data.get("enemyData");
        Enemy enemy = new Enemy(enemyData);
        Game game = FXGL.geto("gameInstance");
        game.addEntity(enemy);
        Entity entity = entityBuilder(data)
                .type(EntityType.ENEMY)
                .viewWithBBox("enemies/" + enemyData.imageName())
                .collidable()
                .with(new TimeComponent())
                .with(new HealthIntComponent(enemyData.hp()))
                .with(enemy)
                .with(new HealthbarComponent())
                .build();
        enemy.addEntity(entity);

        return entity;
    }

    @Spawns("Player")
    public Entity spawnPlayer(SpawnData data){
        PlayerData playerData = data.get("playerData");

        return entityBuilder(data)
                .type(EntityType.PLAYER)
                .viewWithBBox("players/" + playerData.imageName())
                .collidable()
                .with(new TimeComponent())
                .with(new HealthIntComponent(playerData.hp()))
                .with(new Player(playerData))
                .with(new HealthbarComponent())
                .build();
    }
    @Spawns("EmpBall")
    public Entity spawnEMPBall(SpawnData data) {
        EMPBallData empBallData = data.get("empBallData");
        String imageName = empBallData.imageName();

        Node view = texture(imageName);
        view.setRotate(90);

        Point2D initPoint = data.get("initPoint");
        Entity target = data.get("target");

        return entityBuilder(data)
                .type(EntityType.EMPBALL)
                .viewWithBBox(view)
                .collidable()
                .with(new TimeComponent())
                .with(new HealthIntComponent(empBallData.hp()))
                .with(new EMPBallComponent(initPoint, target))
                .build();
    }

    @Spawns("EnemyBase")
    public Entity spawnEnemyBase(SpawnData data){
        EnemyBaseData enemyBaseData = data.get("enemyBaseData");
        Point position = data.get("position");

        return entityBuilder(data)
                .type(EntityType.ENEMY_BASE)
                .viewWithBBox( enemyBaseData.imageName())
                .collidable()
                .with(new TimeComponent())
                .with(new HealthIntComponent(enemyBaseData.hp()))
                .with(new EnemyBase(enemyBaseData, position))
                .with(new HealthbarComponent())
                .build();
    }

    @Spawns("Tower")
    public Entity spawnTower(SpawnData data){
        TowerData towerData = data.get("towerData");
        Point position = data.get("position");
        if(position == null) return null;
        System.out.println("planted");
        // Create a rectangle that matches the hitbox size, adjust width and height accordingly
        Rectangle hitboxView = new Rectangle(48, 48);
        hitboxView.setStroke(Color.RED); // Set the border color of the rectangle
        hitboxView.setFill(Color.TRANSPARENT); // Set the inside of the rectangle to be transparent

        return entityBuilder(data)
                .type(EntityType.TOWER)
                .viewWithBBox(towerData.imageName())
                .view(hitboxView)
                .collidable()
                .with(new TimeComponent())
                .with(new HealthIntComponent(towerData.hp()))
                .with(new DefenseTowerSimple(towerData, position))
                .with(new HealthbarComponent())
                .build();
    }
}
