package org.example.model.Tile;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.model.piece.Piece;
import org.example.model.piece.Position;

public class Tile extends Rectangle {

    private Position position;
    private Piece piece;

    public Tile(boolean lightTile, int x, int y, Piece piece) {
        position = new Position(x, y);
        setWidth(70);
        setHeight(70);
        setFill(lightTile ? Color.BURLYWOOD : Color.DARKGOLDENROD);
        this.piece = piece;
    }

    public Tile() {
        position = new Position(-1, -1);
    }

    public Position getPosition() {
        return position;
    }
}
