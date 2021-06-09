package org.example.controller;

import javafx.geometry.Pos;
import org.example.model.Board;
import org.example.model.Tile.Tile;
import org.example.model.piece.Piece;
import org.example.model.piece.enums.PieceColor;
import org.example.model.piece.enums.PieceType;
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

    public List<Position> getJumps(Position pos, PieceColor color) {
        ArrayList<Position> results = new ArrayList<>();
        int x = pos.getCurrentX(), y = pos.getCurrentY();

        int[] dirDestX = {-2, 2, -2, 2}, dirDestY = {-2, -2, 2, 2}, dirThroughX = {-1, 1, -1, 1}, dirThroughY = {-1, -1, 1, 1};

        // jumps over opponents' pieces
        for (int dir = 0; dir <= 3; dir++) {
            int toX = x + dirDestX[dir],  toY = y + dirDestY[dir],  throughX = x + dirThroughX[dir], throughY = y + dirThroughY[dir];
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

    public List<Position> getJumpsQueen(Position pos, PieceColor color) {
        ArrayList<Position> results = new ArrayList<>();
        int x = pos.getCurrentX(),  y = pos.getCurrentY(), i = x, j = y;
        while(i-- > 0 && j-- > 0){ // i j - throughX, throughY, i + 1, j + 1 - toX, toY
            if(this.board.getPiece(i,j) != null) {
                if (this.board.getPiece(i, j).getPieceColor() == color) { break; }
                else {
                    if(i == 0 || j == 0)
                        break;
                    int throughX = i, throughY = j;
                    while(i-- > 0 && j-- > 0) {
                        if (this.board.getPiece(i, j) != null)
                            break;
                        results.add(new Position(throughX,throughY));
                        results.add(new Position(i,j));
                    }
                    i = 0;
                    j = 0;
                }
            }
        }
        i = x;
        j = y;
        //UP-RIGHT
        while(i++ < 9 && j-- > 0){// i j - throughX, throughY, i - 1, j + 1 - toX, toY
            if(this.board.getPiece(i,j) != null) {
                if (this.board.getPiece(i, j).getPieceColor() == color) { break; }
                else {
                    if(i == 9 || j == 0)
                        break;
                    int throughX = i, throughY = j;
                    while(i++ < 9 && j-- > 0) {
                        if (this.board.getPiece(i, j) != null)
                            break;
                        results.add(new Position(throughX,throughY));
                        results.add(new Position(i,j));
                    }
                    i = 9;
                    j = 0;
                }
            }
        }
        i = x;
        j = y;
        //DOWN-LEFT
        while(i-- > 0 && j++ < 9){// i j - toX, toY, i + 1, j - 1 - throughX, throughY
            if(this.board.getPiece(i,j) != null) {
                if (this.board.getPiece(i, j).getPieceColor() == color) { break; }
                else {
                    if(i == 0 || j == 9)
                        break;
                    int throughX = i, throughY = j;
                    while(i-- > 0 && j++ < 9) {
                        if (this.board.getPiece(i, j) != null)
                            break;
                        results.add(new Position(throughX,throughY));
                        results.add(new Position(i,j));
                    }
                    i = 0;
                    j = 9;
                }
            }
        }
        i = x;
        j = y;
        //DOWN-RIGHT
        while(i++ < 9 && j++ < 9){// i j - toX, toY, i - 1, j - 1 - throughX, throughY
            if(this.board.getPiece(i,j) != null) {
                if (this.board.getPiece(i, j).getPieceColor() == color) { break; }
                else {
                    if(i == 9 || j == 9)
                        break;
                    int throughX = i, throughY = j;
                    while(i++ < 9 && j++ < 9) {
                        if (this.board.getPiece(i, j) != null)
                            break;
                        results.add(new Position(throughX,throughY));
                        results.add(new Position(i,j));
                    }
                    i = 9;
                    j = 9;
                }
            }
        }
        return results;
    }

    public List<Position> trimJumps(Piece p){
        List<Position> jumps = (p.getPieceType() == PieceType.PAWN) ? this.getJumps(p.getPosition(),p.getPieceColor()) : this.getJumpsQueen(p.getPosition(),p.getPieceColor());
        for(int i = 0; i < jumps.size(); i+=2){
            Position visited = jumps.get(i);
            this.board.getTile(visited.getCurrentX(),visited.getCurrentY()).setVisited(true);
        }
        return IntStream.range(0,jumps.size())
                .filter(n -> n%2 != 0)
                .mapToObj(jumps::get)
                .collect(Collectors.toList());
    }

    public void getJumpsInternal(List<List<Position>> allJumps, List<Position> currentJumps, Position pos, PieceColor col, PieceType type){
        List<Position> toVisit = (type == PieceType.PAWN) ? this.getJumps(pos,col) : this.getJumpsQueen(pos,col);
        for(int i = 0; i < toVisit.size(); i+=2) {
            Tile oppColTile = this.board.getTile(toVisit.get(i).getCurrentX(), toVisit.get(i).getCurrentY());
            Tile sameColTile = this.board.getTile(toVisit.get(i + 1).getCurrentX(), toVisit.get(i + 1).getCurrentY());
            if (!oppColTile.isVisited()) {
                oppColTile.setVisited(true);
                currentJumps.add(sameColTile.getPosition());
                allJumps.add(new ArrayList<>(currentJumps));
                getJumpsInternal(allJumps, currentJumps, sameColTile.getPosition(), col, type);
                currentJumps.remove(currentJumps.size() - 1);
                oppColTile.setVisited(false);
            }
        }
    }

    public List<Position> getMovesInOnePiece(Piece piece){
        int x = piece.getPosition().getCurrentX(), y = piece.getPosition().getCurrentY();
        List<Position> oneMoves = new ArrayList<>();
        if (piece.getPieceColor() == PieceColor.WHITE) {
            if (y <= 8) {
                if (x >= 1) {
                    if (this.board.getTiles()[x - 1][y + 1].getPiece() == null) {
                        oneMoves.add(new Position(x - 1, y + 1));
                    }
                }
                if (x <= 8) {
                    if ((this.board.getTiles()[x + 1][y + 1].getPiece() == null)) {
                        oneMoves.add(new Position(x + 1, y + 1));
                    }
                }
            }
        } else {
            if (y >= 1) {
                if (x >= 1) {
                    if (this.board.getTiles()[x - 1][y - 1].getPiece() == null) {
                        oneMoves.add(new Position(x - 1, y - 1));
                    }
                }
                if (x <= 8) {
                    if ((this.board.getTiles()[x + 1][y - 1].getPiece() == null)) {
                        oneMoves.add(new Position(x + 1, y - 1));
                    }
                }
            }
        }
        return oneMoves;
    }

    public List<Position> getMovesInOneQueen(Piece piece){
        int x = piece.getPosition().getCurrentX(), y = piece.getPosition().getCurrentY(), i = x, j = y;
        List<Position> oneMoves = new ArrayList<>();
        while(i-- > 0 && j-- > 0){//UP-LEFT
            if(this.board.getPiece(i,j) != null)
                break;
            oneMoves.add(new Position(i,j));
        }
        i = x;
        j = y;
        while(i++ < 9 && j-- > 0){//UP-RIGHT
            if(this.board.getPiece(i,j) != null)
                break;
            oneMoves.add(new Position(i,j));
        }
        i = x;
        j = y;
        while(i-- > 0 && j++ < 9){//DOWN-LEFT
            if(this.board.getPiece(i,j) != null)
                break;
            oneMoves.add(new Position(i,j));
        }
        i = x;
        j = y;
        while(i++ < 9 && j++ < 9){//DOWN-RIGHT
            if(this.board.getPiece(i,j) != null)
                break;
            oneMoves.add(new Position(i,j));
        }
        return oneMoves;
    }

    public void unVisit() {
        Arrays.stream(this.board.getTiles()).forEach(t -> Arrays.stream(t).forEach(tile -> tile.setVisited(false)));
    }

    public List<List<Position>> getValidMoves(Piece piece) {
        ArrayList<Position> results = new ArrayList<>();
        int x = piece.getPosition().getCurrentX();
        int y = piece.getPosition().getCurrentY();

        // >1 space moves
        List<Position> jumps = trimJumps(piece);
        List<List<Position>> allJumps = new ArrayList<>();

        for(Position p : jumps){
            List<Position> currJumps = new ArrayList<>();
            currJumps.add(p);
            allJumps.add(currJumps);
            getJumpsInternal(allJumps,currJumps,p,piece.getPieceColor(),piece.getPieceType());
        }

        unVisit();

        if (!allJumps.isEmpty()) {
            piece.setCanJump(true);
            int max = allJumps.stream().mapToInt(List::size).max().getAsInt();
            piece.setMoves(max);
            return allJumps.stream().filter(l -> l.size() == max).collect(Collectors.toList());
        }
        // one space moves
        piece.setCanJump(false);
        List<Position> oneMoves = (piece.getPieceType() == PieceType.PAWN) ? getMovesInOnePiece(piece) : getMovesInOneQueen(piece);
        for(Position p : oneMoves)
            allJumps.add(Collections.singletonList(p));
        return allJumps;
    }

}
