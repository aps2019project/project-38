package model.cards.spells;

import model.effects.Disarm;

public class TotalDisarm extends Spell {
    public TotalDisarm() {
        ID=11;
        name="Total Disarm";
        requiredMana=0;

        effects.add(new Disarm(-1,true));
    }
}
