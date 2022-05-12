package chess.comunication.dto.response.server.pregame;

import chess.comunication.dto.Message;

public final class ConnectResponse extends Message {

    public ConnectResponse(String gameId, String playerId) {
        super(gameId, playerId, "connect_response");
    }
}
