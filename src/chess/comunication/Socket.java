package chess.comunication;

import chess.comunication.dto.Message;
import chess.comunication.dto.request.client.game.MoveRequest;
import chess.comunication.dto.response.server.game.MoveResponse;
import chess.comunication.dto.response.server.pregame.NewGameResponse;
import chess.game.base.Move;
import chess.utils.JsonConverter;
import com.google.gson.JsonObject;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

//TODO: delegate requests to gameController
public class Socket extends WebSocketServer {

    private final JsonConverter jsonConverter;
    private final MessageHandler messageHandler;
    private final GamesManager gamesManager;

    public Socket(InetSocketAddress socketAddress, JsonConverter jsonConverter, MessageHandler messageHandler, GamesManager gamesManager) {
        super(socketAddress);
        this.jsonConverter = jsonConverter;
        this.messageHandler = messageHandler;
        this.gamesManager = gamesManager;
    }

    @Override
    public void onOpen(WebSocket connection, ClientHandshake clientHandshake) {
        System.out.println("New client connected!");
        connection.send("Welcome to the server!");
    }

    @Override
    public void onClose(WebSocket connection, int i, String s, boolean b) {
        System.out.println("Connection closed!");
    }

    @Override
    public void onMessage(WebSocket connection, String message) {
        System.out.println(message);
        String messageType = this.getMessageType(message);
        Message response = new Message("none", "none", "invalid");

        switch (messageType) {
            case "new_game":
                //TODO: generate link, UUID - player
                response = new NewGameResponse("fdGExhR", "eb48ef99-105f-40bf-927e-f9693c8994f2", "white", "friend");
                connection.send(this.jsonConverter.toJson(response));
                break;
            case "join_game":
                break;
            case "move":
                MoveRequest moveRequest = this.jsonConverter.toObject(message, MoveRequest.class);
                //TODO: is move possible?
                response = new MoveResponse(
                        moveRequest.getGameId(),
                        moveRequest.getPlayerId(),
                        moveRequest.getMove(),
                        "OK"
                );
                break;
        }

        connection.send(this.jsonConverter.toJson(response));
    }

    @Override
    public void onError(WebSocket connection, Exception e) {
        System.out.println("Communication error: " + e.getMessage());
    }

    @Override
    public void onStart() {
        System.out.println("Server ready!");
    }

    private String getMessageType(String message) {
        Message request = this.jsonConverter.toObject(message, Message.class);
        return request.getMessageType();
    }

}
