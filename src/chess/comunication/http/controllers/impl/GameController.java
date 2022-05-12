package chess.comunication.http.controllers.impl;

import chess.comunication.http.controllers.Controller;

public class GameController extends Controller {

    @Override
    public String doGet() {
        return "chess.html";
    }

    @Override
    public String doGet(String[] params) {
        switch (params[0]) {
            case "new":
                break;
            case "connect":
                break;
        }

        return "chess.html";
    }
}
