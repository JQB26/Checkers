package org.example.model;

import org.example.model.Tile.Tile;
import org.example.model.piece.Piece;
import org.example.model.piece.enums.PieceColor;
import org.example.model.piece.enums.PieceType;
import org.example.model.position.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board implements IBoard {
    private final Tile[][] tiles;
    private final ArrayList<Piece> whitePieces;
    private final ArrayList<Piece> blackPieces;

    public Board() {
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        tiles = new Tile[10][10];
        generateBoard();
    }

    public void generateBoard() {
        for (int row = 0; row <= 9; row++) {
            for (int col = 0; col <= 9; col++) {
                if ((row + col) % 2 == 0) {
                    Piece piece = null;
                    if (row <= 3) {
                        piece = new Piece(PieceType.PAWN, PieceColor.WHITE, col, row);
                        whitePieces.add(piece);
                    } else if (row >= 6) {
                        piece = new Piece(PieceType.PAWN, PieceColor.BLACK, col, row);
                        blackPieces.add(piece);
                    }
                    tiles[col][row] = new Tile(false, col, row, piece);
                } else {
                    tiles[col][row] = new Tile(true, col, row, null);
                }
            }
        }
    }

    public void printBoard() {
        System.out.println("_____________________");
        for (int row = 0; row <= 9; row++) {
            System.out.print(row + "|");
            for (int col = 0; col <= 9; col++) {
                if (tiles[col][row].getPiece() != null) {
                    if (tiles[col][row].getPiece().getPieceColor() == PieceColor.WHITE) {
                        System.out.print("W|");
                    } else {
                        System.out.print("B|");
                    }
                } else {
                    System.out.print(" |");
                }
            }
            System.out.println();
        }
        System.out.println("_____________________");
    }

    public void movePiece(Piece piece, int x, int y) {
        tiles[piece.getPosition().getCurrentX()][piece.getPosition().getCurrentY()].setPiece(null);
        tiles[x][y].setPiece(piece);
        piece.moveTo(x, y);
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public Piece getPiece(int x, int y) {
        return tiles[x][y].getPiece();
    }

    public Tile[][] getTiles(){return this.tiles;}


    public ArrayList<Piece> getWhitePieces() {
        return whitePieces;
    }

    public ArrayList<Piece> getBlackPieces() {
        return blackPieces;
    }

}