package chess.comunication.dto.request.client.pregame;

import chess.comunication.dto.Message;

public final class CreateGameRequest extends Message {

    private String gameType;

    public CreateGameRequest(String playerId, String gameType) {
        super(playerId, "create_game_request");
        this.gameType = gameType;
    }

    public String getGameType() {
        return this.gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
}
