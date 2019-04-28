package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.HasDied;
import model.effects.Dispelablity;
import model.gamestate.GameState;

public class OnDeath extends Trigger {
    {
        conditions.add(new HasDied());
    }

    public OnDeath(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {

    }
}
