package chess.game.pieces;

import chess.exceptions.InvalidPositionException;
import chess.game.base.Position;
import chess.game.base.Vector;
import chess.game.player.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class Piece {

    protected int id;
    protected String name;
    protected Color color;
    protected boolean moved;
    protected boolean captured;

    public Piece(int id, Color color, String name) {
        this.moved = false;
        this.captured = false;
        this.id = id;
        this.color = color;
        this.name = name;
    }

    public Color getColor() {
        return this.color;
    }

    public int getId() {
        return this.id;
    }

    public String getTag() {
        int i = 0, j = 1;
        if(this.name == "knight") {
            i = 1;
            j = 2;
        }
        return color.name().toLowerCase(Locale.ROOT).substring(0, 1) + this.name.toLowerCase(Locale.ROOT).substring(i, j);
    }

    public boolean isMoved() {
        return this.moved;
    }

    public void moved() {
        this.moved = true;
    }

    public void captured() {
        this.captured = true;
    }

    public boolean isCaptured() {
        return this.captured;
    }

    /*public abstract ArrayList<Position> getPossibleMoves() throws InvalidPositionException;*/
}
