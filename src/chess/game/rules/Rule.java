package chess.game.rules;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.player.Color;

public interface Rule {

    public boolean isBroken(Board board, Color playerColor, Move move, Color turn);


}
