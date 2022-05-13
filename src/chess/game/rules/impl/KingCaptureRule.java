package chess.game.rules.impl;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.pieces.Piece;
import chess.game.pieces.impl.King;
import chess.game.player.Color;
import chess.game.rules.Rule;

/**
 * Rule prevents from king being captured.
 */
public class KingCaptureRule extends Rule {

    public KingCaptureRule() {
        super(true);
    }

    @Override
    public boolean isBroken(Board board, Color playerColor, Move move, Color turn) {
        Piece piece = board.getTiles()[move.getToY()][move.getToX()].getPiece();
        if(piece instanceof King) {
            return true;
        }

        return false;
    }
}
