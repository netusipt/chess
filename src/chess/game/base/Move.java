
package chess.game.base;

import chess.game.pieces.Piece;

public class Move {

    private String playerId;
    private int pieceId;
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;

    public Move(String playerId, int pieceId, int fromX, int fromY, int toX, int toY) {
        this.playerId = playerId;
        this.pieceId = pieceId;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }
    
    public static String getNotation(Piece piece, Position toPosition) {
        return "";
    }

    public int getPieceId() {
        return this.pieceId;
    }

    public int getFromX() {
        return this.fromX;
    }

    public int getFromY() {
        return this.fromY;
    }

    public int getToX() {
        return this.toX;
    }

    public int getToY() {
        return this.toY;
    }

    public void setPieceId(int pieceId) {
        this.pieceId = pieceId;
    }

    public void setFromX(int fromX) {
        this.fromX = fromX;
    }

    public void setFromY(int fromY) {
        this.fromY = fromY;
    }

    public void setToX(int toX) {
        this.toX = toX;
    }

    public void setToY(int toY) {
        this.toY = toY;
    }
}
