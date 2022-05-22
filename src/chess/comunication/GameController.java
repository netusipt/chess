
package chess.comunication;

import chess.comunication.dto.Message;
import chess.game.Game;
import chess.game.base.Move;
import chess.game.player.Color;

public class GameController {

    private Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public void joinGame(String playerId, boolean isHuman) {
        game.setPlayer(playerId, Color.BLACK, isHuman);
    }

    public Game getGame() {
        return this.game;
    }

}
