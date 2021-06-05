package org.example;

import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.piece.Piece;
import org.example.piece.enums.PieceColor;
import org.example.piece.enums.PieceType;

import java.util.ArrayList;

public class View {

    private BorderPane rootPane;
    private GridPane boardPane;
    private Scene mainScene;
    private static final int BOARD_SIZE = 10;
    private double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;
    private ArrayList<Piece> pawns;
    private ArrayList<Tile> tiles;
    Circle draggedPawn;

    public View(){
        rootPane = new BorderPane();
        boardPane = new GridPane();
        boardPane.setMaxSize(70 * BOARD_SIZE,70 * BOARD_SIZE);
        boardPane.setStyle("-fx-border-color: black; -fx-border-width: 3");
        rootPane.setCenter(boardPane);
        mainScene = new Scene(rootPane,800,800);
        mainScene.setFill(Color.DARKGRAY);
        generateBoard();
    }

    private void generateBoard() {
        Piece pawn = null;
        Tile tile;
        pawns = new ArrayList<>();
        tiles = new ArrayList<>();
        for (int row = 1; row<= BOARD_SIZE; row++) {
            for (int col = 1; col <= BOARD_SIZE; col++) {
                if ((row + col) % 2 == 0) {
                    if (row<=3) {
                        pawn = new Piece(PieceType.PAWN, PieceColor.WHITE, col, row);
                    } else if (row>=BOARD_SIZE-2) {
                        pawn = new Piece(PieceType.PAWN, PieceColor.BLACK, col, row);
                    }
                    tile = new Tile(false, col, row, pawn);
                } else {
                    tile = new Tile(true, col, row, null);
                }
                tiles.add(tile);
                boardPane.add(tile, col, row);
                if (pawn != null) {
                    pawns.add(pawn);
                    boardPane.add(pawn.getPawn(), col, row);
                    pawn.getPawn().setOnMousePressed(this::pressed);
                    pawn.getPawn().setOnMouseDragged(this::dragged);
                    pawn.getPawn().setOnMouseReleased(this::released);
                    GridPane.setHalignment(pawn.getPawn(), HPos.CENTER);
                }
                pawn = null;
            }
        }
    }

    public void pressed(MouseEvent e) {
        orgSceneX = e.getSceneX();
        orgSceneY = e.getSceneY();
        orgTranslateX = ((Circle) e.getSource()).getTranslateX();
        orgTranslateY = ((Circle) e.getSource()).getTranslateY();
        ((Circle) e.getSource()).toFront();
    }

    public void dragged(MouseEvent e) {
        draggedPawn = (Circle) e.getSource();
        double offsetX = e.getSceneX() - orgSceneX;
        double offsetY = e.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        draggedPawn.setTranslateX(newTranslateX);
        draggedPawn.setTranslateY(newTranslateY);
    }

    public void released(MouseEvent e) {
        draggedPawn.setTranslateX(0);
        draggedPawn.setTranslateY(0);
        if(((int)e.getSceneX()+50)/70 >= 1 && ((int)e.getSceneY()+30)/70 >= 1 && ((int)e.getSceneX()+40)/70 <= 10 && ((int)e.getSceneY()+30)/70 <= 10) {
            GridPane.setRowIndex(draggedPawn, ((int) e.getSceneY()+30)/70);
            GridPane.setColumnIndex(draggedPawn, ((int) e.getSceneX()+40)/70);
        } else {
            GridPane.setRowIndex(draggedPawn, ((int) orgSceneY+30)/70);
            GridPane.setColumnIndex(draggedPawn, ((int) orgSceneX+40)/70);
        }
    }

    public void updateBoard(){

    }

    public Scene getMainScene() {
        return mainScene;
    }
}
