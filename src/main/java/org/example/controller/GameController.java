package org.example.controller;

import javafx.geometry.Pos;
import org.example.model.Board;
import org.example.model.piece.Piece;
import org.example.model.piece.enums.PieceColor;
import org.example.model.piece.enums.PieceType;
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
    boolean anyJumps = false;


    public static GameController getInstance(){return INSTANCE;}

    public void startGame(){
        this.board = new Board();
        this.moveValidator = new MoveValidator(this.board);
        this.gameView = new GameView();
        this.board.generateBoard();
    }

    public Board getBoard(){return this.board;}

    public GameView getGameView(){return this.gameView;}

    public void changeTurn(){
        this.onMove = (this.onMove == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
        this.anyJumps=false;
    }


    public int getListsAndMaxMoves(){
        List<Piece> pieces = (this.onMove==PieceColor.WHITE)?this.board.getWhitePieces():this.board.getBlackPieces();
        int maxMoves = 0;
        for(Piece p : pieces) {
//            System.out.println("PIECE: [" + p.getPosition().getCurrentX() + "," + p.getPosition().getCurrentY() + "] moves: ");
            p.setMoveList(this.moveValidator.getValidMoves(p));
//            p.getMoveList().forEach(list -> list.forEach(pos -> System.out.print("["+ pos.getCurrentX() + "," + pos.getCurrentY() + "]")));
//            System.out.println();
            int moves = (p.getMoveList().isEmpty())?0:p.getMoveList().get(0).size();
            p.setMoves(moves);
            if(p.getCanJump())
                anyJumps = true;
            if(moves > maxMoves)
                maxMoves = moves;
        }
        return maxMoves;
    }

    public void move(){
        System.out.println(this.board.getWhitePieces().size());
        System.out.println(this.board.getBlackPieces().size());
        Piece p;
        this.board.printBoard();
        Scanner scan = new Scanner(System.in);
        int maxMove = getListsAndMaxMoves();
        System.out.println(maxMove);
        System.out.println("Enter " + this.onMove + "'s next move");
        int pX,pY;
        do {
        pX = scan.nextInt();//fromX
        pY = scan.nextInt();//fromY
        p = this.board.getPiece(pX,pY);
        }while(p.getPieceColor() != onMove || p.getMoves() != maxMove ||  (anyJumps != p.getCanJump()));
        System.out.println("Following positions are available:");
        this.moveValidator.getValidMoves(p).forEach(
                j -> {
                    System.out.println("\nList of moves:");
                    for(Position position: j){
                        System.out.print("[" + position.getCurrentX() + "," + position.getCurrentY() + "]");
                    }
                }
        );
        List<List<Position>> list = this.moveValidator.getValidMoves(board.getPiece(pX ,pY));
        int nOfMoves = (list.isEmpty())?0:list.get(0).size();
        while(nOfMoves-- > 0) {
            int nX = scan.nextInt(), nY = scan.nextInt();//toX,toY
            if(list.stream().anyMatch(j -> j.contains(new Position(nX,nY)))) {
                this.board.movePiece(this.board.getPiece(pX, pY), nX, nY);
                capture(pX,pY,nX,nY);
                Piece moved = this.board.getPiece(nX, nY);
                pX = nX;
                pY = nY;
                moved.setMoveList(this.moveValidator.getValidMoves(moved));
                promote(this.board.getPiece(pX,pY));
                if(nOfMoves > 0) {
                    this.board.printBoard();
                    this.board.getPiece(pX, pY).getMoveList().forEach(
                            j -> {
                                System.out.println("\nList of moves:");
                                for (Position position : j) {
                                    System.out.print("[" + position.getCurrentX() + "," + position.getCurrentY() + "]");
                                }
                            }
                    );
                }
            }else{
                System.out.println("Wrong move");
                nOfMoves++;
            }
        }
        changeTurn();
    }

    void capture(int Px, int Py, int Nx, int Ny){
        int cX = Px < Nx ? 1 : -1;
        int cY = Py < Ny ? 1 : -1;
        int x = Px + cX, y = Py + cY;
        for(; x != Nx && y != Ny; x+=cX , y+=cY) {
            System.out.println(x + " " + y);
            if (this.board.getPiece(x, y) != null) {
                this.board.removePiece(x,y);
                break;
            }
        }

    }

    void promote(Piece piece){
        if (piece.getPosition().getCurrentY() == 0 || piece.getPosition().getCurrentY() == 9)
            piece.setPieceType(PieceType.QUEEN);
    }

    public void run(){
        while(true){
            move();
        }
    }
}
