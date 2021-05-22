package org.ppodds.core.game;


public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position plus(Position pos) {
        return new Position(x + pos.x, y + pos.y);
    }
}
