package model.effects;

public abstract class Effect {
    public int duration;
    private Dispelablity dispelablity;

    public Effect(int duration,Dispelablity dispelablity) {
        this.duration = duration;
        this.dispelablity=dispelablity;
    }

    public Dispelablity getDispelablity() {
        return dispelablity;
    }
}