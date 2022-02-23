package player.impl;

import chess.Color;
import chess.Move;
import chess.Position;
import myexceptions.InvalidPositionException;
import pieces.Piece;
import player.Player;

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
