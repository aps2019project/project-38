package model.triggers;

import model.Cell;
import model.Constant;
import model.QualityHaver;
import model.conditions.HasWarriorOnIt;
import model.effects.Dispelablity;
import model.effects.HP;
import model.gamestate.GameState;
import model.gamestate.Move;

public class FiredCell extends Trigger {
    {
        conditions.add(new HasWarriorOnIt());
        effects.add(new HP(-1, Dispelablity.UNDISPELLABLE,
                Constant.EffectsTriggersConstants.FiredCell.firedCellDamage));
    }

    public FiredCell(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        ((Cell)owner).getWarrior().getEffects().addAll(effects);
    }
}
