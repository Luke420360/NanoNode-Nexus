package com.example.nanonodenexus;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.pathfinding.maze.Maze;
import com.almasb.fxgl.texture.Texture;
import com.example.nanonodenexus.data.EnemyBaseData;
import com.example.nanonodenexus.data.EnemyData;
import com.example.nanonodenexus.data.PlayerData;
import com.example.nanonodenexus.data.TowerData;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.*;

import static com.almasb.fxgl.dsl.FXGL.*;

public class MainApp extends GameApplication {

    // private List<Entity> gameObjects;
    private Game game = new Game();
    private Circle hoverCircle;
    private Texture towerTexture;
    Label overlayPanelTitle;
    Label enemyKillCountLabel;
    Label levelCountLabel;
    Label ironCount;
    Label towerCostLabel;
    private Maze maze;
    Text info = new Text("INFO");
    private AStarGrid aStarGrid = new AStarGrid(20, 20);
    private MazePathfinding pathfinding;
    private double infoTimer = 3;
    private com.almasb.fxgl.entity.Entity player;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setFullScreenAllowed(true);
        settings.setWidth(960);
        settings.setHeight(960);
        settings.setTitle("NanoNode - Nexus");
        settings.setVersion("1.0");
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

        //Var for the Game
        vars.put("gameInstance", game);
        vars.put("level", 1);
        vars.put("towerCost", 500);

        //Vars for the GameField;
        vars.put("clearView",  4);
        vars.put("maceCellWidth", 48);
        maze = new Maze(20, 20);
        vars.put("maze", maze);
        pathfinding  = new MazePathfinding(maze);
        vars.put("pathFinding", pathfinding);
        vars.put("path", new ArrayList<>());

        //Vars for the EnemyBase
        vars.put("enemyBasePos", new Point(
                (random.nextInt(10) + 10) * 48,
                (random.nextInt(10) + 10) * 48
        ));

        //Vars for the Player
        vars.put("playerPos", new Point(
                0, 0
        ));
        vars.put("iron",  1000);

        //Vars for the Enemy
        vars.put("killedRobots",  0);

        //Vars for the UI
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
        info.setTranslateX(sceneWidth / 2 - info.getLayoutBounds().getWidth() / 2);
        info.setTranslateY(sceneHeight / 2 - info.getLayoutBounds().getHeight() / 2);
        info.setFill(Color.RED);
        FXGL.addUINode(info);

        overlayPanelTitle = new Label("Overview");
        overlayPanelTitle.setTextFill(Color.rgb(9,24,51));
        overlayPanelTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        enemyKillCountLabel = new Label("Enemy Kills: 0");
        enemyKillCountLabel.setTextFill(Color.rgb(19,62,124));
        enemyKillCountLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        ironCount = new Label("Iron: " + geti("iron"));
        ironCount.setTextFill(Color.rgb(19,62,124));
        ironCount.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        levelCountLabel = new Label("Level: " + geti("level"));
        levelCountLabel.setTextFill(Color.rgb(19,62,124));
        levelCountLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        towerCostLabel = new Label("Tower cost: " + geti("towerCost") + " iron");
        towerCostLabel.setTextFill(Color.rgb(19, 62, 124));
        towerCostLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        overlayPanelTitle.setTranslateX(10);
        overlayPanelTitle.setTranslateY(10);
        enemyKillCountLabel.setTranslateX(10);
        enemyKillCountLabel.setTranslateY(35);
        ironCount.setTranslateX(10);
        ironCount.setTranslateY(55);
        levelCountLabel.setTranslateX(10);
        levelCountLabel.setTranslateY(75);
        towerCostLabel.setTranslateX(10);
        towerCostLabel.setTranslateY(95);

        Pane ovlyPnl = new Pane();
        ovlyPnl.setPrefSize(200, 150);
        ovlyPnl.setLayoutX(getAppWidth() -220);
        ovlyPnl.setLayoutY(10);
        ovlyPnl.setStyle("-fx-background-color: rgba(40, 229, 248, 0.7);");
        ovlyPnl.toFront();

        ovlyPnl.getChildren().add(overlayPanelTitle);
        ovlyPnl.getChildren().add(enemyKillCountLabel);
        ovlyPnl.getChildren().add(ironCount);
        ovlyPnl.getChildren().add(levelCountLabel);
        ovlyPnl.getChildren().add(towerCostLabel);

