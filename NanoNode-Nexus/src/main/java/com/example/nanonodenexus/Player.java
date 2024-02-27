package com.example.nanonodenexus;

import com.almasb.fxgl.dsl.FXGL;
import com.example.nanonodenexus.data.EnemyData;
import com.example.nanonodenexus.data.PlayerData;

public class Player extends Entity {
    protected Player(PlayerData data) {
        super(new Point(200,300), 400, new Point(28, 32), EntityType.PLAYER);
        this.dimensions = new Point(28, 32);
        this.setImage("player.png");
    }

    @Override
    public void onAdded() {
        Point playerPos = FXGL.geto("playerPos");
        entity.setPosition(playerPos.x(), playerPos.y());
    }
}