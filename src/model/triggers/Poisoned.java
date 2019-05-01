package model.triggers;

import model.QualityHaver;
import model.conditions.HasTurnEnded;
import model.effects.Dispelablity;
import model.effects.HP;
import model.gamestate.GameState;
//special because so common.
public class Poisoned extends Trigger{
    {
        conditions.add(new HasTurnEnded());
        effects.add(new HP(-1, Dispelablity.UNDISPELLABLE,-1));
    }

    public Poisoned(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        owner.getEffects().addAll(effects);
    }
}
