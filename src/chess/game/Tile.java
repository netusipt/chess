package chess.game;

import chess.game.pieces.Piece;

public class Tile {

    private Piece piece;

    public boolean isEmpty() {
        return piece == null;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return this.piece;
    }
}
