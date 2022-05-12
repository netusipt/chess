package chess.comunication.dto.response.server.game;

import chess.comunication.dto.Message;
import chess.game.trigger.FLAG;
import chess.game.base.Move;

import java.util.List;

public class MoveResponse extends Message {

    //OK, IMPOSSIBLE
    private Move move;
    private String moveStatus;
    private int whiteTimeLeft;
    private int blackTimeLeft;

    public MoveResponse(String gameId, String playerId, Move move,  String moveStatus, int whiteTimeLeft, int blackTimeLeft) {
        super(gameId, playerId,"move_response");
        this.move = move;
        this.moveStatus = moveStatus;
        this.whiteTimeLeft = whiteTimeLeft;
        this.blackTimeLeft = blackTimeLeft;
    }

    public Move getMove() {
        return this.move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public String getMoveStatus() {
        return this.moveStatus;
    }

    public void setMoveStatus(String moveStatus) {
        this.moveStatus = moveStatus;
    }

    public int getWhiteTimeLeft() {
        return this.whiteTimeLeft;
    }

    public int getBlackTimeLeft() {
        return this.blackTimeLeft;
    }

}
