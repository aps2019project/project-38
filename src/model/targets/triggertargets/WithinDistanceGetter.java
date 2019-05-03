package model.targets.triggertargets;

import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.gamestate.GameState;

import java.util.ArrayList;

public class WithinDistanceGetter implements TriggerTarget {
    boolean friendMod;

    public WithinDistanceGetter(boolean friendMod) {
        this.friendMod = friendMod;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert triggerOwner instanceof Warrior;

        Warrior warrior = (Warrior)triggerOwner;
        return null;
    }
}
