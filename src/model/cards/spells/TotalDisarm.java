package model.cards.spells;

import model.Cell;
import model.effects.Disarm;

public class TotalDisarm extends Spell {
    public TotalDisarm() {
        ID=11;
        name="Total Disarm";
        requiredMana=0;

        effects.add(new Disarm(-1,true));
    }


    @Override
    public void apply(Cell cell) {
        cell.getWarrior().effects.addAll(this.effects);
    }
}
