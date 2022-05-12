package chess.comunication.dto.request.client.game;

import chess.comunication.dto.Message;
import chess.game.Board;

public class GetGameRequest extends Message {

    public GetGameRequest(String gameId, String playerId) {
        super(gameId, playerId, "get_game");
    }
}
