
package chess.game.pieces.impl;

import chess.game.base.Position;
import chess.game.base.Vector;
import java.util.ArrayList;
import chess.exceptions.InvalidPositionException;
import chess.game.pieces.SurroundPiece;
import chess.game.player.Color;


public class King extends SurroundPiece {
    
    public King(int id, Color color) {
        super(id, color, "king");
        
        this.surroundVectors.add(new Vector(1, 0));
        this.surroundVectors.add(new Vector(-1, 0));
        this.surroundVectors.add(new Vector(0, 1));
        this.surroundVectors.add(new Vector(0, -1));
        this.surroundVectors.add(new Vector(1, 1));
        this.surroundVectors.add(new Vector(-1, -1));
        this.surroundVectors.add(new Vector(-1, 1));
        this.surroundVectors.add(new Vector(1, -1));
    }
    
/*    @Override
    public String toString() {
        return "K" + this.position;
    }
    
    @Override
    public ArrayList<Position> getPossibleMoves() throws InvalidPositionException {
        return this.getSurrounding();
    }*/
    
}
