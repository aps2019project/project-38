package model.effects;

public class HP extends Effect {
    int hp;

    public HP(int duration, Dispelablity dispelablity, int hp) {
        super(duration, dispelablity);
        this.hp = hp;
    }
}
