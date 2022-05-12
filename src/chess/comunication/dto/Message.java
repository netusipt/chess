
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

    public Message(String playerId, String type) {
        this.messageType = type;
        this.playerId = playerId;
    }

    public String getGameId() {
        return this.gameId;
    }

    public String getPlayerId() {
        return this.playerId;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

}
