package chess.game.pieces;

import chess.game.base.Vector;
import chess.game.player.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class SurroundPiece extends Piece {

    protected List<Vector> surroundVectors;

    public SurroundPiece(int id, Color color, String name) {
        super(id, color, name);
        this.surroundVectors = new ArrayList<>(); //TODO: rename
    }

    public List<Vector> getSurroundVectors() {
        return surroundVectors;
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
