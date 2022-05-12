package chess.game.rules.impl;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.player.Color;
import chess.game.rules.Rule;

public class TurnRule implements Rule {

    @Override
    public boolean isBroken(Board board, Color playerColor, Move move, Color turn) {
        if(turn == playerColor) {
            return false;
        }
        return true;
    }
}
