package chess.comunication.dto.request.client.game;

import chess.comunication.dto.Message;
import chess.game.base.Position;

public class GetAllPossibleMovesRequest extends Message {

    private Position piecePosition;

    public GetAllPossibleMovesRequest(String gameId, String playerId, Position piecePosition) {
        super(gameId, playerId, "get_all_possible_moves");
        this.piecePosition = piecePosition;
    }

    public Position getPiecePosition() {
        return piecePosition;
    }

    public void setPiecePosition(Position piecePosition) {
        this.piecePosition = piecePosition;
    }

}
