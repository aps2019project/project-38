package model.actions;

import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.triggers.Trigger;

public class Applier implements TriggerAction {
    @Override
    public void execute(QualityHaver source, QualityHaver target) {
        if(target==null || source==null)
            return;

        target.getEffects().addAll(source.getEffects());
        target.getTriggers().addAll(source.getTriggers());
    }
}
