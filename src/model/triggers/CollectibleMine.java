package model.triggers;

import model.QualityHaver;
import model.actions.AutoAction;
import model.actions.Move;
import model.cards.Spell;
import model.effects.Dispelablity;
import model.gamestate.GameState;
import model.gamestate.MoveState;
import model.gamestate.PutMinionState;

public class CollectibleMine extends Mine {
    private Spell collectible;

    public CollectibleMine(int duration, Dispelablity dispelablity, Spell collectible/*must be deep copied*/) {
        super(duration, dispelablity);
        this.collectible = collectible;
    }

    public Spell getCollectible() {
        return collectible;
    }

    public void setCollectible(Spell collectible) {
        this.collectible = collectible;
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        super.executeActions(gameState, owner);
        owner.removeTrigger(this);
//        if(gameState instanceof MoveState) {
//            ((MoveState) gameState).getWarrior().getCell().removeTrigger(this);
//        }else{
//            ((PutMinionState)gameState).getWarrior().getCell().removeTrigger(this);
//        }
    }
}
