package chess.game;

import chess.game.base.Move;
import chess.game.base.Position;
import chess.game.pieces.Piece;
import chess.game.player.Color;
import chess.game.rules.Rule;
import chess.game.rules.impl.*;
import chess.game.specialmoves.MoveAdder;
import chess.game.specialmoves.impl.Castling;
import chess.game.specialmoves.impl.EnPassant;
import chess.game.specialmoves.impl.PawnCapture;
import chess.game.trigger.FLAG;
import chess.game.trigger.Trigger;
import chess.game.trigger.impl.CaptureTrigger;
import chess.game.trigger.impl.CastlingTrigger;
import chess.game.trigger.impl.PawnPromotionTrigger;

import java.util.ArrayList;
import java.util.List;


/**
 * The Referee determines what moves are possible.
 */
public class Referee {

    private final List<Rule> rules;
    private final List<Trigger> triggers;
    private final List<MoveAdder> moveAdders;

    private Move currentMove;

    public Referee() {
        this.rules = new ArrayList<>();
        rules.add(new LimitedBoardRule());
        rules.add(new CaptureOwnRule());
        rules.add(new TurnRule());
        rules.add(new JumpOverRule());
        rules.add(new InSurroundingsRule());
        rules.add(new PawnForwardCaptureRule());
        rules.add(new InDirectionRule());
        rules.add(new KingCaptureRule());

        this.triggers = new ArrayList<>();
        triggers.add(new CaptureTrigger());
        triggers.add(new CastlingTrigger());
        triggers.add(new PawnPromotionTrigger());

        this.moveAdders = new ArrayList<>();
        moveAdders.add(new EnPassant());
        moveAdders.add(new Castling());
        moveAdders.add(new PawnCapture());
    }

    /**
     * Checks if the move is valid.
     * @param board
     * @param playerColor
     * @param move
     * @param turn
     * @return true if the move is valid, false otherwise
     */
    public boolean isMovePermitted(Board board, Color playerColor, Move move, Color turn) {
        Piece piece = board.getTiles()[move.getFromY()][move.getFromX()].getPiece();
        Position piecePosition = new Position(move.getFromX(), move.getFromY());

        List<Move> specialMoves = this.getSpecialMoves(board, playerColor, move.getPlayerId(), piecePosition, turn);

        for(Move specialMove : specialMoves) {
            if(specialMove.getFromX() == move.getFromX() && specialMove.getFromY() == move.getFromY()
                    && specialMove.getToX() == move.getToX() && specialMove.getToY() == move.getToY()) {
                this.currentMove = specialMove;
                return true;
            }
        }

        for (Rule rule : this.rules) {
            if(rule.isBroken(board, playerColor, move, turn)) {
                System.out.println("Rule broken: " + rule.getClass().getSimpleName());
                return false;
            }
        }
        this.currentMove = move;
        return true;
    }

    public Move getMove() {
        return this.currentMove;
    }

    /**
     * Sets flags to given move.
     * @param board
     * @param playerColor
     * @param move
     * @return
     */
    public Move addFlags(Board board, Color playerColor, Move move) {
        if(move.getFlags() == null) {
            move.setFlags(new ArrayList<>());
        }
        for(Trigger trigger : this.triggers) {
            if(trigger.isTriggered(board, move, playerColor)) {
                move.addFlag(trigger.getFlag());
            }
        }
        return move;
    }


    public boolean isCheck(Board board, String playerId, Position lastMovePosition, Position kingPosition) {

        List<Move> moves = this.getPossibleMoves(board, playerId, lastMovePosition);
        for(Move move : moves) {
            if(move.getToX() == kingPosition.getX() && move.getToY() == kingPosition.getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets all possible moves for the given position.
     * @param board game board
     * @param piecePosition
     * @return List of possible moves
     */
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

    /**
     * Gets special moves for the given position.
     * @param board
     * @param playerId
     * @param piecePosition
     * @return list of special moves
     */
    private List<Move> getSpecialMoves(Board board, Color playerColor, String playerId, Position piecePosition, Color turn) {
        List<Move> moves = new ArrayList<>();

        for (MoveAdder moveAdder : this.moveAdders) {
            List<Move> specialMoves = moveAdder.getSpecialMoves(board, playerId, piecePosition);
            for(Move move : specialMoves) {
                boolean valid = true;
                for (Rule rule : this.rules) {
                    if(rule.isBaseRule() && rule.isBroken(board, playerColor, move, turn)) {
                        valid = false;
                    }
                }
                if(valid) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }
}
