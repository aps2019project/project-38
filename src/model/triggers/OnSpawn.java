package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.HasSpawned;
import model.effects.Dispelablity;
import model.gamestate.GameState;

public class OnSpawn extends Trigger {
    {
        conditions.add(new HasSpawned());
    }

    public OnSpawn(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {

    }
}
