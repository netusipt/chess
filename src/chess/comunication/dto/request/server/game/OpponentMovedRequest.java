package chess.comunication.dto.request.server.game;

import chess.comunication.dto.Message;
import chess.game.base.Move;

public class OpponentMovedRequest extends Message {

    private Move move;
    private int whiteTimeLeft;
    private int blackTimeLeft;

    public OpponentMovedRequest(String gameId, String playerId, Move move, int whiteTimeLeft, int blackTimeLeft) {
        super(gameId, playerId, "opponent_moved");
        this.move = move;
        this.whiteTimeLeft = whiteTimeLeft;
        this.blackTimeLeft = blackTimeLeft;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }
}
