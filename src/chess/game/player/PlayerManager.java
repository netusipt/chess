package chess.game.player;

import java.util.List;

public class PlayerManager {

    List<Player> waitingList;

    public void addToWaitingList(Player player) {
        waitingList.add(player);
    }
}
