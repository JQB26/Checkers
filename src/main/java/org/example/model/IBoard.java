package org.example.model;

import org.example.model.Tile.Tile;
import org.example.model.piece.Piece;

import java.util.ArrayList;

public interface IBoard {
    Tile[][] tiles = new Tile[10][10];
    ArrayList<Piece> whitePieces = new ArrayList<Piece>();
    ArrayList<Piece> blackPieces = new ArrayList<Piece>();

    void generateBoard();
    void movePiece(Piece piece, int x, int y);
}
