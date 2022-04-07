package chess.game.rules.impl;

import chess.game.rules.Rule;

public class CaptureOwnRule implements Rule {

    @Override
    public boolean isBroken() {
        return false;
    }
}
