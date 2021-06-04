package org.example.piece;

public class Position {
    private int currentX, currentY;
    private int prevX, prevY;


    public Position(int currentX, int currentY) {
        this.currentX = currentX;
        this.currentY = currentY;
    }

    public Position(int currentX, int currentY, int prevX, int prev_y) {
        this.currentX = currentX;
        this.currentY = currentY;
        this.prevX = prevX;
        this.prevY = prevY;
    }

    public void MoveTo(int x, int y) {
        setCurrentX(x);
        setCurrentY(y);
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        prevX = this.currentX;
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        prevY = this.currentY;
        this.currentY = currentY;
    }

    public int getPrevX() {
        return prevX;
    }

    public int getPrevY() {
        return prevY;
    }
}
