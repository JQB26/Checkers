package org.example.model;

import org.example.model.piece.Piece;
import org.example.model.piece.enums.PieceColor;

import java.util.ArrayList;

public class Model {
    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;

    public Model() {
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
    }


    public void addPiece(Piece piece){
        if(piece.getPieceColor() == PieceColor.WHITE){
            whitePieces.add(piece);
        }
        else{
            blackPieces.add(piece);
        }
    }



    public ArrayList<Piece> getWhitePieces() {
        return whitePieces;
    }

    public ArrayList<Piece> getBlackPieces() {
        return blackPieces;
    }

}
