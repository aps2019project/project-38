package model.actions;

import model.Cell;
import model.Game;
import model.QualityHaver;
import model.cards.Warrior;
import model.triggers.Trigger;

import java.io.Serializable;

public interface AutoAction extends Serializable {
    boolean execute(QualityHaver source, QualityHaver target);
}
