package model.gamestate;

import model.triggers.Trigger;

public class DispelState extends GameState {
    private Trigger beingDispelled;

    public Trigger getBeingDispelled() {
        return beingDispelled;
    }

    public DispelState(Trigger beingDispelled) {
        this.beingDispelled = beingDispelled;
    }
}
