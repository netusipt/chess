package chess.game.pieces;

import chess.exceptions.InvalidPositionException;
import chess.game.base.Position;
import chess.game.base.Vector;
import java.util.ArrayList;

public abstract class  Piece {

    protected Position position;
    protected String name;

    protected ArrayList<Vector> directions;
    protected ArrayList<Vector> surroundVectors;

    /**
     *
     * @param position
     * @throws InvalidPositionException
     */
    public Piece(Position position) throws InvalidPositionException {
        this.position = position;
        this.directions = new ArrayList<>();
        this.surroundVectors = new ArrayList<>(); //TODO: rename
    }
    
    public void setPosition(Position position) throws InvalidPositionException {
        this.position = position;
    }

    public abstract ArrayList<Position> getPossibleMoves() throws InvalidPositionException;

    protected ArrayList<Position> getVectorPositions() throws InvalidPositionException {
        ArrayList<Position> positions = new ArrayList<>();

        for (Vector direction : this.directions) {
            positions.addAll(direction.getLinearCombinations(this.position));
        }

        return positions;
    }

    protected ArrayList<Position> getSurrounding() throws InvalidPositionException {
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
    }

}
