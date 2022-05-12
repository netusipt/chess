package chess.game.rules.impl;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.base.Vector;
import chess.game.pieces.DirectionPiece;
import chess.game.pieces.Piece;
import chess.game.pieces.SurroundPiece;
import chess.game.player.Color;
import chess.game.rules.Rule;

public class InDirectionRule implements Rule {

    @Override
    public boolean isBroken(Board board, Color playerColor, Move move, Color turn) {
        Piece piece = board.getTiles()[move.getFromY()][move.getFromX()].getPiece();

        if(piece instanceof DirectionPiece) {
            System.out.println("Instance of DirectionPiece");

            int diffX = move.getToX() - move.getFromX();
            int diffY = move.getToY() - move.getFromY();

            for (Vector direction : ((DirectionPiece) piece).getDirections()) {
                //if vectors are dependent
                if(diffX * direction.getY() - diffY * direction.getX() == 0) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }
}
