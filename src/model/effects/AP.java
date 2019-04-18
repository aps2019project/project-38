package model.effects;

public class AP extends Effect{
    int ap;

    public AP(int endTurn, Dispelablity dispelablity, int ap) {
        super(endTurn, dispelablity);
        this.ap = ap;
    }
}
