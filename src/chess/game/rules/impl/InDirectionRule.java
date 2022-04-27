package chess.game.rules.impl;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.base.Vector;
import chess.game.pieces.Piece;
import chess.game.player.Color;
import chess.game.rules.Rule;

public class InDirectionRule implements Rule {

    @Override
    public boolean isBroken(Board board, Color playerColor, Move move) {
        Piece piece = board.getTiles()[move.getFromY()][move.getFromX()].getPiece();
        for (Vector direction : piece.getDirections()) {
            if(((move.getToX() - move.getFromX()) / direction.getX()) * direction.getY() == move.getToY()) {
                return false;
            }
        }

        return true;
    }
}
