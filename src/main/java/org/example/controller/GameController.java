package org.example.controller;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.example.model.Board;
import org.example.model.piece.Piece;
import org.example.model.piece.enums.PieceColor;
import org.example.model.piece.enums.PieceType;
import org.example.model.position.Position;
import org.example.view.GameView;

import java.util.ArrayList;
import java.util.List;


public class GameController {
    private Board board;
    private GameView gameView;
    private MoveValidator moveValidator;
    private PieceColor onMove = PieceColor.WHITE;
    private GridPane gridPane;
    boolean anyJumps = false;
    private int prevX = 0;
    private int prevY = 0;
    private static final GameController INSTANCE = new GameController();

    public static GameController getInstance(){return INSTANCE;}

    public void startGame(){
        this.board = new Board();
        this.moveValidator = new MoveValidator(this.board);
        this.gameView = new GameView();
        this.board.generateBoard();
        this.gridPane = new GridPane();
    }

    public Board getBoard(){return this.board;}

    public GameView getGameView(){return this.gameView;}

    public void changeTurn(){
        this.onMove = (this.onMove == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
        this.anyJumps=false;
        prevX = 0;
        prevY = 0;
    }

    public void setBoardPane(GridPane gp){
        gridPane = gp;
    }

    public Node getPiece(int x, int y) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            if(GridPane.getRowIndex(node) == y && GridPane.getColumnIndex(node) == x && node.getClass() == Circle.class) {
                result = node;
                break;
            }
        }

        return result;
    }

    public Node getTile(int x, int y) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            if(GridPane.getRowIndex(node) == y && GridPane.getColumnIndex(node) == x && node.getClass() == Rectangle.class) {
                result = node;
                break;
            }
        }

        return result;
    }

    public ArrayList<Node> getHighlight() {
        ArrayList<Node> result = new ArrayList<>();
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            if(node.getClass() == Circle.class && ((Circle) node).getRadius() == 10) {
                result.add(node);
            }
        }

        return result;
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

    public List<List<Position>> select(int pX, int pY) {
        int maxMove = getListsAndMaxMoves();
        Piece p;
        p = this.board.getPiece(pX, pY);
        if(p.getPieceColor() == onMove && p.getMoves() == maxMove && (anyJumps == p.getCanJump())) {
            removeMoveHighlight();
            removeTileHighlight(prevX, prevY);
            addTileHighlight(pX,pY);
            prevX = pX;
            prevY = pY;
            this.moveValidator.getValidMoves(p).forEach(
                    j -> {
                        for (Position position : j) {
                            addMoveHighlight(position.getCurrentX(),position.getCurrentY());
                        }
                    }
            );
            return this.moveValidator.getValidMoves(p);
        } else {
            return null;
        }
    }

    public void move(int pX, int pY, int nX, int nY) {
        removeTileHighlight(pX, pY);
        removeMoveHighlight();
        List<List<Position>> list = this.moveValidator.getValidMoves(board.getPiece(pX ,pY));
        int nOfMoves = (list.isEmpty())?0:list.get(0).size();
        while (nOfMoves --> 0) {
            if(list.stream().anyMatch(j -> j.contains(new Position(nX,nY)))) {
                this.board.movePiece(this.board.getPiece(pX, pY), nX, nY);
                capture(pX,pY,nX,nY);
                Piece moved = this.board.getPiece(nX, nY);
                pX = nX;
                pY = nY;
                moved.setMoveList(this.moveValidator.getValidMoves(moved));
                promote(this.board.getPiece(pX,pY));
                if(nOfMoves > 0) {
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
        this.board.printBoard();
    }

    void capture(int Px, int Py, int Nx, int Ny){
        int cX = Px < Nx ? 1 : -1;
        int cY = Py < Ny ? 1 : -1;
        int x = Px + cX, y = Py + cY;
        for(; x != Nx && y != Ny; x+=cX , y+=cY) {
            System.out.println(x + " " + y);
            if (this.board.getPiece(x, y) != null) {
                gridPane.getChildren().remove(getPiece(x,y));
                this.board.removePiece(x, y);
                break;
            }
        }

    }

    void promote(Piece piece){
        if (piece.getPosition().getCurrentY() == 0 || piece.getPosition().getCurrentY() == 9){
            piece.setPieceType(PieceType.QUEEN);
            ((Circle)gridPane.getChildren().get(gridPane.getChildren().indexOf(getPiece(piece.getPosition().getCurrentX(), piece.getPosition().getCurrentY())))).setStroke(Color.RED);
        }
    }

    void addTileHighlight(int x, int y){
        ((Rectangle)gridPane.getChildren().get(gridPane.getChildren().indexOf(getTile(x,y)))).setFill(Color.color(0.3,0.3,0.1));
    }

    void removeTileHighlight(int x, int y){
        if((x + y) % 2 == 0) {
            ((Rectangle)gridPane.getChildren().get(gridPane.getChildren().indexOf(getTile(x,y)))).setFill(Color.DARKGOLDENROD);
        } else {
            ((Rectangle)gridPane.getChildren().get(gridPane.getChildren().indexOf(getTile(x,y)))).setFill(Color.BURLYWOOD);
        }
    }

    void addMoveHighlight(int x, int y){
        Circle circle = new Circle();
        circle.setFill(Color.color(0.3,0.3,0.1));
        circle.setRadius(10);
        circle.setCursor(Cursor.HAND);
        circle.setOnMousePressed(this::pressed);
        gridPane.add(circle,x,y);
        GridPane.setHalignment(circle, HPos.CENTER);
    }

    void removeMoveHighlight(){
        getHighlight().forEach(
                j-> gridPane.getChildren().remove(j)
        );
    }

    void pressed(MouseEvent e){
        Node node = getPiece(prevX, prevY);
        GridPane.setRowIndex(node,((int) e.getSceneY() - 40) / 70);
        GridPane.setColumnIndex(node,((int) e.getSceneX() - 30) / 70);
        move(prevX, prevY, ((int) e.getSceneX() - 30) / 70, ((int) e.getSceneY() - 40) / 70);
    }

    public void run(){
        this.anyJumps = false;
        this.onMove = PieceColor.WHITE;
        this.board.printBoard();
    }
}
