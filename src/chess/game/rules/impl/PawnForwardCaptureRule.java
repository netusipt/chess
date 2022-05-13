package chess.game.rules.impl;

import chess.game.Board;
import chess.game.Tile;
import chess.game.base.Move;
import chess.game.base.Vector;
import chess.game.pieces.Piece;
import chess.game.pieces.impl.Pawn;
import chess.game.player.Color;
import chess.game.rules.Rule;

/**
 * Rule prevents pawns from capturing forwards
 */
public class PawnForwardCaptureRule extends Rule {

    public PawnForwardCaptureRule() {
        super(false);
    }

    @Override
    public boolean isBroken(Board board, Color playerColor, Move move, Color turn) {
        Piece piece = board.getTiles()[move.getFromY()][move.getFromX()].getPiece();
            if(piece instanceof Pawn) {
                Piece endangeredPiece = board.getTiles()[move.getToY()][move.getToX()].getPiece();
                if(endangeredPiece != null && endangeredPiece.getColor() != playerColor) {
                    int diffX = move.getToX() - move.getFromX();
                    int diffY = move.getToY() - move.getFromY();

                    for(Vector surroundVector : ((Pawn)piece).getSurroundVectors()) {
                        if(diffX == surroundVector.getX() && diffY == surroundVector.getY()) {
                            return true;
                        }
                    }
                }
            }
        return false;
    }
}
