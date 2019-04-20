package model.effects;

public class AP extends Effect{
    int ap;

    public AP(int duration, Dispelablity dispelablity, int ap) {
        super(duration, dispelablity);
        this.ap = ap;
    }
}
