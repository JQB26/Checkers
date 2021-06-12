package org.example.controller;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.example.Checkers;
import org.example.model.Board;
import org.example.model.piece.Piece;
import org.example.model.piece.enums.PieceColor;
import org.example.model.piece.enums.PieceType;
import org.example.model.position.Position;
import org.example.view.GameView;
import org.example.view.Resultview;

import java.io.File;
import java.net.URL;
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
    private int movesLeft = 0;
    private Piece moved = null;
    private static final GameController INSTANCE = new GameController();
    private AnchorPane resultview;

    public static GameController getInstance(){return INSTANCE;}

    public void startGame(){
        this.board = new Board();
        this.moveValidator = new MoveValidator(this.board);
        this.gameView = new GameView();
        this.board.generateBoard();
        this.gridPane = new GridPane();
        resultview = new AnchorPane();
    }

    public Board getBoard(){return this.board;}

    public GameView getGameView(){return this.gameView;}

    public void changeTurn(){
        this.onMove = (this.onMove == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
        this.anyJumps=false;
        prevX = 0;
        prevY = 0;
        movesLeft = getListsAndMaxMoves();
        if(movesLeft == 0){
            Checkers.setRoot("resultview");
            if(this.onMove == PieceColor.WHITE) {
                ((Label) resultview.getChildren().get(0)).setText("THE WINNER IS\n BLACK");
            } else {
                ((Label) resultview.getChildren().get(0)).setText("THE WINNER IS\n WHITE");
            }
        }
        this.board.printBoard();
        moved = null;
    }

    public void setBoardPane(GridPane gp){
        gridPane = gp;
    }

    public void setWinnerLabel(AnchorPane anchorPane) {
        resultview = anchorPane;
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
            p.setMoveList(this.moveValidator.getValidMoves(p));
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
        if(p.getPieceColor() == onMove && p.getMoves() == maxMove && (anyJumps == p.getCanJump()) && moved == null) {
            removeMoveHighlight();
            removeTileHighlight(prevX, prevY);
            addTileHighlight(pX,pY);
            prevX = pX;
            prevY = pY;
            this.moveValidator.getValidMoves(p).forEach(
                    j -> {
                        for (Position position : j) {
                            addMoveHighlight(j.indexOf(position) == 0, position.getCurrentX(),position.getCurrentY());
                        }
                    }
            );
            return this.moveValidator.getValidMoves(p);
        } else if (moved == this.board.getPiece(pX, pY)){
            addTileHighlight(pX,pY);
            prevX = pX;
            prevY = pY;
            moved.getMoveList().forEach(
                    j -> {
                        for (Position position : j) {
                            addMoveHighlight(j.indexOf(position) == 0, position.getCurrentX(),position.getCurrentY());
                        }
                    }
            );
            return moved.getMoveList();
        } else {
            return null;
        }
    }

    public void move(int pX, int pY, int nX, int nY) {
        removeTileHighlight(pX, pY);
        removeMoveHighlight();
        movesLeft--;
        this.board.movePiece(this.board.getPiece(pX, pY), nX, nY);
        capture(pX,pY,nX,nY);
        moved = this.board.getPiece(nX, nY);
        pX = nX;
        pY = nY;
        moved.setMoveList(this.moveValidator.getValidMoves(moved));
        promote(this.board.getPiece(pX,pY));
        select(nX, nY);
        if (movesLeft == 0) {
            removeTileHighlight(nX, nY);
            removeMoveHighlight();
            changeTurn();
        }
    }

    void capture(int Px, int Py, int Nx, int Ny){
        int cX = Px < Nx ? 1 : -1;
        int cY = Py < Ny ? 1 : -1;
        int x = Px + cX, y = Py + cY;
        for(; x != Nx && y != Ny; x+=cX , y+=cY) {
            if (this.board.getPiece(x, y) != null) {
                gridPane.getChildren().remove(getPiece(x,y));
                this.board.removePiece(x, y);
                break;
            }
        }

    }

    void promote(Piece piece){
        if ((piece.getPosition().getCurrentY() == 0 && piece.getPieceColor() == PieceColor.BLACK) || (piece.getPosition().getCurrentY() == 9 && piece.getPieceColor() == PieceColor.WHITE)){
            piece.setPieceType(PieceType.QUEEN);
            Image img;
            if (piece.getPieceColor() == PieceColor.BLACK) {
                img = new Image("file:src/main/resources/black_queen.png");
            } else {
                img = new Image("file:src/main/resources/white_queen.png");
            }
            ((Circle)gridPane.getChildren().get(gridPane.getChildren().indexOf(getPiece(piece.getPosition().getCurrentX(), piece.getPosition().getCurrentY())))).setFill(new ImagePattern(img));

        }
    }

    void addTileHighlight(int x, int y){
        ((Rectangle)gridPane.getChildren().get(gridPane.getChildren().indexOf(getTile(x,y)))).setFill(Color.color(0.3,0.35,0.1));
    }

    void removeTileHighlight(int x, int y){
        if((x + y) % 2 == 0) {
            ((Rectangle)gridPane.getChildren().get(gridPane.getChildren().indexOf(getTile(x,y)))).setFill(Color.DARKGOLDENROD);
        } else {
            ((Rectangle)gridPane.getChildren().get(gridPane.getChildren().indexOf(getTile(x,y)))).setFill(Color.BURLYWOOD);
        }
    }

    void addMoveHighlight(boolean first, int x, int y){
        Circle circle = new Circle();
        if(first){
            circle.setFill(Color.color(0.3,0.35,0.1));
            circle.setRadius(10);
            circle.setCursor(Cursor.HAND);
            circle.setOnMousePressed(this::pressed);
        } else {
            circle.setFill(Color.color(0.3,0.3,0.3));
            circle.setRadius(10);
        }
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
        movesLeft = getListsAndMaxMoves();
    }
}
