package chess.comunication.dto;

public class PieceInfo {

    private final int id;
    private final String tag;
    private final int x;
    private final int y;

    public PieceInfo(int id, String tag, int x, int y) {
        this.id = id;
        this.tag = tag;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return this.id;
    }

    public String getTag() {
        return this.tag;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
