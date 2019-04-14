package model.effects;

public class HP extends Effect {
    int hp;

    public HP(int endTurn, boolean dispellable, int hp) {
        super(endTurn, dispellable);
        this.hp = hp;
    }
}
