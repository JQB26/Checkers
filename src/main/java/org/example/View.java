package org.example;


import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.piece.Piece;
import org.example.piece.enums.PieceColor;
import org.example.piece.enums.PieceType;

public class View {

    private GridPane mainPane;
    private Scene mainScene;

    public View(){
        mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainScene = new Scene(mainPane,800,800);
        mainScene.setFill(Color.DARKGRAY);
        Piece pawn = null;

        for (int row = 1; row<=10; row++) {
            for (int col =1; col <= 10; col++) {
                Rectangle tile = new Rectangle();
                tile.setWidth(70);
                tile.setHeight(70);
                if ((row + col) % 2 == 0) {
                    tile.setFill(Color.DARKGOLDENROD);
                    if (row<=3) {
                        pawn = new Piece(PieceType.PAWN, PieceColor.WHITE, col, row);
                    } else if (row>=8) {
                        pawn = new Piece(PieceType.PAWN, PieceColor.BLACK, col, row);
                    }
                } else {
                    tile.setFill(Color.BURLYWOOD);
                }
                mainPane.add(tile, col, row);
                if (pawn != null) {
                    mainPane.add(pawn.getPawn(), col, row);
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
