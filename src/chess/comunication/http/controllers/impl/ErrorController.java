package chess.comunication.http.controllers.impl;

import chess.comunication.http.controllers.Controller;

public class ErrorController extends Controller {

    @Override
    public String doGet() {
        return "err/404.html";
    }

    @Override
    public String doGet(String[] params) {
        switch (params[0]) {
            case "404":
                return "err/404.html";
            case "500":
                return "err/500.html";
        }

        return "err/404.html";
    }
}
