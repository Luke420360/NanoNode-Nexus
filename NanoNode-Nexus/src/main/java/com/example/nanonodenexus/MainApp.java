package com.example.nanonodenexus;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.SpawnData;
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
import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

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

        EnemyData enemyData = getAssetLoader().loadJSON("enemies/enemy.json" , EnemyData.class).orElse(null);
        PlayerData playerData = getAssetLoader().loadJSON("players/player.json" , PlayerData.class).orElse(null);
        EnemyBaseData enemyBaseData = getAssetLoader().loadJSON("enemyBase/enemyBase.json" , EnemyBaseData.class).orElse(null);

        if(enemyData == null || playerData == null || enemyBaseData == null) {
            System.out.printf("Couldn't Load assets");
            return;
        }

        spawn(
                "Player",
                new SpawnData()
                        .put("playerData", playerData)
        );

        spawn(
                "EnemyBase",
                new SpawnData()
                        .put("enemyBaseData", enemyBaseData)
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
                double x = FXGL.getInput().getMouseXWorld();
                double y = FXGL.getInput().getMouseYWorld();
                com.almasb.fxgl.entity.Entity clickedEntity = game.getEntity((int) x, (int) y, EntityType.ENEMY);
                if (clickedEntity != null) {
                    var hp = clickedEntity.getComponent(HealthIntComponent.class);
                    hp.damage(50);
                    if(hp.isZero()) clickedEntity.removeFromWorld();
                }

            }
            else if (event.getButton() == MouseButton.PRIMARY) {
                int maceCellWidth = FXGL.geti("maceCellWidth");
                int x = (int) FXGL.getInput().getMouseXWorld() - (int) FXGL.getInput().getMouseXWorld() % maceCellWidth;
                int y = (int) FXGL.getInput().getMouseYWorld() - (int) FXGL.getInput().getMouseYWorld() % maceCellWidth;
                if (game.getEntity(x,y, EntityType.TOWER) != null) return;
                if (DefenseTowerSimple.cost <= game.getIron()) {
                    TowerData towerData = getAssetLoader().loadJSON("towers/tower.json" , TowerData.class).orElse(null);
                    spawn(
                            "Tower",
                            new SpawnData()
                                    .put("towerData", towerData)
                                    .put("position", new Point(x, y))
                    );
                    game.removeIron(DefenseTowerSimple.cost);
                    textPixels.setText("Iron: " + game.getIron());
                }
            }
        });
    }
}