package chess.comunication.http;

import chess.comunication.http.controllers.Controller;
import chess.utils.FileLoader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Router implements HttpHandler {

    private FileLoader fileLoader;
    private ControllerContainer controllerContainer;

    private String prefix;
    private static final String homePath = "public/chess.html";

    public Router(ControllerContainer controllerContainer) {
        fileLoader = new FileLoader();
        this.controllerContainer = controllerContainer;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";

        switch (httpExchange.getRequestMethod()) {
            case "GET":
                String request = httpExchange.getRequestURI().toString();
                String path = request;

                if(request.startsWith("/game")) {
                    this.prefix= "/game/";
                    path = this.homePath;
                    if(request.startsWith(this.prefix + "create/")) {
                        this.prefix = this.prefix + "create/";
                        if(request.startsWith(this.prefix + "friend/")) {
                            this.prefix = this.prefix + "friend/";
                        } else if(request.startsWith(this.prefix + "computer/")) {
                            this.prefix = this.prefix + "computer/";
                        }
                    } else if(request.startsWith(this.prefix + "join/")) {
                        this.prefix = this.prefix + "join/";
                    } else if(request.startsWith(this.prefix + "play/")) {
                        this.prefix = this.prefix + "play/";
                    }
                } else if(request.startsWith("/")) {
                    this.prefix = "/";
                    path = this.homePath;
                }

                if(!request.equals(this.prefix)) { {
                    path = "public/" + request.substring(this.prefix.length());
                }}

                File file = new File(path);

                if(!file.exists()) {
                    file = new File(this.homePath);
                }


                httpExchange.sendResponseHeaders(200, file.length());
                Files.copy(file.toPath(), httpExchange.getResponseBody());

                break;
            case "POST":
                System.out.println("POST request");
                response = "POST";
                break;
        }

        httpExchange.getResponseBody().close();
    }
}
