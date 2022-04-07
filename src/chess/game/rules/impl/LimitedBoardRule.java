package chess.game.rules.impl;

import chess.game.rules.Rule;

public class LimitedBoardRule implements Rule {

    @Override
    public boolean isBroken() {
        return false;
    }
}
