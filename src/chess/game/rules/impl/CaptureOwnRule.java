package chess.game.rules.impl;

import chess.game.Board;
import chess.game.Tile;
import chess.game.base.Move;
import chess.game.player.Color;
import chess.game.rules.Rule;

/**
 * Checks if the move is not a capture of own piece.
 */
public class CaptureOwnRule extends Rule {

    public CaptureOwnRule() {
        super(true);
    }

    @Override
    public boolean isBroken(Board board, Color playerColor, Move move, Color turn) {
        Tile tile = board.getTiles()[move.getToY()][move.getToX()];
        if(!tile.isEmpty()) {
            if(tile.getPiece().getColor() == playerColor) {
                return true;
            }
        }

        return false;
    }
}
