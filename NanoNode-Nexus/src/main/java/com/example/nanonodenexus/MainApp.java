package com.example.nanonodenexus;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setFullScreenFromStart(true);
        settings.setFullScreenAllowed(true);
        settings.setTitle("NanoNode - Nexus");
        settings.setVersion("0.1");
    }

    public static void main(String[] args) {
        launch(args);
    }
}