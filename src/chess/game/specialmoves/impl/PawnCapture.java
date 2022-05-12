package chess.game.specialmoves.impl;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.base.Position;
import chess.game.specialmoves.MoveAdder;

import java.util.List;

public class PawnCapture implements MoveAdder {

    @Override
    public List<Move> addMoves(List<Move> moves, Board board, String playerId, Position piecePosition) {
        return null;
    }
}
