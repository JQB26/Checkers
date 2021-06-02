package org.example.piece;

public class Position {
    private int current_x, current_y;
    private int prev_x, prev_y;


    public Position(int current_x, int current_y) {
        this.current_x = current_x;
        this.current_y = current_y;
    }

    public Position(int current_x, int current_y, int prev_x, int prev_y) {
        this.current_x = current_x;
        this.current_y = current_y;
        this.prev_x = prev_x;
        this.prev_y = prev_y;
    }

    public void MoveTo(int x, int y){
        setCurrent_x(x);
        setCurrent_y(y);
    }

    public int getCurrent_x() {
        return current_x;
    }

    public void setCurrent_x(int current_x) {
        prev_x = this.current_x;
        this.current_x = current_x;
    }

    public int getCurrent_y() {
        return current_y;
    }

    public void setCurrent_y(int current_y) {
        prev_y = this.current_y;
        this.current_y = current_y;
    }

    public int getPrev_x() {
        return prev_x;
    }

    public int getPrev_y() {
        return prev_y;
    }
}
