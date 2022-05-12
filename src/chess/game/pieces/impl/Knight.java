
package chess.game.pieces.impl;

import chess.game.base.Position;
import chess.game.base.Vector;
import java.util.ArrayList;
import chess.exceptions.InvalidPositionException;
import chess.game.pieces.Piece;
import chess.game.pieces.SurroundPiece;
import chess.game.player.Color;


public class Knight extends SurroundPiece {
    
    public Knight(int id, Color color){
        super(id, color, "knight");
        
        this.surroundVectors.add(new Vector(1, 2));
        this.surroundVectors.add(new Vector(-1, -2));
        this.surroundVectors.add(new Vector(2, 1));
        this.surroundVectors.add(new Vector(-2, -1));
        this.surroundVectors.add(new Vector(-2, 1));
        this.surroundVectors.add(new Vector(2, -1));
        this.surroundVectors.add(new Vector(-1, 2));
        this.surroundVectors.add(new Vector(1, -2));
    }
    
/*    @Override
    public String toString() {
        return "N" + this.position;
    }
    
    @Override
    public ArrayList<Position> getPossibleMoves() throws InvalidPositionException {
        return this.getSurrounding();
    }*/
    
}
