package model.triggers;

import model.Cell;
import model.conditions.OnCell;
import model.gamestate.GameState;
import model.gamestate.Move;

public class Poison extends Trigger {
    {
        conditions.put(new OnCell(),true);
        triggers.add(new Poisened(null));
    }

    public Poison(Cell cell) {
        super(cell);
    }

    @Override
    void apply(GameState gameState) {
        Move move=(Move)gameState;
        addTriggers(move.getWarrior(),triggers);
    }
}
