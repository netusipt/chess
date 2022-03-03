
package chess.game.pieces.impl;

import chess.game.base.Position;
import chess.game.base.Vector;
import java.util.ArrayList;
import chess.exceptions.InvalidPositionException;
import chess.game.pieces.Piece;


public class Rook extends Piece {
    
    public Rook(int x, int y) throws InvalidPositionException {
        super(x, y);
        this.name = "rook";
        
        this.directions.add(new Vector(1, 0));
        this.directions.add(new Vector(0, 1));
    }
    
    @Override
    public String toString() {
        return "R" + this.position;
    }

    @Override
    public ArrayList<Position> getPossibleMoves() throws InvalidPositionException {
        return this.getVectorPositions();
    }
    
    
}
