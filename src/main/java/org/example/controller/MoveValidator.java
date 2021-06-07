package org.example.controller;

import javafx.geometry.Pos;
import org.example.model.Board;
import org.example.model.Tile.Tile;
import org.example.model.piece.Piece;
import org.example.model.piece.enums.PieceColor;
import org.example.model.position.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MoveValidator {
    Board board;

    public MoveValidator(Board board) {
        this.board = board;
    }

    public List<Position> getValidJumps(Position pos, PieceColor color){
        List<Position> paths = getPaths(pos, color);
        return IntStream.range(0,paths.size())
                .filter(n -> n%2 != 0)
                .mapToObj(paths::get)
                .collect(Collectors.toList());
    }

    public List<Position> getPaths(Position pos, PieceColor color) {
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
                    if (this.board.getTiles()[throughX][throughY].getPiece().getPieceColor() != color) {
                        results.add(new Position(throughX,throughY));
                        results.add(new Position(toX, toY));
                    }
                }
            }
        }
        return results;
    }

    public void unVisit() {
        Arrays.stream(this.board.getTiles()).forEach(t -> Arrays.stream(t).forEach(tile -> tile.setVisited(false)));
    }

    public void getJumpsInternal(List<List<Position>> allJumps, List<Position> currentJumps, Position pos, PieceColor col){
        List<Position> toVisit = this.getPaths(pos,col);
        for(int i = 0; i < toVisit.size(); i+=2) {
            Tile oppColTile = this.board.getTile(toVisit.get(i).getCurrentX(), toVisit.get(i).getCurrentY());
            Tile sameColTile = this.board.getTile(toVisit.get(i + 1).getCurrentX(), toVisit.get(i + 1).getCurrentY());
            if (!oppColTile.isVisited()) {
                oppColTile.setVisited(true);
                currentJumps.add(sameColTile.getPosition());
                allJumps.add(new ArrayList<>(currentJumps));
                getJumpsInternal(allJumps, currentJumps, sameColTile.getPosition(), col);
                currentJumps.remove(currentJumps.size() - 1);
                oppColTile.setVisited(false);
            }
        }
    }

    public List<List<Position>> getValidMoves(Piece piece) {
        ArrayList<Position> results = new ArrayList<>();
        int x = piece.getPosition().getCurrentX();
        int y = piece.getPosition().getCurrentY();

        // >1 space moves
        List<Position> jumps = getValidJumps(piece.getPosition(),piece.getPieceColor());

        List<Position> currJumps = new ArrayList<>();
        List<List<Position>> allJumps = new ArrayList<>();

        for(Position p : jumps){
            currJumps.add(p);
            allJumps.add(currJumps);
            getJumpsInternal(allJumps,currJumps,p,piece.getPieceColor());
        }

        unVisit();

        if (!allJumps.isEmpty()) {
            int max = allJumps.stream().mapToInt(List::size).max().getAsInt();
            List<List<Position>> finalJumps = allJumps.stream().filter(l -> l.size() == max).collect(Collectors.toList());
            finalJumps.forEach(
                    j -> {
                        System.out.println("\nList of moves:");
                        for(Position position: j){
                            System.out.print("[" + position.getCurrentX() + "," + position.getCurrentY() + "]");
                        }
                    }
            );
            return allJumps;
        }

        // one space moves
        if (piece.getPieceColor() == PieceColor.WHITE) {
            if (y <= 8) {
                if (x >= 1) {
                    if (this.board.getTiles()[x - 1][y + 1].getPiece() == null) {
                        allJumps.add(Collections.singletonList(new Position(x - 1, y + 1)));
                    }
                }
                if (x <= 8) {
                    if ((this.board.getTiles()[x + 1][y + 1].getPiece() == null)) {
                        allJumps.add(Collections.singletonList(new Position(x + 1, y + 1)));
                    }
                }
            }
        } else {
            if (y >= 1) {
                if (x >= 1) {
                    if (this.board.getTiles()[x - 1][y - 1].getPiece() == null) {
                        allJumps.add(Collections.singletonList(new Position(x - 1, y - 1)));
                    }
                }
                if (x <= 8) {
                    if ((this.board.getTiles()[x + 1][y - 1].getPiece() == null)) {
                        allJumps.add(Collections.singletonList(new Position(x + 1, y - 1)));
                    }
                }
            }
        }

        allJumps.forEach(
                    j -> {
                        System.out.println("\nList of moves:");
                        for(Position position: j){
                            System.out.print("[" + position.getCurrentX() + "," + position.getCurrentY() + "]");
                        }
                    }
            );

        return allJumps;
    }

}
