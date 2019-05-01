package model.targets.triggertargets;

import model.QualityHaver;
import model.cards.heros.Hero;
import model.cards.warriors.Warrior;
import model.gamestate.GameState;

import java.util.ArrayList;

public class FriendGeneralGetter implements TriggerTarget{

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert triggerOwner instanceof Warrior;

        Warrior warrior = (Warrior)triggerOwner;

        ArrayList<Hero> targets = new ArrayList<>();
        //targets.add(warrior.getCell().getBoard().getGame().getWarriorsPlayer(warrior).getMainDeck().getHero());
        //todo
        return null;
    }
}
