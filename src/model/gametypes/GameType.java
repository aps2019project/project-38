package model.gametypes;


import model.Board;
import model.Game;
import model.player.Player;

public abstract class GameType {
    Player winner;

    abstract boolean checkGameEnd(Game game);

    abstract void applyTriggerToBoard(Board board);
}
