
package chess.game.pieces.impl;

import chess.game.base.Position;
import java.util.ArrayList;
import chess.exceptions.InvalidPositionException;
import chess.game.pieces.Piece;

public class Pawn extends Piece {
    
    public Pawn(Position position) throws InvalidPositionException {
        super(position);
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
