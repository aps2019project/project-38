package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.Died;
import model.effects.Dispelablity;
import model.gamestate.GameState;

public class OnDeath extends Trigger {
    {
        conditions.put(new Died(),true);
    }
    public OnDeath(Warrior warrior, int duration, Dispelablity dispelablity) {
        super(warrior, duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {

    }
}
