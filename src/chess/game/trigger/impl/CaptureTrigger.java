package chess.game.trigger.impl;

import chess.game.Board;
import chess.game.Tile;
import chess.game.base.Move;
import chess.game.base.Vector;
import chess.game.pieces.Piece;
import chess.game.pieces.impl.Pawn;
import chess.game.player.Color;
import chess.game.trigger.FLAG;
import chess.game.trigger.Trigger;

public class CaptureTrigger extends Trigger {

    public CaptureTrigger() {
        super(FLAG.CAPTURE);
    }

    @Override
    public boolean isTriggered(Board board, Move move, Color playerColor) {
        Tile tile = board.getTiles()[move.getToY()][move.getToX()];
        Piece piece = board.getTiles()[move.getFromY()][move.getFromX()].getPiece();

        if(!(piece instanceof Pawn)) {
            if(!tile.isEmpty()) {
                if(tile.getPiece().getColor() != playerColor) {
                    return true;
                }
            }
        } else {
            int diffX = move.getToX() - move.getFromX();
            int diffY = move.getToY() - move.getFromY();

            for (Vector vector : ((Pawn) piece).getCaptureVectors()) {
                if(diffX == vector.getX() && diffY == vector.getY()) {
                    return true;
                }
            }
        }

        return false;
    }

}
