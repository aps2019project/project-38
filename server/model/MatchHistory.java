package model;

import java.io.Serializable;
import java.util.Date;

public class MatchHistory implements Serializable {
    private String opponentName;
    private boolean didWin;
    private Date date;

    public MatchHistory(String opponentName, boolean didWin, Date date) {
        this.opponentName = opponentName;
        this.didWin = didWin;
        this.date = date;
    }

    //***
    public boolean getDidWin() {
        return didWin;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public Date getDate() {
        return date;
    }
}
