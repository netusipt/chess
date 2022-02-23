
package pieces.impl;

import chess.Position;
import java.util.ArrayList;
import myexceptions.InvalidPositionException;
import pieces.Piece;


public class Pawn extends Piece {
    
    public Pawn(int x, int y) throws InvalidPositionException {
        super(x, y);
    }
    
    @Override
    public String toString() {
        return "P" + this.position;
    }
    
    @Override
    public ArrayList<Position> getPossibleMoves() throws InvalidPositionException {
        return this.getSurrounding();
    }
    
}
