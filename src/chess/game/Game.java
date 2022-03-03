
package chess.game;

import chess.game.player.Color;
import chess.game.player.Player;
import chess.exceptions.InvalidPositionException;
import chess.game.pieces.impl.Bishop;
import chess.game.pieces.impl.King;
import chess.game.pieces.impl.Knight;
import chess.game.pieces.impl.Pawn;
import chess.game.pieces.impl.Queen;
import chess.game.pieces.impl.Rook;


public class Game {
    
    private int round;
    private Player player1;
    private Player player2;
    
    public Game(Player player1, Player player2) throws InvalidPositionException {
        this.player1 = player1;
        this.player2 = player2;
        this.boardSetup();
    }


    private void boardSetup() throws InvalidPositionException {
        this.playerSetup(player1);
        this.playerSetup(player2);
    }

    private void playerSetup(Player player) throws InvalidPositionException {
        
        int firstRow, secondRow, kingColum, queenColum;
        
        if(player.getColor() == Color.WHITE) {
            firstRow = 0;
            secondRow = 1;
            kingColum = 4;
            queenColum = 3;
        } else {
            firstRow = 7;
            secondRow = 6;
            kingColum = 3;
            queenColum = 4;
        }
        
        player.addPiece(new Rook(7, firstRow));
        player.addPiece(new Knight(6, firstRow));
        player.addPiece(new Bishop(5, firstRow));
        player.addPiece(new King(kingColum, firstRow));
        player.addPiece(new Queen(queenColum, firstRow));
        player.addPiece(new Bishop(2, firstRow));
        player.addPiece(new Knight(1, firstRow));
        player.addPiece(new Rook(0, firstRow));
        
        for (int i = 0; i < 8; i++) {
            player.addPiece(new Pawn(i, secondRow));
        }
    }
    
}
