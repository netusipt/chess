
package chess.game.pieces.impl;

import chess.game.base.Position;
import chess.game.base.Vector;
import java.util.ArrayList;
import chess.exceptions.InvalidPositionException;
import chess.game.pieces.Piece;


public class Bishop extends Piece {
    
    public Bishop(int x, int y) throws InvalidPositionException {
        super(x, y);
        
        this.directions.add(new Vector(1, 1));
        this.directions.add(new Vector(-1, 1));
    }
    
    @Override
    public String toString() {
        return "B" + this.position;
    }
    
    @Override
    public ArrayList<Position> getPossibleMoves() throws InvalidPositionException {
        return this.getVectorPositions();
    }
    
}
