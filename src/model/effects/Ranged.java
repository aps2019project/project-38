package model.effects;

public class Ranged extends Effect {
    private int range; //todo constructor
    public Ranged(int duration, Dispelablity dispelablity,int range) {
        super(duration, dispelablity);
        this.range = range;
    }

    public int getRange() {
        return range;
    }
}
