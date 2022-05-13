
package chess.game.player;

import java.util.ArrayList;

import chess.game.base.Position;
import chess.game.pieces.Piece;
import org.java_websocket.WebSocket;


public abstract class Player {
    
    protected String id;
    protected Color color;
    protected WebSocket connection;
    protected Position kingPosition;
    
    protected Player(String id, Color color) {
        this.id = id;
        this.color = color;

        if(color == Color.WHITE) {
            this.kingPosition = new Position(4, 7);
        } else {
            this.kingPosition = new Position(4, 0);
        }
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

    public Position getKingPosition() {
        return this.kingPosition;
    }

    public void setKingPosition(Position position) {
        this.kingPosition = position;
    }
}
