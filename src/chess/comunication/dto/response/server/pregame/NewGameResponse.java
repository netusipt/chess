package chess.comunication.dto.response.server.pregame;

import chess.comunication.dto.Message;

public final class NewGameResponse extends Message {

    private String link;
    private String color;
    private String gameType;

    public NewGameResponse(String link, String playerId, String color, String gameType) {
        super(link, playerId, "game_start");
        this.link = link;
        this.color = color;
        this.gameType = gameType;
    }

}