        getGameScene().addUINode(ovlyPnl);

        //OnHoverCircle
        hoverCircle = new Circle(48 * 4, Color.TRANSPARENT);
        hoverCircle.setStroke(Color.GREEN);
        hoverCircle.setStrokeWidth(1);

        towerTexture = FXGL.getAssetLoader().loadTexture("tower.png");
        towerTexture.setFitWidth(48); // Passe die Größe des Bildes an
        towerTexture.setFitHeight(48); // Passe die Größe des Bildes an

        // Berechne die Position des Bildes, um es mittig im Kreis zu platzieren
        double imageX = hoverCircle.getCenterX() - towerTexture.getFitWidth() / 2;
        double imageY = hoverCircle.getCenterY() - towerTexture.getFitHeight() / 2;
        towerTexture.setTranslateX(imageX);
        towerTexture.setTranslateY(imageY);

        Group hoverGroup = new Group(hoverCircle, towerTexture);
        FXGL.getGameScene().addUINode(hoverGroup);
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.rgb(124,0,227));
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
        FXGL.set("enemyBaseData", enemyBaseData);
        FXGL.set("playerData", playerData);

        spawn(
                "EnemyBase",
                new SpawnData()
                        .put("enemyBaseData", enemyBaseData)
                        .put("position", FXGL.geto("enemyBasePos"))
        );

        player = spawn(
                "Player",
                new SpawnData()
                        .put("playerData", playerData)
        );

        spawn(
                "FoW",
                new SpawnData()
                        .put("position", new Point(0,0))
        );

        setInfo("LEVEL " + geti("level"), 3);
    }

    @Override
    protected void initInput() {

        FXGL.getGameScene().getRoot().setOnMouseMoved(event -> {
            hoverCircle.setCenterX(FXGL.getInput().getMouseXWorld());
            hoverCircle.setCenterY(FXGL.getInput().getMouseYWorld());
            towerTexture.setX(FXGL.getInput().getMouseXWorld());
            towerTexture.setY(FXGL.getInput().getMouseYWorld());
            Point initPoint = game.getPointFromPos( new Point2D(FXGL.getInput().getMouseXWorld(),FXGL.getInput().getMouseYWorld()));
            int placeableRange = FXGL.geti("clearView");
            if(initPoint.x() >= placeableRange || initPoint.y() >= placeableRange) hoverCircle.setStroke(Color.DARKRED);
            else if(geti("iron")>= 500) {
                hoverCircle.setStroke(Color.GREEN);
            }
            else {
                hoverCircle.setStroke(Color.INDIANRED);
            }
        });

        onKeyDown(KeyCode.S, "Save", () -> {
            System.out.println("Saving");
            getSaveLoadService().saveAndWriteTask("save1.sav").run();
        });

        FXGL.getInput().addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
           if (event.getButton() == MouseButton.PRIMARY) {
                int x = (int)  FXGL.getInput().getMouseXWorld();
                int y = (int)  FXGL.getInput().getMouseYWorld();
                addTower(x, y);
            }
        });
    }

    public void addTower (int x, int y) {
        int towerCost = 500;

        TowerData towerData = getAssetLoader().loadJSON("towers/tower.json" , TowerData.class).orElse(null);
        int placeableRange = FXGL.geti("clearView");
        Point initPoint = game.getPointFromPos( new Point2D(x,y));
        if(initPoint.x() >= placeableRange || initPoint.y() >= placeableRange) return;
        int playersIron = geti("iron");
        if(playersIron < towerCost) return;
        set("iron", playersIron - towerCost);
        ironCount.setText("Iron: " + geti("iron"));

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

    public void  onEnemyKilled() {
        int killedRobots = geti("killedRobots");
        enemyKillCountLabel.setText("Enemy Kills: " + killedRobots);
        set("iron", geti("iron") + 150);
        ironCount.setText("Iron: " + geti("iron"));
        if(geti("iron") >= 500) {
            hoverCircle.setStroke(Color.GREEN);
        }
    }

    public void setLevelCountLabel(String levelCountLabel) {
        this.levelCountLabel.setText(levelCountLabel);
    }

    public void removePlayer() {
        player.removeFromWorld();
    }

    public void setGameEnd() {
        player.removeFromWorld();
        getGameController().pauseEngine();
    }
}