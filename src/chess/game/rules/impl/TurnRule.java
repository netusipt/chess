package chess.game.rules.impl;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.player.Color;
import chess.game.rules.Rule;

/**
 * Rule prevents player moving in opponent's turn.
 */
public class TurnRule extends Rule {

    public TurnRule() {
        super(true);
    }

    @Override
    public boolean isBroken(Board board, Color playerColor, Move move, Color turn) {
        if(turn == playerColor) {
            return false;
        }
        return true;
    }
}
