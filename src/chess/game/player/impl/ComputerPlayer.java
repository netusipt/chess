package chess.game.player.impl;

import chess.game.Board;
import chess.game.player.Color;
import chess.game.player.Player;
import chess.game.base.Move;
import chess.game.base.Position;
import chess.exceptions.InvalidPositionException;
import chess.game.pieces.Piece;

import java.util.List;
import java.util.Random;

public class ComputerPlayer extends Player {

    private Random random;
    private List<Move> moves;

    public ComputerPlayer(String name, Color color) {
        super(name, color);
        this.random = new Random();
    }

    public Position getChosenPiecePosition(Board board) {
        Position position;
        Piece selectedPiece;
        do {
            position = getRandomPosition();
            selectedPiece = board.getTiles()[position.getY()][position.getX()].getPiece();
        } while (selectedPiece == null || selectedPiece.getColor() != this.getColor());

        return position;
    }

    public void setPossibleMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Move move() throws InvalidPositionException {
        if(this.moves.isEmpty()) {
            return null;
        }
        Move move;
        do {
            move = this.moves.get(this.random.nextInt(this.moves.size()));
        } while (!(move.getToY() != move.getFromX() || move.getToY() != move.getFromY()));

        return move;
    }


    private Position getRandomPosition() throws InvalidPositionException {
        int x = this.random.nextInt(Position.MAX);
        int y = this.random.nextInt(Position.MAX);
        return new Position(x, y);
    }
}
