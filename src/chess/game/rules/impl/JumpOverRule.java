package chess.game.rules.impl;

import chess.game.Board;
import chess.game.base.Move;
import chess.game.pieces.impl.Knight;
import chess.game.player.Color;
import chess.game.rules.Rule;

import javax.swing.plaf.BorderUIResource;


/**
 * Checks if piece did not jump over other pieces while moving. (Knight is exception)
 */
public class JumpOverRule extends Rule {

    public JumpOverRule() {
        super(true);
    }

    @Override
    public boolean isBroken(Board board, Color playerColor, Move move, Color turn)
    {
        int diffX = move.getToX() - move.getFromX();
        int diffY = move.getToY() - move.getFromY();

        int x = move.getFromX();
        int y = move.getFromY();

        if(!(board.getTiles()[move.getFromY()][move.getFromX()].getPiece() instanceof Knight)) {
            while(Math.abs(diffX) > 1 || Math.abs(diffY) > 1) {
                if(diffX < 0) {
                    x--;
                    diffX++;
                } else if(diffX > 0) {
                    x++;
                    diffX--;
                }

                if(diffY < 0) {
                    y--;
                    diffY++;
                } else if(diffY > 0) {
                    y++;
                    diffY--;
                }

                if(!board.getTiles()[y][x].isEmpty()) {
                    return true;
                }

     /*       } while(!(Math.abs(x - move.getToX()) <= 1)  || !(Math.abs(y - move.getToY()) <= 1));*/
            }
        }

        return false;
    }
}
