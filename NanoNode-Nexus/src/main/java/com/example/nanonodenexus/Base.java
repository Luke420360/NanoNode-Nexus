package com.example.nanonodenexus;

public class Base extends Entity{

    private boolean friendly;

    public boolean isFriendly() {
        return friendly;
    }

    protected Base(Point position) {
        super(position);
    }
}
