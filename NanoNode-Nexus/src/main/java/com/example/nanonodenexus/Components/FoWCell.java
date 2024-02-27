package com.example.nanonodenexus.Components;

import com.almasb.fxgl.entity.component.Component;
import com.example.nanonodenexus.Point;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FoWCell extends Node {
    private final Rectangle cell;

    public FoWCell(Point position) {
        int width = 48;
        cell = new Rectangle(position.x(),position.y(), width, width);
        cell.setFill(Color.rgb(20,20,20,0.99));
    }

    public Rectangle getCell() {
        return cell;
    }

}
