
package player;

import java.util.ArrayList;

import chess.Color;
import myexceptions.InvalidPositionException;
import pieces.Piece;


public abstract class Player {
    
    protected String name;
    protected Color color;
    protected ArrayList<Piece> pieces;
    
    protected Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.pieces = new ArrayList<>();
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

}
