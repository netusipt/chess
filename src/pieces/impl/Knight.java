
package pieces.impl;

import chess.Position;
import chess.Vector;
import java.util.ArrayList;
import myexceptions.InvalidPositionException;
import pieces.Piece;


public class Knight extends Piece {
    
    public Knight(int x, int y) throws InvalidPositionException {
        super(x, y);
        
        this.suroundVectors.add(new Vector(1, 2));
        this.suroundVectors.add(new Vector(2, 1));
        this.suroundVectors.add(new Vector(-2, 1));
        this.suroundVectors.add(new Vector(-1, 2));
    }
    
    @Override
    public String toString() {
        return "N" + this.position;
    }
    
    @Override
    public ArrayList<Position> getPossibleMoves() throws InvalidPositionException {
        return this.getSuroundings();
    }
    
}
