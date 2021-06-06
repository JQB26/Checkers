package org.example.model.Tile;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.model.piece.Piece;
import org.example.model.position.Position;

public class Tile {

    public Rectangle getRectangle() {
        return rectangle;
    }

    private Rectangle rectangle;
    private Position position;
    private boolean visited = false;

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    private Piece piece;
    private Color color;

    public Tile(boolean lightTile, int x, int y, Piece piece) {
        position = new Position(x, y);
        this.piece = piece;

        rectangle = new Rectangle();
        rectangle.setWidth(70);
        rectangle.setHeight(70);

        rectangle.setFill(lightTile ? Color.BURLYWOOD : Color.DARKGOLDENROD);
    }


    public Tile() {
        position = new Position(-1, -1);
    }

    public Position getPosition() {
        return position;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean isVisited(){return visited;}

    public void setVisited(boolean visited){this.visited = visited;}
}
