package org.example;

import javafx.application.Application;
import org.example.model.Board;

public class StartCheckers {

    public static void main(String[] args) throws Exception {
        //Application.launch(Checkers.class);
        Board board = new Board();
        board.printBoard();
    }
}
