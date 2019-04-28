package model.triggers;

import model.Constant;
import model.conditions.HasWarriorOnIt;
import model.effects.Dispelablity;
import model.gamestate.GameState;
import model.gamestate.Move;

public class Poison extends Trigger {
    {
        conditions.add(new HasWarriorOnIt());
        triggers.add(new Poisoned(Constant.EffectsTriggersConstants.CellPoison.poisonBuffDuration,
                Dispelablity.BAD));
    }

    public Poison(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {
        Move move=(Move)gameState;
        move.getWarrior().triggers.addAll(triggers);
    }
}
