package org.example.controller;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import org.example.model.Model;
import org.example.model.Tile.Tile;
import org.example.model.piece.Piece;
import org.example.model.piece.enums.PieceColor;
import org.example.model.piece.enums.PieceType;

import java.util.ArrayList;


public class Controller {
    @FXML
    GridPane boardPane;

    @FXML
    public void initialize() {
        generateBoard();
        getWhitePieces();
    }

    private void generateBoard() {
        Piece pawn = null;
        Tile tile;
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
                boardPane.add(tile, col, row);
                if (pawn != null) {
                    boardPane.add(pawn.getPawn(), col, row);
                    GridPane.setHalignment(pawn.getPawn(), HPos.CENTER);
                }
                pawn = null;
            }
        }
    }

    private void getWhitePieces() {
        Model model = new Model();
        System.out.println(model.getWhitePieces().size());
    }
}
