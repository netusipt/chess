package chess.game.trigger;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.base.Position;
import chess.game.player.Color;

public abstract class Trigger {

    private FLAG flag;
    private Position targetPosition;

    public Trigger(FLAG flag) {
        this.flag = flag;
    }

    public abstract boolean isTriggered(Board board, Move move, Color playerColor);

    public FLAG getFlag() {
        return this.flag;
    }

    protected void setTargetPosition(Position targetPosition) {
        this.targetPosition = targetPosition;
    }

    public Position getTargetPosition() {
        return this.targetPosition;
    }
}
