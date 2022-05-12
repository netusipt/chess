package chess.comunication.dto.request.client.pregame;

import chess.comunication.dto.Message;

public final class ConnectRequest extends Message {

    public ConnectRequest(String gameId, String playerId) {
        super(gameId, playerId, "connect_request");
    }
}
