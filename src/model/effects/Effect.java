package model.effects;

public abstract class Effect {
    int endTurn;
    Dispelablity dispelablity;

    public Effect(int endTurn,Dispelablity dispelablity) {
        this.endTurn = endTurn;
        this.dispelablity=dispelablity;
    }
}