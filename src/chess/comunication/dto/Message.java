
package chess.comunication.dto;

public class Message {

    private String gameId;
    private String playerId;
    private String messageType;

    public Message(String gameId, String playerId, String type) {
        this.messageType = type;
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public String getGameId() {
        return gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getMessageType() {
        return this.messageType;
    }

}
