
package chess.game.base;

import chess.game.pieces.Piece;
import chess.game.trigger.FLAG;

import java.util.ArrayList;
import java.util.List;

public class  Move {

    private String playerId;
    private int pieceId;
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;
    private List<FLAG> flags;

    public Move(String playerId, int pieceId, int fromX, int fromY, int toX, int toY) {
        this.playerId = playerId;
        this.pieceId = pieceId;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.flags = new ArrayList<>();
    }
    
    public static String getNotation(Piece piece, Position toPosition) {
        return "";
    }

    public String getPlayerId() {
        return this.playerId;
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

    public List<FLAG> getFlags() {
        return this.flags;
    }

    public void setFlags(List<FLAG> flags) {
        this.flags = flags;
    }

    public void addFlag(FLAG flag) {
        this.flags.add(flag);
    }
}
