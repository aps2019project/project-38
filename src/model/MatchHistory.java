package model;

import java.util.Date;

public class MatchHistory {
    private String opponentName;
    private boolean didWin;
    private Date date;
    //***
    public void setDidWin(boolean didWin) {
        this.didWin = didWin;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public Date getDate() {
        return date;
    }
}
