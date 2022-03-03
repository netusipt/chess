package chess.game.base;

import java.util.ArrayList;

import chess.exceptions.InvalidPositionException;

public class Vector {

    private final int x;
    private final int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Position> getLinearCombinations(Position position) throws InvalidPositionException {

        ArrayList<Position> positions = this.getPositionsInDirection(this.x, this.y);
        positions.addAll(this.getPositionsInDirection(-this.x, -this.y));

        return positions;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }

    private ArrayList<Position> getPositionsInDirection(int x, int y) throws InvalidPositionException {
        ArrayList<Position> positions = new ArrayList<>();

        int curX = x;
        int curY = y;

        while (
            curX > Position.MIN &&
            curX <= Position.MAX &&
            curY > Position.MIN &&
            curY <= Position.MAX
        ) {
            
            curX += this.x;
            curY += this.y;

            positions.add(new Position(curX, curY));
        }
        
        return positions;
    }
}
