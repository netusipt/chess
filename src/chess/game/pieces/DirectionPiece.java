package chess.game.pieces;

import chess.exceptions.InvalidPositionException;
import chess.game.base.Position;
import chess.game.base.Vector;
import chess.game.player.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class DirectionPiece extends Piece{

    protected List<Vector> directions;

    public DirectionPiece(int id, Color color, String name) {
        super(id, color, name);
        this.directions = new ArrayList<>();
    }

    public List<Vector> getDirections() {
        return this.directions;
    }

    /*    protected ArrayList<Position> getVectorPositions() throws InvalidPositionException {
        ArrayList<Position> positions = new ArrayList<>();

        for (Vector direction : this.directions) {
            positions.addAll(direction.getLinearCombinations(this.position));
        }

        return positions;
    }*/

}
