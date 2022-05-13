package chess.comunication;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

//TODO: delegate requests to gameController
public class Socket extends WebSocketServer {

    private final MessageHandler messageHandler;

    public Socket(InetSocketAddress socketAddress, MessageHandler messageHandler) {
        super(socketAddress);
        this.messageHandler = messageHandler;
    }

    @Override
    public void onOpen(WebSocket connection, ClientHandshake clientHandshake) {
        System.out.println("New client connected!");
        connection.send(this.messageHandler.generateInitMessage());
    }

    @Override
    public void onClose(WebSocket connection, int i, String s, boolean b) {
        System.out.println("Connection closed!");
    }

    @Override
    public void onMessage(WebSocket connection, String message) {
        System.out.println(message);
        this.messageHandler.setConnection(connection);
        this.messageHandler.handle(message);

    }

    @Override
    public void onError(WebSocket connection, Exception e) {
        e.printStackTrace();
        System.out.println("Communication error: " + e.getMessage());
    }

    @Override
    public void onStart() {
        System.out.println("Server ready!");
    }



}
