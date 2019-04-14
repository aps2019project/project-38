package model.effects;

public abstract class Effect {
    int endTurn;
    boolean dispellable;

    public Effect(int endTurn, boolean dispellable) {
        this.endTurn = endTurn;
        this.dispellable = dispellable;
    }
}