package org.example;

import javafx.application.Application;
import org.example.controller.GameController;
import org.example.model.Board;
import org.example.model.position.Position;
import org.example.view.GameView;

import java.util.List;

public class StartCheckers {

    public static void main(String[] args) throws Exception {
        //Application.launch(Checkers.class);
        GameController.getInstance().startGame();
        GameController.getInstance().run();
    }
}
