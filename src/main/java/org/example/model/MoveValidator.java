package org.example.model;

import org.example.model.piece.Piece;
import org.example.model.piece.enums.PieceColor;
import org.example.model.position.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveValidator {
    Board board;

    public MoveValidator(Board board){
        this.board = board;
    }

    public List<Position> getValidJumps(Position pos, PieceColor col) {
        ArrayList<Position> results = new ArrayList<>();
        int x = pos.getCurrentX();
        int y = pos.getCurrentY();

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
                if (this.board.getTiles()[toX][toY].getPiece() == null && this.board.getTiles()[throughX][throughY].getPiece() != null) {
                    if (this.board.getTiles()[throughX][throughY].getPiece().getPieceColor() != col) {
                        results.add(new Position(toX, toY));
                    }
                }
            }
        }
        return results;
    }

    public void unVisit(){
        Arrays.stream(this.board.getTiles()).forEach(t -> Arrays.stream(t).forEach(tile -> tile.setVisited(true)));
    }



    public List<Position> getValidMoves(Piece piece) {
        ArrayList<Position> results = new ArrayList<>();
        int x = piece.getPosition().getCurrentX();
        int y = piece.getPosition().getCurrentY();

        // one space moves
        if (piece.getPieceColor() == PieceColor.WHITE) {
            if (y <= 8) {
                if (x >= 1) {
                    if (this.board.getTiles()[x - 1][y + 1].getPiece() == null) {
                        results.add(new Position(x - 1, y + 1));
                    }
                }
                if (x <= 8) {
                    if ((this.board.getTiles()[x + 1][y + 1].getPiece() == null)) {
                        results.add(new Position(x + 1, y + 1));
                    }
                }
            }
        } else {
            if (y >= 1) {
                if (x >= 1) {
                    if (this.board.getTiles()[x - 1][y - 1].getPiece() == null) {
                        results.add(new Position(x - 1, y - 1));
                    }
                }
                if (x <= 8) {
                    if ((this.board.getTiles()[x + 1][y - 1].getPiece() == null)) {
                        results.add(new Position(x + 1, y - 1));
                    }
                }
            }
        }
        // >1 space moves
        List<Position> jumps = getValidJumps(piece.getPosition(),piece.getPieceColor());

        return results;
    }

}
