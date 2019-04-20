package model.effects;

public abstract class Effect {
    int duration;
    Dispelablity dispelablity;

    public Effect(int duration,Dispelablity dispelablity) {
        this.duration = duration;
        this.dispelablity=dispelablity;
    }
}