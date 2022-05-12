
package chess.game;

import chess.comunication.dto.BoardInfo;
import chess.game.base.Move;
import chess.game.base.Position;
import chess.game.pieces.Piece;
import chess.game.pieces.impl.Pawn;
import chess.game.player.Color;
import chess.game.player.Player;
import chess.game.player.impl.ComputerPlayer;
import chess.game.player.impl.HumanPlayer;
import chess.game.trigger.FLAG;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class Game {

    private String id;
    private Board board;
    private Map<String, Player> players;
    private Referee referee;
    private Color turn;
    private Clock clock;

    public Game() {
        this.id = UUID.randomUUID().toString();
        this.board = new Board();
        this.players = new HashMap<>();
        this.referee = new Referee();
        this.turn = Color.WHITE;

        this.board.setup();
        this.clock = new Clock(10);
        clock.start();
        System.out.println(clock.getTimeLeft(Color.WHITE));
    }

    public void setPlayer(String playerId, Color color, boolean isHuman) {
        if(players.size() >= 2) {
            throw new IllegalStateException("Game already has two players");
        }

        if(isHuman) {
            players.put(playerId, new HumanPlayer(playerId, color));
        } else {
            players.put(playerId, new ComputerPlayer(playerId, color));
        }
    }

    public Player getPlayer(String playerId) {
        return players.get(playerId);
    }

    public Player getOpponent(String playerId) {

        AtomicReference<Player> opponent = new AtomicReference<>();
        this.players.forEach((key, player) -> {
            System.out.println(key);
            if(!key.equals(playerId)) {
                opponent.set(player);
            }
        });
        return opponent.get();
    }

    public boolean updateBoard(Color playerColor,  Move move) {
        if(this.referee.isMovePermitted(board, playerColor, move, this.turn)) {
            Piece piece = this.board.getTiles()[move.getFromY()][move.getFromX()].getPiece();
            System.out.println(this.clock.getTimeLeft(playerColor));
            this.clock.switchTurn();

            if(piece instanceof Pawn) {
                if(!piece.isMoved()) {
                    ((Pawn) piece).setMovedFirstTime(true);
                    if(Math.abs(move.getFromY() - move.getToY()) == 2) {
                        ((Pawn) piece).setTwoTiles(true);
                    }
                } else {
                    ((Pawn) piece).setMovedFirstTime(false);

                }
            }

            piece.moved();

            this.board.getTiles()[move.getFromY()][move.getFromX()].setPiece(null);
            this.board.getTiles()[move.getToY()][move.getToX()].setPiece(piece);

            if(this.turn == Color.WHITE) {
                this.turn = Color.BLACK;
            } else {
                this.turn = Color.WHITE;
            }
            return true;
        }

        return false;
    }

    public boolean updateBoard(Color playerColor,  Move move, boolean simulate) {
        if(this.referee.isMovePermitted(board, playerColor, move, this.turn)) {
            Piece piece = this.board.getTiles()[move.getFromY()][move.getFromX()].getPiece();

            System.out.println(this.clock.getTimeLeft(playerColor));
            this.clock.switchTurn();

            if(piece instanceof Pawn) {

                if(!piece.isMoved()) {
                    ((Pawn) piece).setMovedFirstTime(true);
                    if(Math.abs(move.getFromY() - move.getToY()) == 2) {
                        ((Pawn) piece).setTwoTiles(true);
                    }
                } else {
                    ((Pawn) piece).setMovedFirstTime(false);
                }

            }
            piece.moved();

            this.board.getTiles()[move.getFromY()][move.getFromX()].setPiece(null);
            this.board.getTiles()[move.getToY()][move.getToX()].setPiece(piece);

            if(!simulate) {
                if(this.turn == Color.WHITE) {
                    this.turn = Color.BLACK;
                } else {
                    this.turn = Color.WHITE;
                }
            }

            return true;
        }

        return false;
    }

    public Move setFlags(Color playerColor, Move move) {
        return this.referee.setFlags(this.board, playerColor, move);
    }

    public boolean isCheck(String playerId) {
        return this.referee.isCheck(this.board, this.players.get(playerId).getKingPosition());
    }

    public List<Move> getPossibleMoves(String playerId, Position piecePosition) {
        List<Move> possibleMoves = this.referee.getPossibleMoves(this.board, playerId, piecePosition);
        for (int i = 0; i < possibleMoves.size(); i++) {
            possibleMoves.set(i, setFlags(this.players.get(playerId).getColor(), possibleMoves.get(i)));
        }

        return possibleMoves;
    }

    public int getTimeLeft(Color playerColor) {
        return this.clock.getTimeLeft(playerColor);
    }

/*    public boolean isCheckmate(String playerId) {
        Position kingPosition = this.players.get(playerId).getKingPosition();
        if(this.isCheck(playerId) && this.referee.getPossibleMoves(this.board, kingPosition).isEmpty()) {
            return true;
        }
        return false;
    }*/

    public String getId() {
        return this.id;
    }

    public Color getTurn() {
        return this.turn;
    }

    public Board getBoard() {
        return this.board;
    }

    public BoardInfo getBoardInfo() {
        return this.board.getBoardInfo();
    }

    
}
