package org.example;


import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.example.piece.Piece;
import org.example.piece.enums.PieceColor;
import org.example.piece.enums.PieceType;

public class View {

    private BorderPane rootPane;
    private GridPane boardPane;
    private Scene mainScene;
    private static final int BOARD_SIZE = 10;

    public View(){
        rootPane = new BorderPane();
        boardPane = new GridPane();
        boardPane.setMaxSize(70 * BOARD_SIZE,70 * BOARD_SIZE);
        boardPane.setStyle("-fx-border-color: black; -fx-border-width: 3");
        rootPane.setCenter(boardPane);
        mainScene = new Scene(rootPane,800,800);
        mainScene.setFill(Color.DARKGRAY);

        BoardGeneration();
    }

    private void BoardGeneration() {
        Piece pawn = null;
        Tile tile;
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
                boardPane.add(tile, col, row);
                if (pawn != null) {
                    boardPane.add(pawn.getPawn(), col, row);
                    GridPane.setHalignment(pawn.getPawn(), HPos.CENTER);
                }
                pawn = null;
            }
        }
    }

    public Scene getMainScene() {
        return mainScene;
    }
}
