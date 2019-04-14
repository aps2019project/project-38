package model.effects;

public class AP extends Effect{
    int ap;

    public AP(int endTurn, boolean dispellable, int ap) {
        super(endTurn, dispellable);
        this.ap = ap;
    }
}
