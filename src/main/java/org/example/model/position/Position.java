package org.example.model.position;

public class Position {
    private int currentX, currentY;


    public Position(int currentX, int currentY) {
        this.currentX = currentX;
        this.currentY = currentY;
    }

    public void moveTo(int x, int y) {
        setCurrentX(x);
        setCurrentY(y);
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Position other = (Position) obj;
        boolean sameCurX = (this.currentX == other.currentX);
        boolean sameCurY = (this.currentY == other.currentY);
        return sameCurX && sameCurY;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 15 * hash + this.currentX;
        hash = 15 * hash + this.currentY * 2;
        return hash;
    }
}
