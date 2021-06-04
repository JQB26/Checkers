package org.example.piece;

import javafx.scene.layout.StackPane;
import org.example.piece.enums.PieceColor;
import org.example.piece.enums.PieceType;

public class Piece extends StackPane {
    private PieceType pieceType;
    private PieceColor pieceColor;
    private Position position;
    private boolean isActive;

    public Piece(PieceType pieceType, PieceColor pieceColor, int x, int y) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        position = new Position(x, y);
    }

    public Piece() {
        position = new Position(-1, -1);
    }

    public void moveTo(int x, int y) {
        position.MoveTo(x, y);
    }

    public void promote(){
        pieceType = PieceType.QUEEN;
    }

    public Position getPosition() {
        return position;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
