package chess.comunication;

import chess.game.Game;

import java.util.HashMap;

public class GamesManager {

    private HashMap<String, GameController> gamesList;

    public void newGame(String player1Name, String player2Name) {
        Game game = new Game(player1Name, player2Name);
        GameController gameController = new GameController(game);
        gamesList.put(this.generateGameId(), gameController);
    }

    public void newGame(String playerName) {
        Game game = new Game(playerName);
        GameController gameController = new GameController(game);
        gamesList.put(this.generateGameId(), gameController);
    }

    public void gameEnded(String gameId) {
        gamesList.remove(gameId);
    }

    //TODO: implement
    private String generateGameId() {
        return "";
    }


}
