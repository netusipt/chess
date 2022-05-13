package chess.game.specialmoves.impl;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.base.Position;
import chess.game.pieces.Piece;
import chess.game.pieces.impl.Pawn;
import chess.game.player.Color;
import chess.game.specialmoves.MoveAdder;
import chess.game.trigger.FLAG;

import java.util.ArrayList;
import java.util.List;

public class EnPassant extends MoveAdder {

    private List<Move> specialMoves;

    @Override
    public List<Move> getSpecialMoves(Board board, String playerId, Position piecePosition) {
        List<Move> moves = new ArrayList<>();
        Piece piece = board.getTiles()[piecePosition.getY()][piecePosition.getX()].getPiece();

        if(piece instanceof Pawn) {

            if(piecePosition.getX() + 1 <= Position.MAX) {
                Piece possibleCapture = board.getTiles()[piecePosition.getY()][piecePosition.getX() + 1].getPiece();
                if(possibleCapture instanceof Pawn) {
                    if(((Pawn) possibleCapture).isEmPassantPossible() && possibleCapture.getColor() != piece.getColor()) {
                        int toX = piecePosition.getX() + 1;
                        int toY;
                        if(piece.getColor() == Color.WHITE) {
                            toY = piecePosition.getY() - 1;
                        } else {
                            toY = piecePosition.getY() + 1;
                        }

                        if(toY >= Position.MIN && toY <= Position.MAX) {
                            if(board.getTiles()[toY][toX].isEmpty()) {
                                Move move = new Move(playerId, piece.getId(), piecePosition.getX(), piecePosition.getY(), toX, toY);
                                move.addFlag(FLAG.EN_PASSANT);
                                move.addFlag(FLAG.CAPTURE);
                                moves.add(move);
                            }
                        }
                    }
                }
            }

            if(piecePosition.getX() - 1 >= Position.MIN) {
                Piece possibleCapture = board.getTiles()[piecePosition.getY()][piecePosition.getX() - 1].getPiece();
                if(possibleCapture instanceof Pawn) {
                    if(((Pawn) possibleCapture).isEmPassantPossible() && possibleCapture.getColor() != piece.getColor()) {
                        int toX = piecePosition.getX() - 1;
                        int toY;
                        if(piece.getColor() == Color.WHITE) {
                            toY = piecePosition.getY() - 1;
                        } else {
                            toY = piecePosition.getY() + 1;
                        }

                        if(toY >= Position.MIN && toY <= Position.MAX) {
                            if(board.getTiles()[toY][toX].isEmpty()) {
                                Move move = new Move(playerId, piece.getId(), piecePosition.getX(), piecePosition.getY(), toX, toY);
                                move.addFlag(FLAG.EN_PASSANT);
                                move.addFlag(FLAG.CAPTURE);
                                moves.add(move);
                            }
                        }
                    }
                }
            }


        }
        return moves;
    }


}
