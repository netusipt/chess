
package chess.comunication;

import chess.comunication.dto.Message;
import chess.game.Game;

public class GameController {

    private Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public void process(Message message) {
        switch (message.getMessageType()) {
            case "move":
                break;
        }
    }


}
