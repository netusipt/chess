package chess.game.rules;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.player.Color;

public abstract class Rule {

    private boolean baseRule;

    public Rule(boolean baseRule) {
        this.baseRule = baseRule;
    }

    public abstract boolean isBroken(Board board, Color playerColor, Move move, Color turn);

    public boolean isBaseRule() {
        return this.baseRule;
    }
}
