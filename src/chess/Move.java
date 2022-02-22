
package chess;

import myexceptions.InvalidPositionException;
import pieces.Piece;

public class Move {
    
    private Piece piece;
    private int x;
    private int y;
    
    public Move(Piece piece, int x, int y) throws InvalidPositionException {
        this.piece = piece;
        this.x = x;
        this.y = y;
        
        piece.setPosition(x, y);
    }
    
    public String getAnotation() {
        return "";
    }
}
