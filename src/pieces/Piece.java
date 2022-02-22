package pieces;

import chess.Position;
import chess.Vector;
import java.util.ArrayList;
import myexceptions.InvalidPositionException;

public abstract class Piece {

    protected Position position;
    protected String name;

    protected ArrayList<Vector> directions;
    protected ArrayList<Vector> suroundVectors;

    public Piece(int x, int y) throws InvalidPositionException {
        this.position = new Position(x, y);
        this.directions = new ArrayList<>();
        this.suroundVectors = new ArrayList<>(); //TODO: rename
    }
    
    public void setPosition(int x, int y) throws InvalidPositionException {
        this.position.setX(x);
        this.position.setY(y);
    }

    public abstract ArrayList<Position> getPossibleMoves() throws InvalidPositionException;

    protected ArrayList<Position> getVectorPositions() throws InvalidPositionException {
        ArrayList<Position> positions = new ArrayList<>();

        for (Vector direction : this.directions) {
            positions.addAll(direction.getLinearCombinations(this.position));
        }

        return positions;
    }

    protected ArrayList<Position> getSuroundings() throws InvalidPositionException {
        ArrayList<Position> positions = new ArrayList<>();

        for (Vector suroundVector : this.suroundVectors) {
            positions.add(new Position(
                   this.position.getX() + suroundVector.getX(),
                   this.position.getY() + suroundVector.getY()
            ));
            positions.add(new Position(
                   this.position.getX() - suroundVector.getX(),
                   this.position.getY() - suroundVector.getY()
            ));
        }
        
        return positions;
    }

}
