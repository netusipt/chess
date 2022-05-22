package chess.comunication;

import chess.comunication.dto.Message;
import chess.game.Game;

import java.util.HashMap;
import java.util.Map;

public class GamesManager {

    private Map<String, GameController> onGoingGames;
    private Map<String, GameController> unstatedGames;

    public GamesManager() {
        onGoingGames = new HashMap<>();
        unstatedGames = new HashMap<>();
    }

    public Game createGame() {
        Game game = new Game();

        System.out.println("Game created");
        GameController gameController = new GameController(game);
        unstatedGames.put(game.getId(), gameController);

        return game;
    }

    public void joinGame(String gameId, String playerId, boolean isHuman) {
        GameController gameController = unstatedGames.get(gameId);
        unstatedGames.remove(gameId);

        gameController.joinGame(playerId, isHuman);
        onGoingGames.put(gameId, gameController);
    }

    public Game getGame(String gameId) {
        Game game = onGoingGames.get(gameId).getGame();
        if(game == null) {
            game = unstatedGames.get(gameId).getGame();
        }
        return game;
    }

    public void process(Message message) {
        GameController gameController = onGoingGames.get(message.getGameId());

    }

    public void gameEnded(String gameId) {
        onGoingGames.remove(gameId);
    }


}
