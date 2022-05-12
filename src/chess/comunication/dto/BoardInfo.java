package chess.comunication.dto;

import java.util.List;

public class BoardInfo {

    private List<PieceInfo> pieces;

    public BoardInfo(List<PieceInfo> pieces) {
        this.pieces = pieces;
    }

    public List<PieceInfo> getPieces() {
        return pieces;
    }

    public void setPieces(List<PieceInfo> pieces) {
        this.pieces = pieces;
    }
}
