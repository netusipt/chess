package chess.game.rules.impl;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.player.Color;
import chess.game.rules.Rule;

public class InCheckRule extends Rule {


    public InCheckRule() {
        super(true);
    }

    @Override
    public boolean isBroken(Board board, Color playerColor, Move move, Color turn) {
        return false;
    }
}
