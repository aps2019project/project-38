package model.triggers;

import model.Cell;
import model.Constant;
import model.cards.warriors.Warrior;
import model.conditions.OnCell;
import model.effects.Dispelablity;
import model.effects.HP;
import model.gamestate.GameState;
import model.gamestate.Move;

public class FiredCell extends Trigger {
    {
        conditions.add(new OnCell());
        effects.add(new HP(-1, Dispelablity.UNDISPELLABLE,
                Constant.EffectsTriggersConstants.FiredCell.firedCellDamage));
    }

    public FiredCell(Cell cell, int duration, Dispelablity dispelablity) {
        super(cell, duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {
        Move move = (Move)gameState;
        move.getWarrior().getEffects().addAll(effects);
    }
}
