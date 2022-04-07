package chess.game.base;

import chess.exceptions.InvalidPositionException;

public class Position {
    
    public static final int MAX = 7;
    public static final int MIN = 0;
    
    private int x;
    private int y;

    public Position(int x, int y) throws InvalidPositionException {
        this.setX(x);
        this.setY(y);

        this.y = y;
    }
    
    @Override
    public String toString() {
        return "" + (char)('a' + this.x) + y;    
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }

    public void setX(int x) throws InvalidPositionException {
        if (x >= this.MIN && x <= this.MAX) {
            this.x = x;
        } else {
            throw new InvalidPositionException("X coordinate is out of range (0-7)");
        }
    }

    public void setY(int y) throws InvalidPositionException {
        if (y >= this.MIN && y <= this.MAX) {
            this.y = y;
        } else {
            throw new InvalidPositionException("Y coordinate is out of range (0-7)");
        }
    }

}
