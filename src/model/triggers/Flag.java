package model.triggers;

import model.Cell;
import model.QualityHaver;
import model.cards.Warrior;
import model.conditions.HasDied;
import model.conditions.HasWarriorOnIt;
import model.effects.Dispelablity;
import model.gamestate.GameState;
import model.targets.OnCellGetter;

public class Flag extends Trigger {
    {
        conditions.add(new HasDied().or(new HasWarriorOnIt()));
    }
    public Flag() {
        super(-1, Dispelablity.UNDISPELLABLE);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        owner.getTriggers().remove(this);
        if(owner instanceof Cell){
            new OnCellGetter().getTarget(owner,gameState).get(0).getTriggers().add(this);
        }else {
            ((Warrior)owner).getCell().getTriggers().add(this);
        }
    }
}
