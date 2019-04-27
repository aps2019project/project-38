package model.triggers;

import model.Cell;
import model.Constant;
import model.conditions.OnCell;
import model.effects.Dispelablity;
import model.gamestate.GameState;
import model.gamestate.Move;

public class Poison extends Trigger {
    {
        conditions.add(new OnCell());
        triggers.add(new Poisoned(null, Constant.EffectsTriggersConstants.CellPoison.poisonBuffDuration,
                Dispelablity.BAD));
    }

    public Poison(Cell cell, int duration, Dispelablity dispelablity) {
        super(cell, duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {
        Move move=(Move)gameState;
        addTriggersToWarriorFromTrigger(move.getWarrior(),triggers);
    }
}
