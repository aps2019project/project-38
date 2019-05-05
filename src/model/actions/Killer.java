package model.actions;

import model.Game;
import model.QualityHaver;
import model.cards.Warrior;
import model.gamestate.DeathState;
import model.player.Player;

public class Killer implements TriggerAction,SpellAction{

    @Override
    public void execute(QualityHaver source, QualityHaver target) {
        assert target instanceof Warrior;

        kill((Warrior)target);
    }

    @Override
    public void execute(Player spellOwner, QualityHaver target) {

    }

    public static void kill(Warrior warrior){
        Game game = warrior.getCell().getBoard().getGame();
        Player player = game.getWarriorsPlayer(warrior);
        DeathState death = new DeathState(warrior);
        game.iterateAllTriggersCheck(death);
        player.getWarriors().remove(warrior);
    }
}
