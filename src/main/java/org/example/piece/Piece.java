package org.example.piece;

import javafx.scene.layout.StackPane;

public class Piece extends StackPane {
    protected PieceType pieceType;
    protected Position position;
    protected boolean isActive;

    public Piece(PieceType pieceType, int x, int y) {
        this.pieceType = pieceType;
        position = new Position(x,y);
    }

    public Piece() {
        position = new Position(-1,-1);
    }

    public void MoveTo(int x, int y){
        position.MoveTo(x,y);
    }

    public Position getPosition() {
        return position;
    }

}
