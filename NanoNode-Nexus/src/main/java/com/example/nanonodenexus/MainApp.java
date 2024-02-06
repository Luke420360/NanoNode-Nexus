package com.example.nanonodenexus;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainApp extends GameApplication {

    private List<Entity> gameObjects;
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setFullScreenFromStart(true);
        settings.setFullScreenAllowed(true);
        settings.setTitle("NanoNode - Nexus");
        settings.setVersion("0.1");
    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    protected void initGame() {
        Renderer renderer = new Renderer();
        Game game = new Game(renderer);
        game.addEntity(new DefenseTowerSimple(new Point(10, 10)));
        game.update();
    }
}