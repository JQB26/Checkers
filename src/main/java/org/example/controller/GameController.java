package org.example.controller;

import org.example.model.Board;
import org.example.model.MoveValidator;
import org.example.model.piece.enums.PieceColor;
import org.example.model.position.Position;
import org.example.view.GameView;

import java.util.List;
import java.util.Scanner;


public class GameController {
    private Board board;
    private GameView gameView;
    private MoveValidator moveValidator;
    private PieceColor onMove = PieceColor.WHITE;
    private static final GameController INSTANCE = new GameController();


    public static GameController getInstance(){return INSTANCE;}
    public void startGame(){
        this.board = new Board();
        this.moveValidator = new MoveValidator(this.board);
        this.gameView = new GameView();
        this.board.generateBoard();
    }

    public Board getBoard(){return this.board;}

    public GameView getGameView(){return this.gameView;}

    public void changeTurn(){this.onMove = (this.onMove == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;}

    public void move(){
        this.board.printBoard();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter " + this.onMove + "'s next move");
        int pX = scan.nextInt(), pY = scan.nextInt();//from
        List<Position> list = moveValidator.getValidMoves(board.getPiece(pX ,pY));
        System.out.println("Following positions are available:");
        for(Position position: list){
            System.out.print("[" + position.getCurrentX() + "," + position.getCurrentY() + "]");
        }
        System.out.println();
        int nX = scan.nextInt(), nY = scan.nextInt();//to
        this.board.movePiece(this.board.getPiece(pX,pY),nX,nY);
        changeTurn();
    }

    public void run(){
        while(true){
            move();
        }
    }
}
