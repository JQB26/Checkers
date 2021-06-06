package org.example;

import javafx.application.Application;
import org.example.model.Board;
import org.example.model.position.Position;

import java.util.List;

public class StartCheckers {

    public static void main(String[] args) throws Exception {
        //Application.launch(Checkers.class);
        Board board = new Board();

        board.movePiece(board.getPiece(2,6), 2, 4);
        board.movePiece(board.getPiece(3,3), 1, 5);
        board.movePiece(board.getPiece(3,7), 2, 6);

        board.printBoard();

        List<Position> list = board.getValidJumps(board.getPiece(1 ,5));

        for(Position position: list){
            System.out.print("[" + position.getCurrentX() + "," + position.getCurrentY() + "]");
        }
        System.out.println();



        List<Position> list2 = board.getValidMoves(board.getPiece(2 ,6));

        for(Position position: list2){
            System.out.print("[" + position.getCurrentX() + "," + position.getCurrentY() + "]");
        }
        System.out.println();
    }
}
