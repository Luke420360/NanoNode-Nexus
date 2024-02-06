package com.example.nanonodenexus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;

public class Renderer {
    private LevelBuilder levelBuilder;

    public void renderEntity(Entity entity) {
        com.almasb.fxgl.entity.Entity newEntity = FXGL.entityBuilder()
                .at(100, 100)
                .view(entity.getImage())
                .with(new EffectComponent())
                .buildAndAttach();
    }

    public void renderMap() {
        return;
    }

}
