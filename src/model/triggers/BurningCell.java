package model.triggers;

import model.actions.Applier;
import model.conditions.HasWarriorOnIt;
import model.effects.Dispelablity;
import model.effects.HP;
import model.targets.OnCellGetter;

public class BurningCell extends Trigger {
    {
        conditions.add(new HasWarriorOnIt());
        actions.put(new Applier(),new OnCellGetter());
        effects.add(new HP(-1,Dispelablity.UNDISPELLABLE,-1));
    }
    public BurningCell(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }
}
