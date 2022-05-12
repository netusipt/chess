package chess.game.trigger.impl;

import chess.game.base.Position;
import chess.game.pieces.impl.Rook;
import chess.game.player.Color;
import chess.game.trigger.FLAG;
import chess.game.Board;
import chess.game.base.Move;
import chess.game.pieces.Piece;
import chess.game.pieces.impl.King;
import chess.game.trigger.Trigger;

import java.util.ArrayList;
import java.util.List;

public class CastlingTrigger extends Trigger {

    public List<Position> castlePositions;

    public CastlingTrigger() {
        super(FLAG.CASTLE);
        this.castlePositions = new ArrayList<>(2);
    }

    @Override
    public boolean isTriggered(Board board, Move move, Color playerColor) {
        Piece piece = board.getTiles()[move.getFromY()][move.getFromX()].getPiece();
        if (piece instanceof King) {
            if(playerColor == Color.WHITE) {

                Piece possibleRook = board.getTiles()[7][0].getPiece();
                if(possibleRook instanceof Rook && !possibleRook.isMoved() && !((King) piece).isMoved()) {
                    this.castlePositions.add(new Position(2, 7));
                }
                possibleRook = board.getTiles()[7][7].getPiece();
                if(possibleRook instanceof Rook && !possibleRook.isMoved() && !((King) piece).isMoved()) {
                    this.castlePositions.add(new Position(6, 7));
                }
            }

        }

        if(castlePositions.isEmpty()) {
            return false;
        }
        return true;
    }

}
