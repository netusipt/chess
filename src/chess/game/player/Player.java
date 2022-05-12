
package chess.game.player;

import java.util.ArrayList;

import chess.game.base.Position;
import chess.game.pieces.Piece;
import org.java_websocket.WebSocket;


public abstract class Player {
    
    protected String id;
    protected Color color;
    protected ArrayList<Piece> pieces;
    protected WebSocket connection;
    protected Position kingPosition;
    
    protected Player(String id, Color color) {
        this.id = id;
        this.color = color;
        this.pieces = new ArrayList<>();
    }

    public void setConnection(WebSocket connection) {
        this.connection = connection;
    }

    public WebSocket getConnection() {
        return connection;
    }

    public String getId() {
        return this.id;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void addPiece(Piece piece) {
        this.pieces.add(piece);
    }
    
    public void removePiece(int index) {
        this.pieces.remove(index);
    }

    public Position getKingPosition() {
        return this.kingPosition;
    }
}
