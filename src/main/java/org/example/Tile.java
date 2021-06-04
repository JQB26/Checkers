package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.piece.Piece;

public class Tile extends Rectangle {

    private Piece piece;

    public Tile(boolean lightTile, int x, int y, Piece piece) {
        setWidth(70);
        setHeight(70);
        setFill(lightTile ? Color.BURLYWOOD : Color.DARKGOLDENROD);
        this.piece = piece;
    }
}
