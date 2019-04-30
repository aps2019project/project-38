package model.actions.triggeraction;

import model.QualityHaver;

public class Applier implements TriggerAction {
    @Override
    public void execute(QualityHaver source, QualityHaver target) {
        if(target==null || source==null)
            return;

        target.getEffects().addAll(source.getEffects());
        target.getTriggers().addAll(source.getTriggers());
    }
}
