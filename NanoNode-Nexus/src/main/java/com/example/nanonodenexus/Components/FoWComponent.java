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
    public void onUpdate(double tpf) {
        int killedRobots = FXGL.geti("killedRobots");
        renderFoW(killedRobots);
    }

    private void renderFoW(int killedRobots) {
        int startValue = 0;
        entity.getViewComponent().clearChildren();
        entity.setZIndex(10);
        if(killedRobots <= 5) {
            startValue = 4;
            FXGL.set("clearView", startValue);
        }
        else if(killedRobots <= 10) {
            startValue = 10;
            FXGL.set("clearView", startValue);
        }
        else if(killedRobots <= 15) {
            startValue = 15;
            FXGL.set("clearView", startValue);
        }
        else {
            startValue = 20;
            FXGL.set("clearView", startValue);
        }


        for (int row = 0; row < 20; row++) {
            for (int col = 0; col < 20; col++) {
                if(row < startValue && col < startValue) continue;
                double alpha = 1;
                if((col <=startValue +1 && row <=startValue +2) || (col <=startValue +2  && row <= startValue +1)) {
                    if(col < row) {
                        alpha = switch (row - startValue +1) {
                            case 1 -> 0.25;
                            case 2 -> 0.5;
                            case 3 -> 0.75;
                            default -> alpha;
                        };
                    }
                    else if (row <= col){
                        alpha = switch (col - startValue +1) {
                            case 1 -> 0.25;
                            case 2 -> 0.5;
                            case 3 -> 0.75;
                            default -> alpha;
                        };
                    }
                }
                FoWCell cell = new FoWCell(new Point(col*48, row*48), alpha);
                entity.getViewComponent().addChild(cell.getCell());
            }
        }
    }

}
