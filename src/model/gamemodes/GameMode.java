package model.gamemodes;

import model.Game;
import model.player.Player;

import java.io.Serializable;

public abstract class GameMode implements Serializable {
    public Player winner;

    public abstract boolean checkGameEnd(Game game);

    public abstract void applyTriggerToBoard(Game game);

    public abstract GameMode deepCopy();
}