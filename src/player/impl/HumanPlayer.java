package player.impl;

import chess.Color;
import chess.Move;
import chess.Position;
import myexceptions.InvalidPositionException;
import player.Player;

public class HumanPlayer extends Player {

    public HumanPlayer(String name, Color color) {
        super(name, color);
    }

    public String move(int pieceInx, int to_x, int to_y) throws InvalidPositionException {
        Position newPosition = new Position(to_x, to_y);
        this.pieces.get(pieceInx).setPosition(newPosition);

        return Move.getNotation(this.pieces.get(pieceInx), newPosition);
    }
}
