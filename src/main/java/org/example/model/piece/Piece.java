package org.example.model.piece;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.Checkers;
import org.example.controller.GameController;
import org.example.model.position.Position;
import org.example.model.piece.enums.PieceColor;
import org.example.model.piece.enums.PieceType;
import org.example.view.GameView;

import java.util.ArrayList;
import java.util.List;

public class Piece{
    private Circle circle;
    private PieceType pieceType;
    private PieceColor pieceColor;
    private Position position;
    private boolean isActive;
    private double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;
    private Circle draggedCircle;
    private Circle prevCircle;
    private GridPane boardPane;
    private List<List<Position>> moveList = new ArrayList<>();
    private int moves;
    private boolean canJump = false;
    private List<List<Position>> legalMoves;
    private int maxMoves;
    private int prevMaxMoves = 1;

    public Circle getCircle() {
        return circle;
    }

    public Piece(PieceType pieceType, PieceColor pieceColor, int x, int y) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        position = new Position(x, y);

        circle = new Circle();
        circle.setRadius(30);
        if(this.pieceColor == PieceColor.WHITE){
            circle.setFill(Color.WHITE);
        } else {
            circle.setFill(Color.BLACK);
        }
        circle.setCursor(Cursor.HAND);
        circle.setOnMousePressed(this::pressed);
        circle.setOnMouseDragged(this::dragged);
        circle.setOnMouseReleased(this::released);
    }

    public void pressed(MouseEvent e) {
        draggedCircle = (Circle) e.getSource();
        orgSceneX = e.getSceneX();
        orgSceneY = e.getSceneY();
        orgTranslateX = draggedCircle.getTranslateX();
        orgTranslateY = draggedCircle.getTranslateY();
        draggedCircle.toFront();
        legalMoves = GameController.getInstance().select(((int) e.getSceneX() - 30) / 70, ((int) e.getSceneY() - 40) / 70);
        maxMoves = (legalMoves == null)?0:legalMoves.size();
        draggedCircle.toFront();
    }

    public void dragged(MouseEvent e) {
        double offsetX = e.getSceneX() - orgSceneX;
        double offsetY = e.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        draggedCircle.setTranslateX(newTranslateX);
        draggedCircle.setTranslateY(newTranslateY);
    }

    public void released(MouseEvent e) {
        draggedCircle.setTranslateX(0);
        draggedCircle.setTranslateY(0);
        if (((int) e.getSceneX() - 30) / 70 >= 0 && ((int) e.getSceneY() - 40) / 70 >= 0 && ((int) e.getSceneX() - 30) / 70 <= 9 && ((int) e.getSceneY() - 40) / 70 <= 9 && legalMoves != null && maxMoves > 0) {
            legalMoves.forEach(
                    j -> {
                        for(Position position: j) {
                            if (position.getCurrentX() == ((int) e.getSceneX() - 30) / 70 && position.getCurrentY() == ((int) e.getSceneY() - 40) / 70) {
                                GridPane.setRowIndex(draggedCircle, ((int) e.getSceneY() - 40) / 70);
                                GridPane.setColumnIndex(draggedCircle, ((int) e.getSceneX() - 30) / 70);
                                GameController.getInstance().move(((int) (orgSceneX - 30) / 70), ((int) (orgSceneY - 40) / 70), ((int) e.getSceneX() - 30) / 70, ((int) e.getSceneY() - 40) / 70);
                                prevCircle = draggedCircle;
                                prevMaxMoves = maxMoves;
                            }
                        }
                    }
            );
        } else {
            GridPane.setRowIndex(draggedCircle, ((int) orgSceneY - 30) / 70);
            GridPane.setColumnIndex(draggedCircle, ((int) orgSceneX - 40) / 70);
        }
    }

    public Piece() {
        position = new Position(-1, -1);
    }

    public void moveTo(int x, int y) {
        position.MoveTo(x, y);
    }

    public void promote(){
        pieceType = PieceType.QUEEN;
    }

    public Position getPosition() {
        return position;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setMoveList(List<List<Position>> moveList){this.moveList = moveList;}

    public List<List<Position>> getMoveList(){return this.moveList;}

    public void setMoves(int moves) { this.moves = moves; }

    public int getMoves() { return moves; }

    public void setCanJump(boolean canJump) { this.canJump = canJump; }

    public boolean getCanJump() { return canJump; }

}