package chess.game.player.impl;

import chess.game.player.Color;
import chess.game.base.Move;
import chess.game.base.Position;
import chess.exceptions.InvalidPositionException;
import chess.game.player.Player;

public class HumanPlayer extends Player {

    public HumanPlayer(String name, Color color) {
        super(name, color);
    }

/*    public String move(int pieceInx, int to_x, int to_y) throws InvalidPositionException {
        Position newPosition = new Position(to_x, to_y);
        this.pieces.get(pieceInx).setPosition(newPosition);

        return Move.getNotation(this.pieces.get(pieceInx), newPosition);
    }*/
}
