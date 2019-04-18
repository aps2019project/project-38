package model.cards.spells;

import model.Cell;
import model.effects.Disarm;
import model.effects.Dispelablity;

public class TotalDisarm extends Spell {
    {
        ID = 11;
        name = "Total Disarm";
        requiredMana = 0;

        effects.add(new Disarm(-1, Dispelablity.BAD));
    }

    @Override
    public void apply(Cell cell) {
        cell.getWarrior().effects.addAll(this.effects);
    }
}
