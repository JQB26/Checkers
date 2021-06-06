package org.example.model;

import org.example.model.Tile.Tile;
import org.example.model.piece.Piece;
import org.example.model.piece.enums.PieceColor;
import org.example.model.piece.enums.PieceType;
import org.example.model.position.Position;

import java.util.ArrayList;
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

    public List<Position> getValidMoves(Piece piece) {
        ArrayList<Position> results = new ArrayList<>();
        int x = piece.getPosition().getCurrentX();
        int y = piece.getPosition().getCurrentY();

        // one space moves
        if (piece.getPieceColor() == PieceColor.WHITE) {
            if (y <= 8) {
                if (x >= 1) {
                    if (tiles[x - 1][y + 1].getPiece() == null) {
                        results.add(new Position(x - 1, y + 1));
                    }
                }
                if (x <= 8) {
                    if ((tiles[x + 1][y + 1].getPiece() == null)) {
                        results.add(new Position(x + 1, y + 1));
                    }
                }
            }
        } else {
            if (y >= 1) {
                if (x >= 1) {
                    if (tiles[x - 1][y - 1].getPiece() == null) {
                        results.add(new Position(x - 1, y - 1));
                    }
                }
                if (x <= 8) {
                    if ((tiles[x + 1][y - 1].getPiece() == null)) {
                        results.add(new Position(x + 1, y - 1));
                    }
                }
            }
        }
        return results;
    }

    public List<Position> getValidJumps(Piece piece) {
        ArrayList<Position> results = new ArrayList<>();
        int x = piece.getPosition().getCurrentX();
        int y = piece.getPosition().getCurrentY();

        int[] dirDestX = {-2, 2, -2, 2};
        int[] dirDestY = {-2, -2, 2, 2};
        int[] dirThroughX = {-1, 1, -1, 1};
        int[] dirThroughY = {-1, -1, 1, 1};

        // jumps over opponents' pieces
        for (int dir = 0; dir <= 3; dir++) {
            int toX = x + dirDestX[dir];
            int toY = y + dirDestY[dir];
            int throughX = x + dirThroughX[dir];
            int throughY = y + dirThroughY[dir];

            if (toX >= 0 && toX <= 9 && toY >= 0 && toY <= 9) {
                if (tiles[toX][toY].getPiece() == null && tiles[throughX][throughY].getPiece() != null) {
                    if (tiles[throughX][throughY].getPiece().getPieceColor() != piece.getPieceColor()) {
                        results.add(new Position(toX, toY));
                    }
                }
            }
        }
        return results;
    }

    public void printBoard() {
        System.out.println("_____________________");
        for (int row = 0; row <= 9; row++) {
            System.out.print("|");
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


    public ArrayList<Piece> getWhitePieces() {
        return whitePieces;
    }

    public ArrayList<Piece> getBlackPieces() {
        return blackPieces;
    }

}
