package model.actions.triggeractions;

import model.Cell;
import model.Game;
import model.QualityHaver;
import model.cards.Warrior;
import model.triggers.Trigger;

public interface TriggerAction {
    void execute(QualityHaver source, QualityHaver target);
}
