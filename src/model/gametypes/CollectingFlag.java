package model.gametypes;

import model.Board;
import model.Game;

public class CollectingFlag extends GameType {
    @Override
    boolean checkGameEnd(Game game) {
        return false;
    }

    @Override
    void applyTriggerToBoard(Board board) {

    }
}
