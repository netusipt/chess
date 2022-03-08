package chess.game.player.impl;

import chess.game.player.Color;
import chess.game.player.Player;
import chess.game.base.Move;
import chess.game.base.Position;
import chess.exceptions.InvalidPositionException;
import chess.game.pieces.Piece;

import java.util.Random;

public class ComputerPlayer extends Player {

    private Random random;

    public ComputerPlayer(String name, Color color) {
        super(name, color);
        this.random = new Random();
    }

    public String move() throws InvalidPositionException {
        Piece piece = this.getRandomPiece();
        Position position = getRandomPosition();
        piece.setPosition(position);

        return Move.getNotation(piece, position);
    }

    private Piece getRandomPiece() {
        return this.pieces.get(this.random.nextInt() * this.pieces.size());
    }

    private Position getRandomPosition() throws InvalidPositionException {
        int x = this.random.nextInt(Position.MAX);
        int y = this.random.nextInt(Position.MAX);
        return new Position(x, y);
    }
}
