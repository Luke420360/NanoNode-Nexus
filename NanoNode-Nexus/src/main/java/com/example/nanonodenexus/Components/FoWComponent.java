package com.example.nanonodenexus.Components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.example.nanonodenexus.Point;
import com.example.nanonodenexus.data.FoWData;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FoWComponent extends Component {
    private final int mazeDimensions = 48;
    private Color fogColor = Color.DARKGREY;

    public FoWComponent( Point position) {

    }

    @Override
    public void onAdded() {
        System.out.println("Mounted");
        Rectangle rect = new Rectangle(192,192, 48, 48);
        rect.setFill(Color.GREEN);
        entity.getViewComponent().addChild(rect);
    }

    @Override
    public void onUpdate(double tpf) {
//        FXGL.geti()

    }
}
