package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) {
        View manager = new View();
        scene = manager.getMainScene();
        stage.setScene(scene);
        stage.setHeight(800);
        stage.setWidth(800);
        stage.setMaxHeight(800);
        stage.setMaxWidth(800);
        stage.setMinHeight(800);
        stage.setMinWidth(800);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}