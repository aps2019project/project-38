package model.triggers;

import model.Cell;
import model.Game;
import model.QualityHaver;
import model.cards.Warrior;
import model.conditions.HasDied;
import model.conditions.HasWarriorOnIt;
import model.effects.Dispelablity;
import model.gamestate.GameState;

public class Flag extends Trigger {
    {
        conditions.add(new HasDied().or(new HasWarriorOnIt()));
    }

    public Flag() {
        super(-1, Dispelablity.UNDISPELLABLE);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        Game game = getGameFromQualityHaver(owner);
        owner.removeTrigger(this);
//        game.triggRemoveBuffer.put(this, owner);
        if (owner instanceof Cell) {
//            game.triggAddBuffer.put(this, ((Cell) owner).getWarrior());
            owner.addTrigger(this);
        } else {
//            game.triggAddBuffer.put(this, ((Warrior) owner).getCell());
            owner.addTrigger(this);
        }
    }
}
