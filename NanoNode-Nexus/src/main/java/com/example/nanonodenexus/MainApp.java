package com.example.nanonodenexus;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.pathfinding.Pathfinder;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.pathfinding.astar.AStarPathfinder;
import com.almasb.fxgl.pathfinding.maze.Maze;
import com.almasb.fxgl.pathfinding.maze.MazeCell;
import com.example.nanonodenexus.data.EnemyBaseData;
import com.example.nanonodenexus.data.EnemyData;
import com.example.nanonodenexus.data.PlayerData;
import com.example.nanonodenexus.data.TowerData;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.*;

public class MainApp extends GameApplication {

    // private List<Entity> gameObjects;
    private Game game = new Game(new Renderer());
    private Maze maze;
    private Enemy enemy;
    Text textPixels = new Text("Iron: -");
    private AStarGrid aStarGrid = new AStarGrid(20, 20);
    private MazePathfinding pathfinding;

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
        getGameWorld().addEntityFactory(new NNNFactory());
        maze = new Maze(20, 20);
        pathfinding  = new MazePathfinding(maze);
        List<MazeCell> mazeCells = maze.getCells();
        for (MazeCell mazeCell : mazeCells) {
            int x = mazeCell.getX();
            int y = mazeCell.getY();
            boolean topWall = mazeCell.hasTopWall();
            boolean leftWall = mazeCell.hasLeftWall();
            int maceCellWidth = FXGL.geti("maceCellWidth");

            Rectangle rect = new Rectangle(maceCellWidth - (leftWall ? 1 : 0), maceCellWidth - (topWall ? 1 : 0), Color.GREY);
            FXGL.entityBuilder()
                    .at((x * maceCellWidth) + (leftWall ? 1 : 0), (y * maceCellWidth) + (topWall ? 1 : 0))
                    .view(rect)
                    .with(new EffectComponent())
                    .buildAndAttach();
        }

        EnemyData enemyData = getAssetLoader().loadJSON("enemies/enemy.json" , EnemyData.class).orElse(null);
        PlayerData playerData = getAssetLoader().loadJSON("players/player.json" , PlayerData.class).orElse(null);
        EnemyBaseData enemyBaseData = getAssetLoader().loadJSON("enemyBase/enemyBase.json" , EnemyBaseData.class).orElse(null);

        if(enemyData == null || playerData == null || enemyBaseData == null) {
            System.out.printf("Couldn't Load assets");
            return;
        }

        // EnemyBase
        Random random = new Random();
        Point enemyBaseDim = new Point(
        (random.nextInt(11) + 10) * 48,
       (random.nextInt(11) + 10) * 48
        );

        spawn(
                "EnemyBase",
                new SpawnData()
                        .put("enemyBaseData", enemyBaseData)
                        .put("position", enemyBaseDim)
        );

        spawn(
                "Player",
                new SpawnData()
                        .put("playerData", playerData)
        );

        spawn(
                "Enemy",
                new SpawnData()
                        .put("enemyData", enemyData)
        );
    }

    @Override
    protected void initInput() {

        FXGL.getInput().addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                int x = (int)  FXGL.getInput().getMouseXWorld();
                int y = (int)  FXGL.getInput().getMouseYWorld();

                EnemyData enemyData = getAssetLoader().loadJSON("enemies/enemy.json" , EnemyData.class).orElse(null);
                spawn(
                        "Enemy",
                        new SpawnData()
                                .put("enemyData", enemyData)
                                .put("position", new Point(x, y))
                );

            }
            else if (event.getButton() == MouseButton.PRIMARY) {
                int maceCellWidth = FXGL.geti("maceCellWidth");

                //List<com.almasb.fxgl.entity.Entity> allEnemies = getGameWorld().getEntitiesByType(EntityType.ENEMY);
                List<Entity> allEnemies = game.getAllEntity();
                Enemy enemy = null;
                for (Entity _enemy : allEnemies) {
                    if (_enemy instanceof Enemy) {
                        enemy = (Enemy) allEnemies.getFirst();
                        break;
                    }
                }
                if (Objects.isNull(enemy)) { return; }
                System.out.printf("enemy found");

                Point2D pos = new Point2D(FXGL.getInput().getMouseXWorld() / maceCellWidth, FXGL.getInput().getMouseYWorld() / maceCellWidth);
                Point2D pos2 = new Point2D((float) enemy.getGameEntity().getPosition().getX() / maceCellWidth, enemy.getGameEntity().getPosition().getY() / maceCellWidth);
                List<AStarMazeCell> path = pathfinding.findPath((int)pos2.getX(), (int)pos2.getY(), (int)pos.getX(), (int)pos.getY());
                //List<AStarMazeCell> neighbors = pathfinding.getNeighbors(new AStarMazeCell(new MazeCell((int)pos.getX(), (int)pos.getY())));
                for (AStarMazeCell cell : path) {
                    enemy.setDestination(game.getPosFromPoint(cell.getX(), cell.getY()));
                }

                //if (DefenseTowerSimple.cost <= game.getIron()) {
                //    DefenseTowerSimple tower = new DefenseTowerSimple(new Point(x, y));
                //    game.addEntity(tower);
                //    game.removeIron(DefenseTowerSimple.cost);
                //    textPixels.setText("Iron: " + game.getIron());
                //}
            }
        });
    }
}