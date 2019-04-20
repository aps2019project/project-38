package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.Moved;
import model.conditions.Spawned;
import model.conditions.TurnEnded;
import model.effects.Dispelablity;
import model.gamestate.GameState;
//add triggers and effects with "1" duration to this trigger.
public class NearWarriorsPassive extends Trigger {
    {
        conditions.add(new Spawned().or(new Moved()).or(new TurnEnded()));
    }
    public NearWarriorsPassive(Warrior warrior, int duration, Dispelablity dispelablity) {
        super(warrior, duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {

    }
}
