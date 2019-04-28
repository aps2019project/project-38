package model.cards.spells;

import model.Cell;
import model.effects.Disarm;
import model.effects.Dispelablity;

public class TotalDisarm extends Spell {
    {
        getEffects().add(new Disarm(-1, Dispelablity.BAD));
    }

    public TotalDisarm() {
        super(11, "Total Disarm", 0, 0, false);
    }

    @Override
    public void apply(Cell cell) {
        cell.getWarrior().getEffects().addAll(this.getEffects());
    }
}
