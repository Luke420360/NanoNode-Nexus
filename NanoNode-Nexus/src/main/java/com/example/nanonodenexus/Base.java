package com.example.nanonodenexus;

public class Base extends Entity{

    private boolean friendly;

    public boolean isFriendly() {
        return friendly;
    }

    protected Base(Point position, int maxHp, Point dimensions) {
        super(position, maxHp, dimensions);
    }
}
