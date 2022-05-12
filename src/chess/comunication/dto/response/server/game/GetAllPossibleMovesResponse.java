package chess.comunication.dto.response.server.game;

import chess.comunication.dto.Message;
import chess.game.base.Move;

import java.util.List;

public class GetAllPossibleMovesResponse extends Message {

    private int pieceId;
    private List<Move> moves;

    public GetAllPossibleMovesResponse(String gameId, String playerId, List<Move> moves, int pieceId) {
        super(gameId, playerId, "get_all_possible_moves");
        this.pieceId = pieceId;
        this.moves = moves;
    }

    public int getPieceId() {
        return this.pieceId;
    }

    public void setPieceId(int pieceId) {
        this.pieceId = pieceId;
    }
}
