package chess.comunication.dto.response.server.game;

import chess.comunication.dto.BoardInfo;
import chess.comunication.dto.Message;
import chess.game.player.Color;

import java.util.Locale;

public class GetGameResponse extends Message {

    private BoardInfo board;

    private String turn;

    private Color color;

    public GetGameResponse(String gameId, String playerId, Color color, BoardInfo board, String turn) {
        super(gameId, playerId, "get_game_response");
        this.color = color;
        this.board = board;
        this.turn = turn;
    }

    public BoardInfo getBoard() {
        return board;
    }

    public void setBoard(BoardInfo board) {
        this.board = board;
    }

    public String getColor() {
        return color.name().toLowerCase(Locale.ROOT);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getTurn() {
        return this.turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }
}
