package model.actions;

import model.Game;
import model.QualityHaver;
import model.cards.Warrior;
import model.gamestate.DeathState;
import model.player.Player;

public class Killer implements AutoAction{

    @Override
    public void execute(QualityHaver source, QualityHaver target) {
        assert target instanceof Warrior;

        kill((Warrior)target);
    }

    public static void kill(Warrior warrior) {
        Game game = warrior.getCell().getBoard().getGame();
        Player player = game.getWarriorsPlayer(warrior);
        DeathState death = new DeathState(warrior);
        game.iterateAllTriggersCheck(death);
        warrior.getCell().setWarrior(null);
        player.getWarriors().remove(warrior);
    }
}
