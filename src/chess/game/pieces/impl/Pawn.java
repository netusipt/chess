
package chess.game.pieces.impl;

import chess.game.base.Position;
import java.util.ArrayList;
import chess.exceptions.InvalidPositionException;
import chess.game.pieces.Piece;
import chess.game.pieces.SurroundPiece;
import chess.game.player.Color;

public class Pawn extends SurroundPiece {
    
    public Pawn(Color color) {
        super(color);
    }
    
/*    @Override
    public String toString() {
        return "P" + this.position;
    }
    
    @Override
    public ArrayList<Position> getPossibleMoves() throws InvalidPositionException {
        return this.getSurrounding();
    }*/
    
}
