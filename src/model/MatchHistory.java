package model;

import java.util.Date;

public class MatchHistory {
    private Account opponent;
    private boolean didWin;
    private Date date;
    //***

    public Account getOpponent() {
        return opponent;
    }

    public Date getDate() {
        return date;
    }
}
