package org.example.model.piece.enums;

public enum PieceColor {
    WHITE, BLACK;
    @Override
    public String toString(){
        return this == WHITE ? "white" : "black";
    }
}
