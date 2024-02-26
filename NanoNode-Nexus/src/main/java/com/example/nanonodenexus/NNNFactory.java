package com.example.nanonodenexus;

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
import javafx.scene.Node;

import static com.almasb.fxgl.dsl.FXGL.*;

public class NNNFactory implements EntityFactory {

    @Spawns("Enemy")
    public Entity spawnEnemy(SpawnData data) {
        EnemyData enemyData = data.get("enemyData");

        return entityBuilder(data)
                .type(EntityType.ENEMY)
                .viewWithBBox("enemies/" + enemyData.imageName())
                .collidable()
                .with(new TimeComponent())
                .with(new HealthIntComponent(enemyData.hp()))
                .with(new Enemy(enemyData))
                .with(new HealthbarComponent())
                .build();
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
        String imageName = data.get("imageName");

        Node view = texture(imageName);
        view.setRotate(90);

        Entity tower = data.get("tower");
        Entity target = data.get("target");

        return entityBuilder(data)
                .type(EntityType.EMPBALL)
                .viewWithBBox(view)
                .collidable()
                .with(new TimeComponent())
                .with(new HealthIntComponent(empBallData.hp()))
                .with(new EMPBallComponent(tower, target))
                .build();
    }

    @Spawns("EnemyBase")
    public Entity spawnEnemyBase(SpawnData data){
        EnemyBaseData enemyBaseData = data.get("enemyBaseData");

        return entityBuilder(data)
                .type(EntityType.ENEMY_BASE)
                .viewWithBBox( enemyBaseData.imageName())
                .collidable()
                .with(new TimeComponent())
                .with(new HealthIntComponent(enemyBaseData.hp()))
                .with(new EnemyBase(enemyBaseData))
                .with(new HealthbarComponent())
                .build();
    }

    @Spawns("Tower")
    public Entity spawnTower(SpawnData data){
        TowerData towerData = data.get("towerData");
        Point position = data.get("position");
        if(position == null) return null;
        return entityBuilder(data)
                .type(EntityType.TOWER)
                .viewWithBBox(towerData.imageName())
                .collidable()
                .with(new TimeComponent())
                .with(new HealthIntComponent(towerData.hp()))
                .with(new DefenseTowerSimple(towerData, position))
                .with(new HealthbarComponent())
                .build();
    }
}
