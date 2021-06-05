package org.example.model.piece;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.model.piece.enums.PieceColor;
import org.example.model.piece.enums.PieceType;

public class Piece extends Circle{
    private Circle pawn;
    private PieceType pieceType;
    private PieceColor pieceColor;
    private Position position;
    private boolean isActive;
    private double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;
    private Circle draggedPawn;

    public Circle getPawn() {
        return pawn;
    }

    public Piece(PieceType pieceType, PieceColor pieceColor, int x, int y) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        pawn = new Circle();
        position = new Position(x, y);
        pawn.setRadius(30);
        if(pieceColor == PieceColor.WHITE){
            pawn.setFill(Color.WHITE);
        } else {
            pawn.setFill(Color.BLACK);
        }
        pawn.setCursor(Cursor.HAND);
        pawn.setOnMousePressed(this::pressed);
        pawn.setOnMouseDragged(this::dragged);
        pawn.setOnMouseReleased(this::released);
    }

    public void pressed(MouseEvent e) {
        draggedPawn = (Circle) e.getSource();
        orgSceneX = e.getSceneX();
        orgSceneY = e.getSceneY();
        orgTranslateX = draggedPawn.getTranslateX();
        orgTranslateY = draggedPawn.getTranslateY();
        draggedPawn.toFront();
    }

    public void dragged(MouseEvent e) {
        double offsetX = e.getSceneX() - orgSceneX;
        double offsetY = e.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        draggedPawn.setTranslateX(newTranslateX);
        draggedPawn.setTranslateY(newTranslateY);
    }

    public void released(MouseEvent e) {
        draggedPawn.setTranslateX(0);
        draggedPawn.setTranslateY(0);
        if(((int)e.getSceneX()-30)/70 >= 0 && ((int)e.getSceneY()-40)/70 >= 0 && ((int)e.getSceneX()-30)/70 <= 9 && ((int)e.getSceneY()-40)/70 <= 9) {
            GridPane.setRowIndex(draggedPawn, ((int) e.getSceneY()-40)/70);
            GridPane.setColumnIndex(draggedPawn, ((int) e.getSceneX()-30)/70);
        } else {
            GridPane.setRowIndex(draggedPawn, ((int) orgSceneY-30)/70);
            GridPane.setColumnIndex(draggedPawn, ((int) orgSceneX-40)/70);
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


}