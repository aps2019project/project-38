package model.actions;

import model.Cell;
import model.Game;
import model.QualityHaver;
import model.cards.Warrior;
import model.triggers.Trigger;

public interface AutoAction {
    void execute(QualityHaver source, QualityHaver target);
}
