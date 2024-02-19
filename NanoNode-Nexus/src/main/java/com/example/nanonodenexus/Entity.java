package com.example.nanonodenexus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.component.Component;
import com.example.nanonodenexus.Components.HealthbarComponent;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public abstract class Entity extends Component {

    protected Entity(Point position, int maxHP, Point dimensions) {
        this.position = position;
        this.maxHp = maxHP;
        this.dimensions = dimensions;
        setHp(maxHP);
    }

    private String image;
    private int hp;
    private final int maxHp;
    private com.almasb.fxgl.entity.Entity gameEntity;
    private com.almasb.fxgl.entity.Entity healthBar = null;
    private double healthBarWidth;
    private Rectangle rect;
    private Point position;
    private Point cords;
    protected Point dimensions;

    public void setPosition(Point position) {
        this.position = position;
    }

    public com.almasb.fxgl.entity.Entity getGameEntity() {
        return this.gameEntity;
    }

    public Point getPosition() {
        return this.position;
    }

    public int getMaxHp() {
        return this.maxHp;
    }

    public Point getDimensions() {
        return this.dimensions;
    }

    public String getImage() {
        return this.image;
    }

    public void die() {
        Game game = FXGL.geto("gameInstance");
        game.removeEntity(this);
        FXGL.getGameWorld().removeEntity(this.gameEntity);
    }

    public void setImage(String image) {
        this.image = image;
        Rectangle rct = new Rectangle(dimensions.x(),dimensions.y());
        Image img = new Image("assets/textures/" + image);
        rct.setFill(new ImagePattern(img));

        this.gameEntity = FXGL.entityBuilder()
                .at(this.position.x(), this.position.y())
                .view(rct)
                .with(new EffectComponent())
                .with(new HealthIntComponent(this.hp))
                .with(new HealthbarComponent())
                .collidable()
                .buildAndAttach();
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void takeDamage (int damage) {
        if (this.hp <= damage)
            this.die();
        this.setHp(hp-damage);
        this.getGameEntity().getComponent(HealthIntComponent.class).damage(damage);
    }
}
