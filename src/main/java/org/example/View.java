package org.example;


import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.piece.Piece;
import org.example.piece.enums.PieceColor;
import org.example.piece.enums.PieceType;

public class View {

    private BorderPane rootPane;
    private GridPane boardPane;
    private Scene mainScene;

    public View(){
        rootPane = new BorderPane();
        boardPane = new GridPane();
        boardPane.setMaxSize(700,700);
        boardPane.setStyle("-fx-border-color: black; -fx-border-width: 3");
        rootPane.setCenter(boardPane);
        mainScene = new Scene(rootPane,800,800);
        mainScene.setFill(Color.DARKGRAY);
        Piece pawn = null;
        Tile tile;

        for (int row = 1; row<=10; row++) {
            for (int col =1; col <= 10; col++) {
                if ((row + col) % 2 == 0) {
                    tile = new Tile(false, col, row,null);
                    if (row<=3) {
                        pawn = new Piece(PieceType.PAWN, PieceColor.WHITE, col, row);
                    } else if (row>=8) {
                        pawn = new Piece(PieceType.PAWN, PieceColor.BLACK, col, row);
                    }
                } else {
                    tile = new Tile(true, col, row,null);
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
