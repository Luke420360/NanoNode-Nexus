package com.example.gamedemo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Map;

public class TowerDenfenseApp extends GameApplication {
    private Entity player;
    private Entity enemy;
    private Entity projectile;

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1600);
        settings.setHeight(900);
        settings.setTitle("Basic Game App");
        settings.setVersion("0.1");
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                player.translateX(5); // move right 5 pixels
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                player.translateX(-5); // move left 5 pixels
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                player.translateY(-5); // move up 5 pixels
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                player.translateY(5); // move down 5 pixels
            }
        }, KeyCode.S);

        input.addAction(new UserAction("Shoot") {
            @Override
            protected void onActionBegin() {
                shoot();
            }
        }, KeyCode.SPACE);
    }

    @Override
    protected void initUI() {
        Text textPixels = new Text("Hello");
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100

        FXGL.getGameScene().addUINode(textPixels); // add to the scene graph
        FXGL.getGameScene().setBackgroundRepeat("Bg.png");
    }

    @Override
    protected void initGame() {
        player = FXGL.entityBuilder()
                .at(300, 300)
                .view("Player.png")
                .buildAndAttach();

        enemy = FXGL.entityBuilder()
                .at(100, 100)
                .view("Knife.png")
                .with(new EffectComponent())
                .buildAndAttach();

        FXGL.animationBuilder()
                .repeatInfinitely()
                .rotate(enemy)
                .from(0)
                .to(360)
                .buildAndPlay();
    }

    private void shoot() {
        double directionX = player.getCenter().getX() - enemy.getCenter().getX();
        double directionY = player.getCenter().getY() - enemy.getCenter().getY();
        double length = Math.sqrt(directionX * directionX + directionY * directionY);

        projectile = FXGL.entityBuilder()
                .at(enemy.getCenter())
                .viewWithBBox("particle.png")
                .with(new ProjectileComponent(new Point2D (directionX, directionY), 200))
                .collidable()
                .build();

        FXGL.getGameWorld().addEntity(projectile);

        FXGL.runOnce(() -> {
            FXGL.getGameWorld().removeEntity(projectile);
        }, Duration.seconds(2));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
