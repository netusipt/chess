package chess.game;

import chess.game.pieces.impl.*;
import chess.game.player.Color;

import javax.swing.*;

public class Board {

    public static final int size = 8;

    private final Tile[][] tiles;

    public Board() {
        tiles = new Tile[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = new Tile();
            }
        }
    }

    //chess init setup
    public void setup() {
        this.setupMainForce(Color.BLACK);
        this.setupMainForce(Color.WHITE);
        this.setupPawns();
    }

    private void setupMainForce(Color color) {
        int line = 0;
        if(color == Color.WHITE) {
            line = 7;
        }
        tiles[line][0].setPiece(new Rook(color));
        tiles[line][1].setPiece(new Knight(color));
        tiles[line][2].setPiece(new Bishop(color));
        tiles[line][3].setPiece(new King(color));
        tiles[line][4].setPiece(new Queen(color));
        tiles[line][5].setPiece(new Bishop(color));
        tiles[line][6].setPiece(new Knight(color));
        tiles[line][7].setPiece(new Rook(color));
    }

    private void setupPawns() {
        for (int i = 0; i < size; i++) {
            tiles[1][i].setPiece(new Pawn(Color.BLACK));
            tiles[6][i].setPiece(new Pawn(Color.WHITE));
        }
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }
}
