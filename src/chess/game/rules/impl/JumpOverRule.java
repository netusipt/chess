package chess.game.rules.impl;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.player.Color;
import chess.game.rules.Rule;

public class JumpOverRule implements Rule {

    @Override
    public boolean isBroken(Board board, Color playerColor, Move move)
    {
        int y = move.getFromY();
        int x = move.getFromX();
        do {
            if(!board.getTiles()[++y][++x].isEmpty()) {
                return true;
            }
        } while (x != move.getToX() - 1);

        return false;
    }
}
