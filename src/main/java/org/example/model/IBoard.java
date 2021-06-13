package org.example.model;

import org.example.model.Tile.Tile;
import org.example.model.piece.Piece;

import java.util.ArrayList;

public interface IBoard {

    void generateBoard();
    void movePiece(Piece piece, int x, int y);
    void removePiece(int x, int y);

}
