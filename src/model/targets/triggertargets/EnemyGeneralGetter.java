package model.targets.triggertargets;

import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.gamestate.GameState;

import java.util.ArrayList;

public class EnemyGeneralGetter implements TriggerTarget {
    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert triggerOwner instanceof Warrior;
        //todo
        return null;
    }
}
