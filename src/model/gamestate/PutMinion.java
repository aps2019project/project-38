package model.gamestate;

import model.cards.warriors.Warrior;

public class PutMinion extends GameState {
    private Warrior warrior;

    public PutMinion(Warrior warrior) {
        this.warrior = warrior;
    }

    public Warrior getWarrior() {
        return warrior;
    }
}
