package chess.game;

import chess.game.player.Color;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

public class Clock extends Thread {

    private int initTime;
    private Map<Color, Integer> timeLeft;
    private Color turn;
    private Color unchangedTurn;
    private boolean switched;

    public Clock(int initTime) {
        this.initTime = initTime * 60;
        timeLeft = new java.util.HashMap<Color, Integer>();
        timeLeft.put(Color.WHITE, this.initTime);
        timeLeft.put(Color.BLACK, this.initTime);
        turn = Color.WHITE;
        switched = false;
    }

    public void setTurn(Color turn) {
        this.turn = turn;
        this.switched = true;
    }


    public int getTimeLeft(Color color) {
        return this.timeLeft.get(color);
    }

    @Override
    public void run() {
        LocalDateTime start = LocalDateTime.now();

        while (timeLeft.get(this.turn) > 0) {
            if(this.switched) {
                start = LocalDateTime.now();
                this.initTime = this.timeLeft.get(this.turn);
                this.unchangedTurn = this.turn;
                this.switched = false;
            }

            LocalDateTime now = LocalDateTime.now();
            this.timeLeft.put(this.unchangedTurn, initTime - (int)(now.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC))) ;
        }
    }
}
