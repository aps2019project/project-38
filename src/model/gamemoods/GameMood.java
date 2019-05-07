package model.gamemoods;


import model.Board;
import model.Game;
import model.cards.Warrior;
import model.player.Player;
import model.triggers.Flag;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class GameMood implements Serializable {
    static ArrayList<Game> steps = new ArrayList<>();
    public Player winner;

    static {
        //todo
    }

    public abstract boolean checkGameEnd(Game game);

    public abstract void applyTriggerToBoard(Game game);

    int getNumberOFPlayerFlags(Player player) {
        int result = 0;
        for (Warrior warrior : player.getWarriors()) {
            result += warrior.getTriggers().stream().filter(trigger -> trigger instanceof Flag).count();
        }
        return result;
    }
}
