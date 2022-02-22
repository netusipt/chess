
package pieces.impl;

import chess.Position;
import chess.Vector;
import java.util.ArrayList;
import myexceptions.InvalidPositionException;
import pieces.Piece;


public class King extends Piece {
    
    public King(int x, int y) throws InvalidPositionException {
        super(x, y);
        
        this.suroundVectors.add(new Vector(1, 0));
        this.suroundVectors.add(new Vector(0, 1));
        this.suroundVectors.add(new Vector(1, 1));
        this.suroundVectors.add(new Vector(-1, 1));
    }
    
    @Override
    public String toString() {
        return "K" + this.position;
    }
    
    @Override
    public ArrayList<Position> getPossibleMoves() throws InvalidPositionException {
        return this.getSuroundings();
    }
    
}
