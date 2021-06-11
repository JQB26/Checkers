package org.example;

import javafx.application.Application;
import org.example.controller.GameController;

public class StartCheckers {

    public static void main(String[] args) {
        GameController.getInstance().startGame();
        GameController.getInstance().run();
        Application.launch(Checkers.class);
    }
}
