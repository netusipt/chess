package chess.game;

import chess.game.base.Move;
import chess.game.base.Position;
import chess.game.base.Vector;
import chess.game.pieces.Piece;
import chess.game.pieces.impl.Pawn;
import chess.game.player.Color;
import chess.game.rules.Rule;
import chess.game.rules.impl.*;
import chess.game.specialmoves.MoveAdder;
import chess.game.specialmoves.impl.EnPassant;
import chess.game.trigger.FLAG;
import chess.game.trigger.Trigger;
import chess.game.trigger.impl.CaptureTrigger;
import chess.game.trigger.impl.CastlingTrigger;
import chess.game.trigger.impl.PawnPromotionTrigger;

import java.util.ArrayList;
import java.util.List;


public class Referee {

    private final List<Rule> rules;
    private final List<Trigger> triggers;
    private final List<MoveAdder> moveAdders;

    public Referee() {
        this.rules = new ArrayList<>();
        rules.add(new LimitedBoardRule());
        rules.add(new CaptureOwnRule());
        rules.add(new TurnRule());
        rules.add(new JumpOverRule());
        rules.add(new InSurroundingsRule());
        rules.add(new InDirectionRule());

        this.triggers = new ArrayList<>();
        triggers.add(new CaptureTrigger());
        triggers.add(new CastlingTrigger());
        triggers.add(new PawnPromotionTrigger());

        this.moveAdders = new ArrayList<>();
        moveAdders.add(new EnPassant());
    }

    public boolean isMovePermitted(Board board, Color playerColor, Move move, Color turn) {
        Piece piece = board.getTiles()[move.getFromY()][move.getFromX()].getPiece();
        Position piecePosition = new Position(move.getFromX(), move.getFromY());

        List<Move> moves = this.getSpecialMoves(board, move.getPlayerId(), piecePosition);

        for(Move specialMove : moves) {
            if(specialMove.getFromX() == move.getFromX() && specialMove.getFromY() == move.getFromY()
                    && specialMove.getToX() == move.getToX() && specialMove.getToY() == move.getToY()) {
                return true;
            }
        }


        if(piece instanceof Pawn) {
            if(!board.getTiles()[move.getToY()][move.getToX()].isEmpty()) {
                int diffX = move.getToX() - move.getFromX();
                int diffY = move.getToY() - move.getFromY();

                for(Vector captureVector : ((Pawn) piece).getCaptureVectors()) {
                    if(captureVector.getX() == diffX && captureVector.getY() == diffY) {
                        return true;
                    }
                }
            }
        }

        for (Rule rule : this.rules) {
            if(rule.isBroken(board, playerColor, move, turn)) {
                System.out.println("Rule broken: " + rule.getClass().getSimpleName());
                return false;
            }
        }
        return true;
    }

    public Move setFlags(Board board, Color playerColor, Move move) {
        List<FLAG> flags = new ArrayList<>();
        for(Trigger trigger : this.triggers) {
            if(trigger.isTriggered(board, move, playerColor)) {
                flags.add(trigger.getFlag());
            }
        }
        move.setFlags(flags);
        return move;
    }

    //TODO: implement
    public boolean isCheck(Board board, Position kingPosition) {
        return false;
    }

    //TODO: implement
/*    public boolean isCheckMate(Board board, Position kingPosition) {
        if(this.getPossibleMoves(board, kingPosition).isEmpty()) {
            return true;
        }
        return false;
    }*/

    //TODO: implement
    public List getPossibleMoves(Board board, String playerId, Position piecePosition) {
        List<Move> moves = new ArrayList<>();
        Piece piece = board.getTiles()[piecePosition.getY()][piecePosition.getX()].getPiece();

        for (int i = 0; i < Board.size; i++) {
            for (int j = 0; j < Board.size; j++) {
                Move move = new Move(playerId, piece.getId(), piecePosition.getX(), piecePosition.getY(), j, i);

                if(this.isMovePermitted(board, piece.getColor(), move, piece.getColor())) {
                    moves.add(move);
                }
            }
        }

        return moves;
    }

    private List<Move> getSpecialMoves(Board board, String playerId, Position piecePosition) {
        List<Move> moves = new ArrayList<>();

        for (MoveAdder moveAdder : this.moveAdders) {
            moves = moveAdder.addMoves(moves, board, playerId, piecePosition);
        }
        return moves;
    }

/*    public List<Move> setCastlingMoves(Board board, String playerId, King king, List<Move> moves) {
        if(playerColor == Color.WHITE) {

            Piece possibleRook = board.getTiles()[7][0].getPiece();
            if(possibleRook instanceof Rook && !possibleRook.isMoved() && !((King) piece).isMoved()) {
                moves.add(new Move(playerId, king.getId(), king.getPosition().getX(), king.getPosition().getY(), 2, 7));
            }
            possibleRook = board.getTiles()[7][7].getPiece();
            if(possibleRook instanceof Rook && !possibleRook.isMoved() && !((King) piece).isMoved()) {
                moves.add(new Position(6, 7));
            }
        }
    }*/


/*    public boolean isCheckmate(Board board, Position kingPosition) {
        return false;
    }*/
}
