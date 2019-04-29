package model.effects;

import java.io.Serializable;

public abstract class Effect implements Serializable {
    protected int duration;
    Dispelablity dispelablity;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Effect(int duration, Dispelablity dispelablity) {
        this.duration = duration;
        this.dispelablity=dispelablity;
    }
}