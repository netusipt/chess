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

    public Router(ControllerContainer controllerContainer) {
        fileLoader = new FileLoader();
        this.controllerContainer = controllerContainer;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        Controller controller;

        switch (httpExchange.getRequestURI().toString()){
            case "/":
                controller = this.controllerContainer.get("home");
                break;
            case "/game":
                controller = this.controllerContainer.get("game");
                break;
        }


        switch (httpExchange.getRequestMethod()) {
            case "GET":
                String request = httpExchange.getRequestURI().toString();
                System.out.println("GET request");
                System.out.println(request);

                String path = "public" + request;
                File file = new File(path);

                if (request.equals("/")) {
                    path += "chess.html";
                    file = new File(path);
                } else if(!file.exists()) {
                    path = "public/err/404.html";
                    file = new File(path);
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
