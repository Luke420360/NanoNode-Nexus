package com.example.nanonodenexus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.example.nanonodenexus.data.EnemyData;
import com.example.nanonodenexus.data.PlayerData;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class Player extends Entity {
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    protected Player(PlayerData data) {
        super(new Point(200,300), 400, new Point(28, 32), EntityType.PLAYER);
        this.dimensions = new Point(28, 32);
        this.setImage("playerIdle.png");
    }

    @Override
    public void onAdded() {
        Point playerPos = FXGL.geto("playerPos");
        entity.setPosition(playerPos.x(), playerPos.y());
    }
}