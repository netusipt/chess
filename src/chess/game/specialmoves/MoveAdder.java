package chess.game.specialmoves;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.base.Position;
import chess.game.rules.Rule;
import chess.game.rules.impl.CaptureOwnRule;
import chess.game.rules.impl.JumpOverRule;
import chess.game.rules.impl.LimitedBoardRule;

import java.util.ArrayList;
import java.util.List;

public abstract class MoveAdder {

    private final List<Rule> baseRules;

    public MoveAdder() {
        this.baseRules = new ArrayList<>();
        this.baseRules.add(new LimitedBoardRule());
        this.baseRules.add(new CaptureOwnRule());
        this.baseRules.add(new JumpOverRule());
    }

    public abstract List<Move> getSpecialMoves(Board board, String playerId, Position piecePosition);

    protected List<Move> getPermitted(List<Move> moves, Board board, String playerId, Position piecePosition) {
        List<Move> permitted = new ArrayList<>();
        for (Move move : moves) {
            for(Rule rule : this.baseRules) {
                //if(!rule.isBroken(move)) {
                    permitted.add(move);
                //}

            }
        }
        return permitted;
    }
}
