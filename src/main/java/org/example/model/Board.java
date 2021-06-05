package org.example.model;

import javafx.scene.paint.Color;
import org.example.model.Tile.Tile;
import org.example.model.piece.Piece;
import org.example.model.piece.enums.PieceColor;
import org.example.model.piece.enums.PieceType;

import java.util.ArrayList;

public class Board implements IBoard{
    private Tile[][] tiles;
    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;

    public Board() {
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        tiles = new Tile[10][10];

        generateBoard();
    }

    public void generateBoard(){
        for(int row = 0; row <= 9; row++){
            for(int col = 0; col <= 9; col++){
                if ((row + col) % 2 == 0) {
                    Piece piece = null;
                    if(row <= 3){
                        piece = new Piece(PieceType.PAWN, PieceColor.WHITE, col, row);
                        whitePieces.add(piece);
                    } else if (row >= 6){
                        piece = new Piece(PieceType.PAWN, PieceColor.BLACK, col, row);
                        blackPieces.add(piece);
                    }
                    tiles[col][row] = new Tile(false, col, row, piece);
                } else{
                    tiles[col][row] = new Tile(true, col, row, null);
                }
            }
        }
    }

    public void movePiece(Piece piece, int x, int y){
        piece.moveTo(x, y);
    }

    public Tile getTile(int x, int y){
        return tiles[x][y];
    }


    public ArrayList<Piece> getWhitePieces() {
        return whitePieces;
    }

    public ArrayList<Piece> getBlackPieces() {
        return blackPieces;
    }

}
