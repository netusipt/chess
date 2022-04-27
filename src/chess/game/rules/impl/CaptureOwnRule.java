package chess.game.rules.impl;

import chess.game.Board;
import chess.game.Tile;
import chess.game.base.Move;
import chess.game.player.Color;
import chess.game.rules.Rule;

public class CaptureOwnRule implements Rule {

    @Override
    public boolean isBroken(Board board, Color playerColor, Move move) {
        Tile tile = board.getTiles()[move.getToY()][move.getToX()];
        if(!tile.isEmpty()) {
            if(tile.getPiece().getColor() == playerColor) {
                return true;
            }
        }

        return false;
    }
}
