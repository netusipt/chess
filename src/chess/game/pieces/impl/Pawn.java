
package chess.game.pieces.impl;

import java.util.ArrayList;
import java.util.List;
import chess.game.base.Vector;
import chess.game.pieces.SurroundPiece;
import chess.game.player.Color;

public class Pawn extends SurroundPiece {

    private boolean movedFirstTime;
    private boolean twoTiles;
    private List<Vector> captureVectors;
    
    public Pawn(int id, Color color) {
        super(id, color, "pawn");
        this.movedFirstTime = false;
        this.twoTiles = false;
        this.captureVectors = new ArrayList<>();

        if (color == Color.WHITE) {
            this.surroundVectors.add(new Vector(0, -2));
            this.surroundVectors.add(new Vector(0, -1));
            this.captureVectors.add(new Vector(1, -1));
            this.captureVectors.add(new Vector(-1, -1));
        } else {
            this.surroundVectors.add(new Vector(0, 2));
            this.surroundVectors.add(new Vector(0, 1));
            this.captureVectors.add(new Vector(1, 1));
            this.captureVectors.add(new Vector(-1, 1));
        }



    }

    public void setMovedFirstTime(boolean movedFirstTime) {
        this.movedFirstTime = movedFirstTime;

        if(movedFirstTime) {
            List<Vector> newVectors = new ArrayList<>();
            for (Vector surroundVector : this.surroundVectors) {
                if(surroundVector.getY() == -1 || surroundVector.getY() == 1) {
                    newVectors.add(surroundVector);
                }
            }
            this.surroundVectors = newVectors;
        }
    }

    public boolean isMovedFirstTime() {
        return this.movedFirstTime;
    }

    public boolean setTwoTiles(boolean firstMoveTwoTiles) {
        return this.twoTiles = firstMoveTwoTiles;
    }

    public boolean isEmPassantPossible() {
        if(this.movedFirstTime && this.twoTiles) {
            return true;
        }

        return false;
    }

    public List<Vector> getCaptureVectors() {
        return this.captureVectors;
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
