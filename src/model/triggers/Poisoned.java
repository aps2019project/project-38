package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.HasTurnEnded;
import model.effects.Dispelablity;
import model.effects.HP;
import model.gamestate.GameState;
import model.gamestate.Move;

public class Poisoned extends Trigger{
    {
        conditions.add(new HasTurnEnded());
        effects.add(new HP(-1, Dispelablity.UNDISPELLABLE,-1));
    }

    public Poisoned(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {
        getWarrior().effects.addAll(effects);
    }
}
