package model.effects;

public class Ranged extends Effect {
    int range;

    public Ranged(int duration, Dispelablity dispelablity, int range) {
        super(duration, dispelablity);
        this.range = range;
    }
}
