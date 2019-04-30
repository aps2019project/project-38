package model.targets;

import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.gamestate.Attack;
import model.gamestate.GameState;

import java.util.ArrayList;

public class AttackerGetter implements TriggerTarget {

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert gameState instanceof Attack;
        assert triggerOwner instanceof Warrior;

        ArrayList<Warrior> target = new ArrayList<>();
        target.add(((Attack)gameState).getAttacker());
        return target;
    }
}
