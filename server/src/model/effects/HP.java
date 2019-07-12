package model.effects;

public class HP extends Effect {
    private int hp;

    public int getHp() {
        return hp;
    }

    public HP(int duration, Dispelablity dispelablity, int hp) {
        super(duration, dispelablity);
        this.hp = hp;
    }
}
