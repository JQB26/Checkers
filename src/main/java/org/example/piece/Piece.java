package org.example.piece;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.piece.enums.PieceColor;
import org.example.piece.enums.PieceType;

public class Piece extends Circle {
    private PieceType pieceType;
    private PieceColor pieceColor;
    private Position position;
    private boolean isActive;
    private double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;

    public Circle getPawn() {
        return pawn;
    }

    private Circle pawn;

    public Piece(PieceType pieceType, PieceColor pieceColor, int x, int y) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        position = new Position(x, y);
        pawn = new Circle();
        pawn.setRadius(30);
        if(pieceColor == PieceColor.WHITE){
            pawn.setFill(Color.WHITE);
        } else {
            pawn.setFill(Color.BLACK);
        }

        pawn.setCursor(Cursor.HAND);

        pawn.setOnMousePressed(t -> {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            orgTranslateX = ((Circle) (t.getSource())).getTranslateX();
            orgTranslateY = ((Circle) (t.getSource())).getTranslateY();
            ((Circle) (t.getSource())).toFront();
        });

        pawn.setOnMouseDragged(t -> {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            ((Circle) (t.getSource())).setTranslateX(newTranslateX);
            ((Circle) (t.getSource())).setTranslateY(newTranslateY);

        });

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


}
