package model.actions.triggeraction;

import model.QualityHaver;

public class Applier implements TriggerAction {
    @Override
    public void execute(Trigger ownerTrigger, QualityHaver target) {
        if(target==null || ownerTrigger==null)
            return;

        target.getEffects().addAll(ownerTrigger.getEffects());
        target.getTriggers().addAll(ownerTrigger.getTriggers());
    }
}
