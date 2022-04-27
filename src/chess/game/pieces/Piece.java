package chess.game.pieces;

import chess.exceptions.InvalidPositionException;
import chess.game.base.Position;
import chess.game.base.Vector;
import chess.game.player.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class  Piece {

    protected String name;
    protected Color color;
    protected List<Vector> directions;

    public Piece(Color color) {
        this.color = color;
        this.directions = new ArrayList<>();
    }

    public Color getColor() {
        return this.color;
    }

    public List<Vector> getDirections() {
        return this.directions;
    }

    /*public abstract ArrayList<Position> getPossibleMoves() throws InvalidPositionException;*/
}
