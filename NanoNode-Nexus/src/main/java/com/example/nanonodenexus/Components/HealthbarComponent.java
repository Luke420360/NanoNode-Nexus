package com.example.nanonodenexus.Components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.view.ChildViewComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.ui.ProgressBar;
import com.example.nanonodenexus.EntityType;
import com.example.nanonodenexus.Game;
import javafx.scene.paint.Color;

public class HealthbarComponent extends ChildViewComponent {

    private HealthIntComponent hp;
    private ProgressBar hpBar;

    public HealthbarComponent() {
        super(0, 64, false);

        hpBar = new ProgressBar(false);
        hpBar.setWidth(60);
        hpBar.setHeight(8);
        hpBar.setFill(Color.LIGHTGREEN);
        hpBar.setLabelVisible(false);

        getViewRoot().getChildren().add(hpBar);
    }

    @Override
    public void onAdded() {
        super.onAdded();
        hpBar.maxValueProperty().bind(hp.maxValueProperty());
        hpBar.currentValueProperty().bind(hp.valueProperty());
    }

    public void onUpdate(double tpf) {
        if (hpBar.currentValueProperty().intValue() <= 0) {
            Entity entityToRemove = entity;
            FXGL.getGameWorld().removeEntity(entity);
            Game game = FXGL.geto("gameInstance");
            if(entity.isType(EntityType.ENEMY)) game.addKilledRobot();
            game.removeEntityFromFXGLEntity(entity);
        }
    }
}
