package org.example;

import org.example.controller.GameController;

public class StartCheckers {

    public static void main(String[] args) throws Exception {
        //Application.launch(Checkers.class);
        GameController.getInstance().startGame();
        GameController.getInstance().run();
    }
}
