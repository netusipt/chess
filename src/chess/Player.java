
package chess;

import java.util.ArrayList;
import pieces.Piece;


public class Player {
    
    private String name;
    private boolean mashine;
    private Color color;
    private ArrayList<Piece> pieces;
    
    public Player(String name, boolean mashine, Color color) {
        this.name = name;
        this.mashine = mashine;
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
