package model.effects;

public class HP extends Effect {
    int hp;

    public HP(int endTurn, Dispelablity dispelablity, int hp) {
        super(endTurn, dispelablity);
        this.hp = hp;
    }
}
