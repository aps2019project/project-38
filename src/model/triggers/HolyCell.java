package model.triggers;

import model.Cell;
import model.conditions.OnCell;
import model.effects.Dispelablity;
import model.gamestate.GameState;
import model.gamestate.Move;

public class HolyCell extends Trigger {
    {
        conditions.put(new OnCell(),true);
        triggers.add(new HolyBuff(null,1,Dispelablity.GOOD));
    }

    public HolyCell(Cell cell, int duration, Dispelablity dispelablity) {
        super(cell, duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {
        Move move = (Move) gameState;
        addTriggers(move.getWarrior(),triggers);
    }
}
