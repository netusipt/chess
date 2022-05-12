package chess.game.rules.impl;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.base.Position;
import chess.game.player.Color;
import chess.game.rules.Rule;

public class LimitedBoardRule implements Rule {

    @Override
    public boolean isBroken(Board board, Color playerColor, Move move, Color turn) {
        if(move.getToX() < Position.MIN || move.getToX() > Position.MAX || move.getToY() < Position.MIN || move.getToY() > Position.MAX) {
            return true;
        }
        return false;
    }
}
