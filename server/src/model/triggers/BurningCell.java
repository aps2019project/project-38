package model.triggers;

import model.effects.Dispelablity;
import model.effects.HP;

public class BurningCell extends Mine {
    {
        effects.add(new HP(-1,Dispelablity.UNDISPELLABLE,-1));
    }

    public BurningCell(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }
}
