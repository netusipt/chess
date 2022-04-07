
package chess.game;

import chess.comunication.dto.request.client.game.MoveRequest;
import chess.exceptions.InvalidMoveException;
import chess.game.base.Position;
import chess.game.player.Color;
import chess.game.player.Player;
import chess.exceptions.InvalidPositionException;
import chess.game.pieces.impl.Bishop;
import chess.game.pieces.impl.King;
import chess.game.pieces.impl.Knight;
import chess.game.pieces.impl.Pawn;
import chess.game.pieces.impl.Queen;
import chess.game.pieces.impl.Rook;
import chess.game.player.impl.ComputerPlayer;
import chess.game.player.impl.HumanPlayer;


public class Game {

    private Player player1;
    private Player player2;
    private Referee referee;
    private int round;
    
    public Game(String playerName) {

        //TODO: set random player color
        this.player1 = new HumanPlayer(playerName, Color.WHITE);
        this.player2 = new ComputerPlayer("Computer", Color.BLACK);

        this.referee = new Referee(this.player1, this.player2);

        try {
            this.boardSetup();
        } catch (InvalidPositionException e) {
            System.err.println("Bord is not setup properly! " + e.getMessage());
        }
    }

    public Game(String player1Name, String player2Name) {

        //TODO: set random player color
        this.player1 = new HumanPlayer(player1Name, Color.WHITE);
        this.player2 = new HumanPlayer(player2Name, Color.BLACK);

        this.referee = new Referee(this.player1, this.player2);

        try {
            this.boardSetup();
        } catch (InvalidPositionException e) {
            System.err.println("Bord is not setup properly! " + e.getMessage());
        }
    }

    public void updateBoard(MoveRequest move) throws InvalidMoveException {
        //TODO
        if(this.referee.isMovePermitted(move)) {

        } else {

        }
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
        
        player.addPiece(new Rook(new Position(7, firstRow)));
        player.addPiece(new Knight(new Position(6, firstRow)));
        player.addPiece(new Bishop(new Position(5, firstRow)));
        player.addPiece(new King(new Position(kingColum, firstRow)));
        player.addPiece(new Queen(new Position(queenColum, firstRow)));
        player.addPiece(new Bishop(new Position(2, firstRow)));
        player.addPiece(new Knight(new Position(1, firstRow)));
        player.addPiece(new Rook(new Position(0, firstRow)));
        
        for (int i = 0; i < 8; i++) {
            player.addPiece(new Pawn(new Position(i, secondRow)));
        }
    }
    
}
