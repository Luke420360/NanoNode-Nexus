package com.example.nanonodenexus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public abstract class Entity {

    private String image;
    private int hp;
    private int maxHp;
    private com.almasb.fxgl.entity.Entity gameEntity;

    public void setPosition(Point position) {
        this.position = position;
    }

    public com.almasb.fxgl.entity.Entity getGameEntity() {
        return this.gameEntity;
    }
    private Point position;
    protected Point dimensions;

    protected Entity(Point position) {
        this.position = position;
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
        this.hp -= damage;
    }

}
