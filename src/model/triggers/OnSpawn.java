package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.Spawned;
import model.effects.Dispelablity;
import model.gamestate.GameState;

public class OnSpawn extends Trigger {
    {
        conditions.add(new Spawned());
    }
    public OnSpawn(Warrior warrior, int duration, Dispelablity dispelablity) {
        super(warrior, duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {

    }
}
