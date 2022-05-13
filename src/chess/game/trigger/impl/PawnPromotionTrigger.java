package chess.game.trigger.impl;

import chess.game.Board;
import chess.game.Tile;
import chess.game.base.Move;
import chess.game.pieces.Piece;
import chess.game.pieces.impl.Pawn;
import chess.game.player.Color;
import chess.game.trigger.FLAG;
import chess.game.trigger.Trigger;


/**
 * Triggers when a pawn reaches the end of the board.
 */
public class PawnPromotionTrigger extends Trigger {

    public PawnPromotionTrigger() {
        super(FLAG.PAWN_PROMOTION);
    }

    @Override
    public boolean isTriggered(Board board, Move move, Color playerColor) {
        Tile tile = board.getTiles()[move.getToY()][move.getToX()];
        Piece piece = board.getTiles()[move.getFromY()][move.getFromX()].getPiece();

        if(piece instanceof Pawn) {
            if(playerColor == Color.WHITE) {
                if(move.getToY() == 0) {
                    return true;
                }
            } else {
                if(move.getToY() == Board.size - 1) {
                    return true;
                }
            }
        }

        return false;
    }
}
