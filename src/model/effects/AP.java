package model.effects;

public class AP extends Effect{
    private int ap;

    public AP(int duration, Dispelablity dispelablity, int ap) {
        super(duration, dispelablity);
        this.ap = ap;
    }

    public int getAp() {
        return ap;
    }
}
