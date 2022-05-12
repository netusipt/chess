package chess.game;

import chess.comunication.dto.BoardInfo;
import chess.comunication.dto.PieceInfo;
import chess.game.pieces.Piece;
import chess.game.pieces.impl.*;
import chess.game.player.Color;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public static final int size = 8;

    private final Tile[][] tiles;
    private int pieceCount;

    public Board() {
        this.pieceCount = 0;
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
        tiles[line][0].setPiece(new Rook(this.pieceCount++, color));
        tiles[line][1].setPiece(new Knight(this.pieceCount++, color));
        tiles[line][2].setPiece(new Bishop(this.pieceCount++, color));
        tiles[line][3].setPiece(new Queen(this.pieceCount++, color));
        tiles[line][4].setPiece(new King(this.pieceCount++, color));
        tiles[line][5].setPiece(new Bishop(this.pieceCount++, color));
        tiles[line][6].setPiece(new Knight(this.pieceCount++, color));
        tiles[line][7].setPiece(new Rook(this.pieceCount++, color));
    }

    private void setupPawns() {
        for (int i = 0; i < size; i++) {
            tiles[1][i].setPiece(new Pawn(this.pieceCount++, Color.BLACK));
            tiles[6][i].setPiece(new Pawn(this.pieceCount++, Color.WHITE));
        }
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public BoardInfo getBoardInfo() {
        List<PieceInfo> pieceInfoList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(!this.tiles[i][j].isEmpty()) {
                    Piece piece = this.tiles[i][j].getPiece();
                    pieceInfoList.add(new PieceInfo(piece.getId(), piece.getTag(), j, i));
                }
            }
        }

        BoardInfo boardInfo = new BoardInfo(pieceInfoList);
        return boardInfo;
    }
}
