package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        URL url = new File("src/main/resources/primary.fxml").toURI().toURL();
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

    public static void main(String[] args) {
        launch();
    }

}