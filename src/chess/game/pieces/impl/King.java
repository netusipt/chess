
package chess.game.pieces.impl;

import chess.game.base.Position;
import chess.game.base.Vector;
import java.util.ArrayList;
import chess.exceptions.InvalidPositionException;
import chess.game.pieces.Piece;


public class King extends Piece {
    
    public King(int x, int y) throws InvalidPositionException {
        super(x, y);
        
        this.surroundVectors.add(new Vector(1, 0));
        this.surroundVectors.add(new Vector(0, 1));
        this.surroundVectors.add(new Vector(1, 1));
        this.surroundVectors.add(new Vector(-1, 1));
    }
    
    @Override
    public String toString() {
        return "K" + this.position;
    }
    
    @Override
    public ArrayList<Position> getPossibleMoves() throws InvalidPositionException {
        return this.getSurrounding();
    }
    
}
