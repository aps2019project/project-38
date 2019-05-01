package model.targets.triggertargets;

import model.QualityHaver;
import model.gamestate.GameState;

import java.util.ArrayList;

public class TriggerOwnerGetter implements TriggerTarget {

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        ArrayList<QualityHaver> targets = new ArrayList<>();
        targets.add(triggerOwner);
        return targets;
    }
}
