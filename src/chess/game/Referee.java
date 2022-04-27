package chess.game;

import chess.game.base.Move;
import chess.game.pieces.Piece;
import chess.game.player.Color;
import chess.game.rules.Rule;
import chess.game.rules.impl.CaptureOwnRule;
import chess.game.rules.impl.InDirectionRule;
import chess.game.rules.impl.JumpOverRule;
import chess.game.rules.impl.LimitedBoardRule;

import java.util.ArrayList;
import java.util.List;


public class Referee {

    private List<Rule> rules;

    public Referee() {
        rules.add(new LimitedBoardRule());
        rules.add(new CaptureOwnRule());
        rules.add(new InDirectionRule());
        rules.add(new JumpOverRule());
    }

    //TODO: implement
    public boolean isMovePermitted(Board board, Color playerColor, Move move) {
        for (Rule rule : this.rules) {
            if(rule.isBroken(board, playerColor, move)) {
                return false;
            }
        }

        return true;
    }

    //TODO: implement
    public List getPossibleMoves(Piece piece) {
        return new ArrayList();
    }
}
