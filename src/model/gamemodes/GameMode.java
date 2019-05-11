package model.gamemodes;


import model.Game;
import model.cards.Warrior;
import model.player.Player;
import model.triggers.Flag;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class GameMode implements Serializable {
    public Player winner;

    public abstract boolean checkGameEnd(Game game);

    public abstract void applyTriggerToBoard(Game game);

    public abstract GameMode deepCopy();
}
