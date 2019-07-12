package model.gamestate;

import model.cards.Warrior;

public class DeathState extends GameState {
    private Warrior warrior;

    public Warrior getWarrior() {
        return warrior;
    }

    public DeathState(Warrior warrior) {
        this.warrior = warrior;
    }
}
