package model.triggers;

import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.conditions.HasDied;
import model.conditions.HasSpawned;
import model.effects.Dispelablity;
import model.gamestate.DeathState;
import model.gamestate.GameState;
import model.gamestate.PutMinionState;

// add effects and triggers with "-1" duration to this trigger.
public class AllFriendsPassive extends Trigger {
    {
        conditions.add(new HasSpawned().or(new HasDied()));
    }

    public AllFriendsPassive(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        Warrior ownerWarrior = (Warrior)owner;
        if(gameState instanceof PutMinionState){
            addEffectsAndTriggers(ownerWarrior);
        }

        if(gameState instanceof DeathState){
            removeEffectsAndTriggers(ownerWarrior);
        }
    }

    private void addEffectsAndTriggers(Warrior ownerWarrior){
        ownerWarrior.getCell().getBoard().getGame().getWarriorsPlayer(ownerWarrior).getWarriors().forEach(warrior ->{
            warrior.getEffects().addAll(effects);
            warrior.getTriggers().addAll(triggers);
        });
    }

    private void removeEffectsAndTriggers(Warrior ownerWarrior){
        ownerWarrior.getCell().getBoard().getGame().getWarriorsPlayer(ownerWarrior).getWarriors().forEach(warrior -> {
            warrior.getEffects().removeAll(effects);
            warrior.getTriggers().removeAll(triggers);
        });
    }
}
