package chess.game.pieces;

import chess.exceptions.InvalidPositionException;
import chess.game.base.Position;
import chess.game.base.Vector;
import chess.game.player.Color;

import java.util.ArrayList;
import java.util.Collection;

public abstract class DirectionPiece extends Piece{

/*    protected ArrayList<Vector> directions;*/

    public DirectionPiece(Color color) {
        super(color);
        /*this.directions = new ArrayList<>();*/
    }

/*    protected ArrayList<Position> getVectorPositions() throws InvalidPositionException {
        ArrayList<Position> positions = new ArrayList<>();

        for (Vector direction : this.directions) {
            positions.addAll(direction.getLinearCombinations(this.position));
        }

        return positions;
    }*/

}
