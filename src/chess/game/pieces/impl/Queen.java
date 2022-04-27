
package chess.game.pieces.impl;

import chess.game.base.Position;
import chess.game.base.Vector;
import java.util.ArrayList;
import chess.exceptions.InvalidPositionException;
import chess.game.pieces.DirectionPiece;
import chess.game.pieces.Piece;
import chess.game.player.Color;


public class Queen extends DirectionPiece {
    
    public Queen(Color color) {
        super(color);
        
        this.directions.add(new Vector(1, 0));
        this.directions.add(new Vector(0, 1));
        this.directions.add(new Vector(1, 1));
        this.directions.add(new Vector(-1, 1));
    }
    
/*    @Override
    public String toString() {
        return "Q" + this.position;
    }
    
    @Override
    public ArrayList<Position> getPossibleMoves() throws InvalidPositionException {
        return this.getVectorPositions();
    }*/
    
}
