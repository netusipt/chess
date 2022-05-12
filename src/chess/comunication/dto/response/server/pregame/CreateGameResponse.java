package chess.comunication.dto.response.server.pregame;

import chess.comunication.dto.Message;

public final class CreateGameResponse extends Message {

    private String link;
    private String color;
    private String gameType;

    public CreateGameResponse(String link, String playerId, String color, String gameType) {
        super(link, playerId, "create_game_response");
        this.link = link;
        this.color = color;
        this.gameType = gameType;
    }

    public String getLink() {
        return this.link;
    }

    public String getColor() {
        return this.color;
    }

    public String getGameType() {
        return this.gameType;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

}
