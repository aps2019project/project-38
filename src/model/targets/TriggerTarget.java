package model.targets;

import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.gamestate.GameState;

public interface TriggerTarget {
    QualityHaver getTarget(QualityHaver triggerOwner, GameState gameState);
}
