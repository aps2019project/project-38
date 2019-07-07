package model.targets;

import model.Cell;
import model.QualityHaver;
import model.cards.Warrior;
import model.gamestate.GameState;

import java.util.ArrayList;

public class OnCellGetter implements TriggerTarget {

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert triggerOwner instanceof Cell;

        ArrayList<Warrior> targets = new ArrayList<>();
        targets.add(((Cell)triggerOwner).getWarrior());
        return targets;
    }
}
