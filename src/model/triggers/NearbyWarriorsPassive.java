package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.Moved;
import model.conditions.Spawned;
import model.conditions.TurnStarted;
import model.effects.Dispelablity;
import model.gamestate.GameState;
//add triggers and effects with "1" duration to this trigger.
public class NearbyWarriorsPassive extends Trigger {
    {
        conditions.add(new Spawned().or(new Moved()).or(new TurnStarted()));

    }
    public NearbyWarriorsPassive(Warrior warrior, int duration, Dispelablity dispelablity) {
        super(warrior, duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {

    }
}
