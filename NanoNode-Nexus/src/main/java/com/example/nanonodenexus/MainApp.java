package com.example.nanonodenexus;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainApp extends GameApplication {

    // private List<Entity> gameObjects;
    private Game game;
    @Override
    protected void initSettings(GameSettings settings) {
        //settings.setFullScreenFromStart(true);
        //settings.setFullScreenAllowed(true);
        settings.setTitle("NanoNode - Nexus");
        settings.setVersion("0.1");
    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    protected void initGame() {
        Renderer renderer = new Renderer();
        game = new Game(renderer);
        //DefenseTowerSimple tower = new DefenseTowerSimple(new Point(10, 10));
        Player player = new Player(new Point(10, 10));
        game.addEntity(player);
        //game.addEntity(tower);
        //game.update();
    }

    @Override
    protected void initInput() {

        FXGL.getInput().addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                double x = FXGL.getInput().getMouseXWorld();
                double y = FXGL.getInput().getMouseYWorld();
                Entity clickedEntity = game.getEntity((int) x, (int) y);

                if (clickedEntity != null)
                    System.out.printf(clickedEntity.toString());
            }
            else if (event.getButton() == MouseButton.PRIMARY) {
                double x = FXGL.getInput().getMouseXWorld();
                double y = FXGL.getInput().getMouseYWorld();
                System.out.println("Primary button clicked at: " + x + ", " + y);
                DefenseTowerSimple tower = new DefenseTowerSimple(new Point((int)x, (int)y));
                game.addEntity(tower);
                game.removeIron(tower.getCost());


            }
        });
    }
}