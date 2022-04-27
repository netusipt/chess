package chess.game.pieces;

import chess.exceptions.InvalidPositionException;
import chess.game.base.Position;
import chess.game.base.Vector;
import chess.game.player.Color;

import java.util.ArrayList;

public abstract class SurroundPiece extends Piece {

    protected ArrayList<Vector> surroundVectors;

    public SurroundPiece(Color color) {
        super(color);
        this.surroundVectors = new ArrayList<>(); //TODO: rename
    }

/*    protected ArrayList<Position> getSurrounding() throws InvalidPositionException {
        ArrayList<Position> positions = new ArrayList<>();

        for (Vector surroundVector : this.surroundVectors) {
            positions.add(new Position(
                    this.position.getX() + surroundVector.getX(),
                    this.position.getY() + surroundVector.getY()
            ));
            positions.add(new Position(
                    this.position.getX() - surroundVector.getX(),
                    this.position.getY() - surroundVector.getY()
            ));
        }

        return positions;
    }*/
}
