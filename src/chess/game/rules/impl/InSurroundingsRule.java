package chess.game.rules.impl;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.base.Vector;
import chess.game.pieces.Piece;
import chess.game.pieces.SurroundPiece;
import chess.game.player.Color;
import chess.game.rules.Rule;

public class InSurroundingsRule implements Rule {

    @Override
    public boolean isBroken(Board board, Color playerColor, Move move, Color turn) {
        Piece piece = board.getTiles()[move.getFromY()][move.getFromX()].getPiece();

        if(piece instanceof SurroundPiece) {
            int diffX = move.getToX() - move.getFromX();
            int diffY = move.getToY() - move.getFromY();

            for (Vector surroundVector : ((SurroundPiece) piece).getSurroundVectors()) {
                if (diffX == surroundVector.getX() && diffY == surroundVector.getY()) {
                    return false;
                }
            }


            return true;
        }
        return false;
    }
}
