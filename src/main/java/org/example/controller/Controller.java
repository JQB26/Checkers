package org.example.controller;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import org.example.model.Tile.Tile;
import org.example.model.piece.Piece;
import org.example.model.piece.enums.PieceColor;
import org.example.model.piece.enums.PieceType;

import java.util.ArrayList;

public class Controller {
    @FXML
    GridPane boardPane;
    private double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;
    private ArrayList<Piece> pawns;
    private ArrayList<Tile> tiles;
    Circle draggedPawn;

    @FXML
    public void initialize() {
        generateBoard();
    }

    private void generateBoard() {
        Piece pawn = null;
        Tile tile;
        pawns = new ArrayList<>();
        tiles = new ArrayList<>();
        for (int row = 0; row <= 9; row++) {
            for (int col = 0; col <= 9; col++) {
                if ((row + col) % 2 == 0) {
                    if (row<=3) {
                        pawn = new Piece(PieceType.PAWN, PieceColor.WHITE, col, row);
                    } else if (row >= 6) {
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
        draggedPawn = (Circle) e.getSource();
        orgSceneX = e.getSceneX();
        orgSceneY = e.getSceneY();
        orgTranslateX = draggedPawn.getTranslateX();
        orgTranslateY = draggedPawn.getTranslateY();
        draggedPawn.toFront();
    }

    public void dragged(MouseEvent e) {
        double offsetX = e.getSceneX() - orgSceneX;
        double offsetY = e.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        draggedPawn.setTranslateX(newTranslateX);
        draggedPawn.setTranslateY(newTranslateY);
        System.out.println(e.getSceneX() + ", " + e.getSceneY());
    }

    public void released(MouseEvent e) {
        draggedPawn.setTranslateX(0);
        draggedPawn.setTranslateY(0);
        if(((int)e.getSceneX()-30)/70 >= 0 && ((int)e.getSceneY()-40)/70 >= 0 && ((int)e.getSceneX()-30)/70 <= 9 && ((int)e.getSceneY()-40)/70 <= 9) {
            GridPane.setRowIndex(draggedPawn, ((int) e.getSceneY()-40)/70);
            GridPane.setColumnIndex(draggedPawn, ((int) e.getSceneX()-30)/70);
        } else {
            GridPane.setRowIndex(draggedPawn, ((int) orgSceneY-30)/70);
            GridPane.setColumnIndex(draggedPawn, ((int) orgSceneX-40)/70);
        }
    }

    public void updateBoard(){

    }
}
