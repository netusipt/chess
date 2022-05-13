package chess.game.specialmoves.impl;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.base.Position;
import chess.game.pieces.Piece;
import chess.game.pieces.impl.King;
import chess.game.pieces.impl.Rook;
import chess.game.player.Color;
import chess.game.specialmoves.MoveAdder;
import chess.game.trigger.FLAG;

import java.util.List;

public class Castling extends MoveAdder {

    @Override
    public List<Move> getSpecialMoves(Board board, String playerId, Position piecePosition) {
        List<Move> moves = new java.util.ArrayList<>();
        Piece piece = board.getTiles()[piecePosition.getY()][piecePosition.getX()].getPiece();
        if (piece instanceof King && !piece.isMoved()) {
            Piece possibleRook;
            if (piece.getColor() == Color.WHITE) {
                possibleRook = board.getTiles()[7][0].getPiece();
                if (possibleRook instanceof Rook && !possibleRook.isMoved()) {
                    moves.add(new Move(playerId, piece.getId(), piecePosition.getX(), piecePosition.getY(), 2, 7));
                }
                possibleRook = board.getTiles()[7][7].getPiece();
                if (possibleRook instanceof Rook && !possibleRook.isMoved()) {
                    moves.add(new Move(playerId, piece.getId(), piecePosition.getX(), piecePosition.getY(), 6, 7));
                }
            } else {
                possibleRook = board.getTiles()[0][0].getPiece();
                if (possibleRook instanceof Rook && !possibleRook.isMoved()) {
                    moves.add(new Move(playerId, piece.getId(), piecePosition.getX(), piecePosition.getY(), 2, 0));
                }
                possibleRook = board.getTiles()[0][7].getPiece();
                if (possibleRook instanceof Rook && !possibleRook.isMoved()) {
                    moves.add(new Move(playerId, piece.getId(), piecePosition.getX(), piecePosition.getY(), 6, 0));
                }
            }
        }

        for(Move move : moves) {
            move.addFlag(FLAG.CASTLE);
        }

        return moves;
    }
}
