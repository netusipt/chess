
package chess.game;

import chess.exceptions.InvalidMoveException;
import chess.game.base.Move;
import chess.game.player.Color;
import chess.game.player.Player;
import chess.game.player.impl.ComputerPlayer;
import chess.game.player.impl.HumanPlayer;

public class Game {

    private Board board;
    private Player player1;
    private Player player2;
    private Referee referee;
    private Color turn;
    
    public Game(String playerName) {
        turn = Color.WHITE;

        //TODO: set random player color
        this.player1 = new HumanPlayer(playerName, Color.WHITE);
        this.player2 = new ComputerPlayer("Computer", Color.BLACK);
        this.board = new Board();
        board.setup();

        this.referee = new Referee();
    }

    public Game(String player1Name, String player2Name) {
        turn = Color.WHITE;

        //TODO: set random player color
        this.player1 = new HumanPlayer(player1Name, Color.WHITE);
        this.player2 = new HumanPlayer(player2Name, Color.BLACK);
        this.board = new Board();
        board.setup();

        this.referee = new Referee();

    }

    public boolean updateBoard(Move move) {
        //TODO
        if(this.referee.isMovePermitted(board, turn, move)) {
            return true;
        }

        return false;
    }

    
}
