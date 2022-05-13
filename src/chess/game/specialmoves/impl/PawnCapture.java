package chess.game.specialmoves.impl;

import chess.game.Board;
import chess.game.Tile;
import chess.game.base.Move;
import chess.game.base.Position;
import chess.game.base.Vector;
import chess.game.pieces.Piece;
import chess.game.pieces.impl.Pawn;
import chess.game.specialmoves.MoveAdder;
import chess.game.trigger.FLAG;

import java.util.ArrayList;
import java.util.List;


public class PawnCapture extends MoveAdder {

    @Override
    public List<Move> getSpecialMoves(Board board, String playerId, Position piecePosition) {
        List<Move> moves = new ArrayList<>();

        Piece piece = board.getTiles()[piecePosition.getY()][piecePosition.getX()].getPiece();
        if (piece instanceof Pawn) {
            for(Vector captureVector : ((Pawn) piece).getCaptureVectors()) {
                int toX = piecePosition.getX() + captureVector.getX();
                int toY = piecePosition.getY() + captureVector.getY();

                if(toX >= Position.MIN && toX <= Position.MAX && toY >= Position.MIN && toY <= Position.MAX) {
                    Piece endangeredPiece = board.getTiles()[toY][toX].getPiece();
                    if(endangeredPiece != null && endangeredPiece.getColor() != piece.getColor()) {
                        Move move = new Move(
                                playerId,
                                piece.getId(),
                                piecePosition.getX(),
                                piecePosition.getY(),
                                toX,
                                toY
                        );
                        move.addFlag(FLAG.CAPTURE);
                        moves.add(move);
                    }
                }
            }
        }

        return moves;
    }
}
