package chess.comunication.dto.response.server.game;

import chess.comunication.dto.Message;
import chess.game.base.Move;

public class MoveResponse extends Message {

    //OK, IMPOSSIBLE
    private Move move;
    private String moveStatus;

    public MoveResponse(String gameId, String playerId, Move move,  String moveStatus) {
        super(gameId, playerId,"move_response");
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public String getMoveStatus() {
        return this.moveStatus;
    }
}
