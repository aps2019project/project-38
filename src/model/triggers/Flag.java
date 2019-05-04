package model.triggers;

import model.effects.Dispelablity;

public class Flag extends Trigger {
    public Flag() {
        super(-1, Dispelablity.UNDISPELLABLE);
        //todo --> conditions
    }
}
