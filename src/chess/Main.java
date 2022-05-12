package chess;

import chess.comunication.GamesManager;
import chess.comunication.MessageHandler;
import chess.comunication.Socket;
import chess.comunication.http.ControllerContainer;
import chess.comunication.http.Router;
import chess.utils.JsonConverter;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) {

        JsonConverter jsonConverter = new JsonConverter(new Gson());
        GamesManager gamesManager = new GamesManager();
        MessageHandler messageHandler = new MessageHandler(gamesManager, jsonConverter);

        InetSocketAddress socketAddress = new InetSocketAddress(8080);
        Socket socket = new Socket(socketAddress, messageHandler);

        socket.start();
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(80), 0);
            HttpContext context = httpServer.createContext("/");
            context.setHandler(new Router(new ControllerContainer()));
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
