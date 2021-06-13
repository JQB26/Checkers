package org.example.model.piece.enums;

public enum PieceType {
    PAWN, QUEEN;
    @Override
    public String toString(){
        return this == PAWN ? "pawn" : "queen";
    }
}
