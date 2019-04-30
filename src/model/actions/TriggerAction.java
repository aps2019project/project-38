package model.actions;

import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.triggers.Trigger;

public interface TriggerAction {
    void execute(Trigger trigger, QualityHaver target);
}
