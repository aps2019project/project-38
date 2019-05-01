package model.triggers;

import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.conditions.HasDied;
import model.conditions.HasSpawned;
import model.conditions.HasTurnStarted;
import model.effects.Dispelablity;
import model.gamestate.DeathState;
import model.gamestate.GameState;
import model.gamestate.PutMinionState;
import model.gamestate.TurnStartState;
import model.targets.triggertargets.AllFriendsGetter;
//special because of special action.
// add effects and triggers with "1" duration to this trigger.
public class AllFriendsPassive extends Trigger {
    {
        conditions.add(new HasSpawned().or(new HasDied()).or(new HasTurnStarted()));
    }

    public AllFriendsPassive(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        Warrior ownerWarrior = (Warrior)owner;
        if(gameState instanceof PutMinionState || gameState instanceof TurnStartState){
            addEffectsAndTriggers(ownerWarrior);
        }

        if(gameState instanceof DeathState){
            removeEffectsAndTriggers(ownerWarrior);
        }
    }

    private void addEffectsAndTriggers(Warrior ownerWarrior){
        new AllFriendsGetter().getTarget(ownerWarrior,null).stream().map(o -> (Warrior)o).forEach(warrior ->{
            warrior.getEffects().addAll(effects);
            warrior.getTriggers().addAll(triggers);
        });
    }

    private void removeEffectsAndTriggers(Warrior ownerWarrior){
        new AllFriendsGetter().getTarget(ownerWarrior,null).stream().map(o -> (Warrior)o).forEach(warrior -> {
            warrior.getEffects().removeAll(effects);
            warrior.getTriggers().removeAll(triggers);
        });
    }
}
