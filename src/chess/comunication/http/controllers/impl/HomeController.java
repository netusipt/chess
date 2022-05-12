package chess.comunication.http.controllers.impl;

import chess.comunication.http.controllers.Controller;

public class HomeController extends Controller {

    @Override
    public String doGet() {
        return "chess.html";
    }

    @Override
    public String doGet(String[] params) {
        return null;
    }

    public void doGet(String request) {

    }

}
