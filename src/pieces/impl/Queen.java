
package pieces.impl;

import chess.Position;
import chess.Vector;
import java.util.ArrayList;
import myexceptions.InvalidPositionException;
import pieces.Piece;


public class Queen extends Piece {
    
    public Queen(int x, int y) throws InvalidPositionException {
        super(x, y);
        
        this.directions.add(new Vector(1, 0));
        this.directions.add(new Vector(0, 1));
        this.directions.add(new Vector(1, 1));
        this.directions.add(new Vector(-1, 1));
    }
    
    @Override
    public String toString() {
        return "Q" + this.position;
    }
    
    @Override
    public ArrayList<Position> getPossibleMoves() throws InvalidPositionException {
        return this.getVectorPositions();
    }
    
}
