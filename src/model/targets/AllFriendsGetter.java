package model.targets;

import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.gamestate.GameState;

import java.util.ArrayList;

public class AllFriendsGetter implements TriggerTarget {

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert triggerOwner instanceof Warrior;

        return ((Warrior)triggerOwner).getCell().getBoard().getGame().getWarriorsPlayer((Warrior)triggerOwner).getWarriors();
    }
}
