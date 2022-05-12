package chess.comunication.dto.request.server.pregame;

import chess.comunication.dto.Message;
import chess.game.Game;
import chess.game.player.Color;

public class StartGameRequest extends Message {

    private Color color;

    public StartGameRequest(String gameId, String playerId, Color color) {
        super(gameId, playerId, "game_start");
        this.color = color;
    }
}
