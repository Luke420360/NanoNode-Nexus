package com.example.nanonodenexus;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.pathfinding.maze.Maze;
import com.almasb.fxgl.pathfinding.maze.MazeCell;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainApp extends GameApplication {

    // private List<Entity> gameObjects;
    private Game game;
    private Maze maze;
    @Override
    protected void initSettings(GameSettings settings) {
        //settings.setFullScreenFromStart(true);
        //settings.setFullScreenAllowed(true);
        settings.setWidth(960);
        settings.setHeight(960);
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
        Player player = new Player(new Point(10, 10));
        game.addEntity(player);
        maze = new Maze(20, 20);
        List<MazeCell> mazeCells = maze.getCells();
        for (MazeCell mazeCell : mazeCells) {
            int x = mazeCell.getX();
            int y = mazeCell.getY();
            boolean topWall = mazeCell.hasTopWall();
            boolean leftWall = mazeCell.hasLeftWall();

            Rectangle rect = new Rectangle(48, 48, Color.GREY);
            FXGL.entityBuilder()
                    .at((x * 48) + (topWall ? 2 : 0), (y * 48) + (leftWall ? 2 : 0))
                    .view(rect)
                    .with(new EffectComponent())
                    .buildAndAttach();

        }
    }

    @Override
    protected void initInput() {

        FXGL.getInput().addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                double x = FXGL.getInput().getMouseXWorld();
                double y = FXGL.getInput().getMouseYWorld();
                Entity clickedEntity = game.getEntity((int) x, (int) y);

                if (clickedEntity != null)
                    clickedEntity.takeDamage(50);
            }
            else if (event.getButton() == MouseButton.PRIMARY) {
                int x = (int) FXGL.getInput().getMouseXWorld() - (int) FXGL.getInput().getMouseXWorld() % 48 + 8;
                int y = (int) FXGL.getInput().getMouseYWorld() - (int) FXGL.getInput().getMouseYWorld() % 48 + 8;
                System.out.println("Primary button clicked at: " + x + ", " + y);
                DefenseTowerSimple tower = new DefenseTowerSimple(new Point(x, y));
                game.addEntity(tower);
                game.removeIron(tower.getCost());


            }
        });
    }
}