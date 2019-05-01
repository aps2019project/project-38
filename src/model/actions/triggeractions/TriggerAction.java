package model.actions.triggeractions;

import model.QualityHaver;
import model.triggers.Trigger;

public interface TriggerAction {
    void execute(Trigger ownerTrigger, QualityHaver target);
}
