package model.triggers;

import model.cards.warriors.Warrior;
import model.gamestate.GameState;

public class Poisened extends Trigger{

    public Poisened(Warrior warrior) {
        super(warrior);
    }

    @Override
    void apply(GameState gameState) {

    }
}
