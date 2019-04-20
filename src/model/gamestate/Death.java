package model.gamestate;

import model.cards.warriors.Warrior;

public class Death extends GameState {
    private Warrior warrior;

    public Warrior getWarrior() {
        return warrior;
    }

    public Death(Warrior warrior) {
        this.warrior = warrior;
    }
}
