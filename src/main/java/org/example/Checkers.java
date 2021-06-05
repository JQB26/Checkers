package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.view.GameView;

import java.io.File;
import java.net.URL;

public class Checkers extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL url = new File("src/main/java/org/example/view/gameview.fxml").toURI().toURL();
        BorderPane rootPane = FXMLLoader.load(url);
        stage.setScene(new Scene(rootPane, 800, 1100));
        stage.setHeight(800);
        stage.setWidth(1100);
        stage.setMaxHeight(800);
        stage.setMaxWidth(1100);
        stage.setMinHeight(800);
        stage.setMinWidth(1100);
        stage.show();
    }
}
