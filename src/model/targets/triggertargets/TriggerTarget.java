package model.targets.triggertargets;

import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.gamestate.GameState;

import java.util.ArrayList;

public interface TriggerTarget {
    ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState);
}
