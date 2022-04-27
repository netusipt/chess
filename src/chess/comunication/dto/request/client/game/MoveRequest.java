package chess.comunication.dto.request.client.game;

import chess.comunication.dto.Message;
import chess.game.base.Move;

public class MoveRequest extends Message {

    private Move move;

    private int pieceId;
    private int posX;
    private int posY;

    public MoveRequest(String gameId, String playerId, Move move) {
        super(gameId, playerId, "move_request");
        this.move = move;
    }

    public Move getMove() {
        return this.move;
    }
}
