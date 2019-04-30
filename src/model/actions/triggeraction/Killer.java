package model.actions.triggeraction;

import model.Game;
import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.gamestate.DeathState;
import model.player.Player;

public class Killer implements TriggerAction{

    @Override
    public void execute(QualityHaver source, QualityHaver target) {
        if(!(target instanceof Warrior))
            return;

        kill((Warrior)target);
    }

    public static void kill(Warrior warrior){
        Game game = warrior.getCell().getBoard().getGame();
        Player player = game.getWarriorsPlayer(warrior);
        DeathState death = new DeathState(warrior);
        game.iterateAllTriggers(death);
        player.getWarriors().remove(warrior);
    }
}
