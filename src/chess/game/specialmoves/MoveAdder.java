package chess.game.specialmoves;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.base.Position;

import java.util.List;

public interface MoveAdder {

    public List<Move> addMoves(List<Move> moves, Board board, String playerId, Position piecePosition);
}
