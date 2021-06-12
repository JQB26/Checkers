package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.GameController;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Checkers extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        GameController.getInstance().startGame();
        GameController.getInstance().run();

        prepareStage(stage);

        scene = new Scene(loadFXML("mainview"), 800, 1100);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL url = new File("src/main/java/org/example/view/" + fxml + ".fxml").toURI().toURL();
        return FXMLLoader.load(url);
    }

    public void prepareStage(Stage stage) {
        stage.setHeight(800);
        stage.setWidth(1100);
        stage.setMaxHeight(800);
        stage.setMaxWidth(1100);
        stage.setMinHeight(800);
        stage.setMinWidth(1100);
    }
}
