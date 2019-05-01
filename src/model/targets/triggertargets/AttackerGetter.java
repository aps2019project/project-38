package model.targets.triggertargets;

import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.gamestate.AttackState;
import model.gamestate.GameState;

import java.util.ArrayList;

public class AttackerGetter implements TriggerTarget {

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert gameState instanceof AttackState;
        assert triggerOwner instanceof Warrior;

        ArrayList<Warrior> target = new ArrayList<>();
        target.add(((AttackState)gameState).getAttacker());
        return target;
    }
}
