package chess.comunication.http.controllers;

public abstract class Controller {

    public abstract String doGet();

    public abstract String doGet(String[] params);
}
