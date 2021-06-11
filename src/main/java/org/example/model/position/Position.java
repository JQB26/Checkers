package org.example.model.position;

public class Position {
    private int currentX, currentY;
    private int prevX, prevY;


    public Position(int currentX, int currentY) {
        this.currentX = currentX;
        this.currentY = currentY;
        prevX = -1;
        prevY = -1;
    }

    public Position(int currentX, int currentY, int prevX, int prevY) {
        this.currentX = currentX;
        this.currentY = currentY;
        this.prevX = prevX;
        this.prevY = prevY;
    }

    public void moveTo(int x, int y) {
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

    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        final Position other = (Position) obj;
        boolean sameCurX = (this.currentX == other.currentX);
        boolean sameCurY = (this.currentY == other.currentY);
        return sameCurX && sameCurY;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 15 * hash + this.currentX;
        hash = 15 * hash + this.currentY*2;
        return hash;
    }
}
