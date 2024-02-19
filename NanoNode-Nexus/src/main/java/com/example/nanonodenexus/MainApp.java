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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainApp extends GameApplication {

    // private List<Entity> gameObjects;
    private Game game = new Game(new Renderer());
    private Maze maze;
    Text textPixels = new Text("Iron: -");

    @Override
    protected void initSettings(GameSettings settings) {
        //settings.setFullScreenFromStart(true);
        settings.setFullScreenAllowed(true);
        settings.setWidth(960);
        settings.setHeight(960);
        settings.setTitle("NanoNode - Nexus");
        settings.setVersion("0.1");
    }

    @Override
    protected  void onUpdate(double tpf) {
        super.onUpdate(tpf);
        List<Entity> entities = game.getAllEntity();
        for (Entity en : entities) {
            if(en instanceof Enemy) {
                System.out.printf("Enemy");
                ((Enemy) en).onUpdate(tpf);
            }
            else if (en instanceof DefenseTowerSimple) {
                ((DefenseTowerSimple) en).onUpdate(tpf);
            }
            else if (en instanceof Player) {
                ((Player) en).onUpdate(tpf);
            }
            else if (en instanceof EnemyBase) {
                ((EnemyBase) en).onUpdate(tpf);
            }
        }
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("maceCellWidth", 48);
        vars.put("gameInstance", game);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initUI() {
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100
        FXGL.getGameScene().addUINode(textPixels); // add to the scene graph
    }

    @Override
    protected void initGame() {

        maze = new Maze(20, 20);
        List<MazeCell> mazeCells = maze.getCells();
        for (MazeCell mazeCell : mazeCells) {
            int x = mazeCell.getX();
            int y = mazeCell.getY();
            boolean topWall = mazeCell.hasTopWall();
            boolean leftWall = mazeCell.hasLeftWall();
            int maceCellWidth = FXGL.geti("maceCellWidth");

            Rectangle rect = new Rectangle(maceCellWidth, maceCellWidth, Color.GREY);
            FXGL.entityBuilder()
                    .at((x * maceCellWidth) + (topWall ? 2 : 0), (y * maceCellWidth) + (leftWall ? 2 : 0))
                    .view(rect)
                    .with(new EffectComponent())
                    .buildAndAttach();
        }
        Player player = new Player(new Point(10, 10));
        Enemy enemy = new Enemy(new Point(200, 200),250, new Point(40, 40), player);
        EnemyBase enemyBase = new EnemyBase(new Point(500, 500), 900, new Point(40,40));
        game.addEntity(enemyBase);
        game.addEntity(player);
        game.addEntity(enemy);
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
                int maceCellWidth = FXGL.geti("maceCellWidth");
                int x = (int) FXGL.getInput().getMouseXWorld() - (int) FXGL.getInput().getMouseXWorld() % maceCellWidth + 8;
                int y = (int) FXGL.getInput().getMouseYWorld() - (int) FXGL.getInput().getMouseYWorld() % maceCellWidth + 8;
                System.out.println("Primary button clicked at: " + x + ", " + y);

                if (DefenseTowerSimple.cost <= game.getIron()) {
                    DefenseTowerSimple tower = new DefenseTowerSimple(new Point(x, y));
                    game.addEntity(tower);
                    game.removeIron(DefenseTowerSimple.cost);
                    textPixels.setText("Iron: " + game.getIron());
                }
            }
        });
    }
}