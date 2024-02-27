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
    private final Color fogColor = Color.DARKGREY;

    @Override
    public void onAdded() {
        System.out.println("Mounted");
    }

    @Override
    public void onUpdate(double tpf) {
        int killedRobots = FXGL.geti("killedRobots");
        renderFoW(killedRobots);
    }

    private void renderFoW(int killedRobots) {
        int startValue = 0;
        entity.getViewComponent().clearChildren();
        if(killedRobots < 1) {
            startValue = 4;
        }
        else if(killedRobots >= 1) {
            startValue = 10;
        }
        else if(killedRobots <= 15) {
            startValue = 15;
        }
        else {
            startValue = 20;
        }
        for (int row = 0; row < 20; row++) {
            for (int col = 0; col < 20; col++) {
                if(row < startValue && col < startValue) continue;
                FoWCell cell = new FoWCell(new Point(col*48, row*48));
                entity.getViewComponent().addChild(cell.getCell());
            }
        }
    }

}
