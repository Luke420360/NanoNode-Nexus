package com.example.nanonodenexus;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.pathfinding.maze.Maze;
import com.almasb.fxgl.pathfinding.maze.MazeCell;
import com.almasb.fxgl.time.LocalTimer;
import com.example.nanonodenexus.data.EnemyBaseData;
import com.example.nanonodenexus.data.EnemyData;
import com.example.nanonodenexus.data.PlayerData;
import com.example.nanonodenexus.data.TowerData;
import com.example.nanonodenexus.data.*;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.*;

import static com.almasb.fxgl.dsl.FXGL.*;

public class MainApp extends GameApplication {

    // private List<Entity> gameObjects;
    private Game game = new Game();
    private Maze maze;
    private Enemy enemy;
    Text textPixels = new Text("Iron: -");
    Text info = new Text("INFO");
    Text textKilledRobots = new Text("Killed Robots:");
    private AStarGrid aStarGrid = new AStarGrid(20, 20);
    private MazePathfinding pathfinding;
    private double infoTimer = 3;

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
//        FXGL.getGameScene().clearUINodes();
//        textKilledRobots.setTranslateX(50);
//        textKilledRobots.setTranslateY(150);
//        textKilledRobots.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 18));
//        textKilledRobots.setFill(Color.LIGHTSKYBLUE);
//        String killedRobots = String.valueOf(geti("killedRobots"));
//        textKilledRobots.setText("Killed Robots: "+killedRobots);
//        FXGL.getGameScene().addUINode(textKilledRobots);
        if (infoTimer > 0) infoTimer -= tpf;
        if (infoTimer <= 0) info.setText("");

    }

    public void setInfo(String text, int duration) {
        infoTimer = duration;
        info.setText(text);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        Random random = new Random();
        vars.put("clearView",  4);
        vars.put("maceCellWidth", 48);
        vars.put("gameInstance", game);
        vars.put("enemyBasePos", new Point(
                (random.nextInt(10) + 10) * 48,
                (random.nextInt(10) + 10) * 48
        ));
        vars.put("playerPos", new Point(
                0, 0
        ));
        maze = new Maze(20, 20);
        vars.put("maze", maze);
        pathfinding  = new MazePathfinding(maze);
        vars.put("pathFinding", pathfinding);
        vars.put("killedRobots",  0);
        vars.put("path", new ArrayList<>());
        vars.put("setInfo", this);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initUI() {
        info.setFont(Font.font("Verdana", 60));
        double sceneWidth = FXGL.getAppWidth();
        double sceneHeight = FXGL.getAppHeight();
        info.setTranslateX(sceneWidth / 2 - info.getLayoutBounds().getWidth() / 2); // Center align text
        info.setTranslateY(sceneHeight / 2 - info.getLayoutBounds().getHeight() / 2); // Middle of the screen
        FXGL.addUINode(info);

        String killedRobots = String.valueOf(geti("killedRobots"));
        textPixels.setTranslateX(50);
        textPixels.setTranslateY(100);
        FXGL.getGameScene().addUINode(textPixels);
        textKilledRobots.setTranslateX(50);
        textKilledRobots.setTranslateY(150);
        textKilledRobots.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 18));
        textKilledRobots.setFill(Color.LIGHTSKYBLUE);
        textKilledRobots.setText("Killed Robots: "+killedRobots);
        FXGL.getGameScene().addUINode(textKilledRobots);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new NNNFactory());

        pathfinding.renderMaze();

        EnemyData enemyData = getAssetLoader().loadJSON("enemies/enemy.json" , EnemyData.class).orElse(null);
        PlayerData playerData = getAssetLoader().loadJSON("players/player.json" , PlayerData.class).orElse(null);
        EnemyBaseData enemyBaseData = getAssetLoader().loadJSON("enemyBase/enemyBase.json" , EnemyBaseData.class).orElse(null);

        if(enemyData == null || playerData == null || enemyBaseData == null) {
            System.out.printf("Couldn't Load assets");
            return;
        }
        FXGL.set("enemyData", enemyData);


        spawn(
                "EnemyBase",
                new SpawnData()
                        .put("enemyBaseData", enemyBaseData)
                        .put("position", FXGL.geto("enemyBasePos"))
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

        spawn(
                "FoW",
                new SpawnData()
                        .put("position", new Point(0,0))
        );

        setInfo("START", 3);

    }

    @Override
    protected void initInput() {

        FXGL.getInput().addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                int x = (int)  FXGL.getInput().getMouseXWorld();
                int y = (int)  FXGL.getInput().getMouseYWorld();
                addTower(x, y);
                //EnemyData enemyData = getAssetLoader().loadJSON("enemies/enemy.json" , EnemyData.class).orElse(null);
                //spawn(
                //        "Enemy",
                //        new SpawnData()
                //                .put("enemyData", enemyData)
                //                .put("position", new Point(x, y))
                //);

            }
            else if (event.getButton() == MouseButton.PRIMARY) {
                int x = (int)  FXGL.getInput().getMouseXWorld();
                int y = (int)  FXGL.getInput().getMouseYWorld();
            }
        });
    }

    public void addTower (int x, int y) {
        TowerData towerData = getAssetLoader().loadJSON("towers/tower.json" , TowerData.class).orElse(null);
        int placeableRange = FXGL.geti("clearView");
        Point initPoint = game.getPointFromPos( new Point2D(x,y));
        if(initPoint.x() >= placeableRange || initPoint.y() >= placeableRange) return;
        spawn(
                "Tower",
                new SpawnData()
                        .put("towerData", towerData)
                        .put("position", new Point(initPoint.x() * 48, initPoint.y() *48))
        );
    }

    public void pathFinding (int x, int y) {
        int maceCellWidth = FXGL.geti("maceCellWidth");
        List<Entity> allEnemies = game.getAllEntity();
        Enemy enemy = null;
        for (Entity _enemy : allEnemies) {
            if (_enemy instanceof Enemy) {
                enemy = (Enemy) allEnemies.getFirst();
                break;
            }
        }
        if (Objects.isNull(enemy)) { return; }

        Point2D pos = new Point2D(FXGL.getInput().getMouseXWorld() / maceCellWidth, FXGL.getInput().getMouseYWorld() / maceCellWidth);
        Point2D pos2 = new Point2D((float) enemy.getGameEntity().getPosition().getX() / maceCellWidth, enemy.getGameEntity().getPosition().getY() / maceCellWidth);
        List<AStarMazeCell> path = pathfinding.findPath((int)pos2.getX(), (int)pos2.getY(), (int)pos.getX(), (int)pos.getY());
        for (AStarMazeCell cell : path) {
            enemy.setDestination(game.getPosFromPoint(cell.getX(), cell.getY()));
        }
    }
}