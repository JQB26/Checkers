package org.example;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import org.example.piece.Piece;
import org.example.piece.enums.PieceColor;
import org.example.piece.enums.PieceType;

public class Controller {
    @FXML
    GridPane boardPane;

    @FXML
    public void initialize() {
        generateBoard();
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
}
