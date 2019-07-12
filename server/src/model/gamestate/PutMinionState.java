package model.gamestate;

import model.cards.Warrior;

public class PutMinionState extends GameState {
    private Warrior warrior;

    public PutMinionState(Warrior warrior) {
        this.warrior = warrior;
    }

    public Warrior getWarrior() {
        return warrior;
    }
}
