package com.example.nanonodenexus.Components;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.view.ChildViewComponent;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.scene.paint.Color;

public class HealthbarComponent extends ChildViewComponent {

    private HealthIntComponent hp;
    private ProgressBar hpBar;

    public HealthbarComponent() {
        super(0, 64, false);

        hpBar = new ProgressBar(false);
        hpBar.setWidth(60);
        hpBar.setHeight(8);
        hpBar.setFill(Color.LIGHTGREEN);
        hpBar.setLabelVisible(false);

        getViewRoot().getChildren().add(hpBar);
    }

    @Override
    public void onAdded() {
        super.onAdded();
        hpBar.maxValueProperty().bind(hp.maxValueProperty());
        hpBar.currentValueProperty().bind(hp.valueProperty());
    }
}
