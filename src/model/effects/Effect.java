package model.effects;

import java.io.Serializable;

public abstract class Effect implements Serializable {
    public int duration;
    Dispelablity dispelablity;

    public Effect(int duration, Dispelablity dispelablity) {
        this.duration = duration;
        this.dispelablity=dispelablity;
    }

    public Dispelablity getDispelablity() {
        return dispelablity;
    }
}