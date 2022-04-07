package chess.game;

import chess.comunication.dto.request.client.game.MoveRequest;
import chess.game.pieces.Piece;
import chess.game.player.Player;
import chess.game.rules.Rule;
import chess.game.rules.impl.LimitedBoardRule;

import java.util.ArrayList;
import java.util.List;


public class Referee {

    private List<Rule> rules;

    private Player player1;
    private Player player2;

    public Referee(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        rules.add(new LimitedBoardRule());
    }

    //TODO: implement
    public boolean isMovePermitted(MoveRequest move) {
        for (Rule rule : this.rules) {
            if(rule.isBroken()) {
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
