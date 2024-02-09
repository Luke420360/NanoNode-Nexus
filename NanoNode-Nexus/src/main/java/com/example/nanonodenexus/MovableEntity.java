package com.example.nanonodenexus;

public class MovableEntity extends Entity {

    private Point direction;
    private int speed;

    protected MovableEntity(Point position, int maxHp, Point dimensions) {
        super(position, maxHp, dimensions);
    }

    public void move() {
        Point pos = this.getPosition();
        this.setPosition(new Point(
                pos.x() + direction.x(),
                pos.y() + direction.y()
        ));
    }
    public Point getDirection() {
        return this.direction;
    }



    public void setDirection(Point direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
