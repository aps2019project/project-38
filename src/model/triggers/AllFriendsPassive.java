package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.Died;
import model.conditions.Spawned;
import model.conditions.TurnStarted;
import model.effects.Dispelablity;
import model.gamestate.Death;
import model.gamestate.GameState;
import model.gamestate.PutMinion;
import model.gamestate.TurnStart;

// add effects and triggers with "1" duration to this trigger.
public class AllFriendsPassive extends Trigger {
    {
        conditions.add(new Spawned().or(new TurnStarted()).or(new Died()));
    }
    public AllFriendsPassive(Warrior warrior, int duration, Dispelablity dispelablity) {
        super(warrior, duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {
        if(gameState instanceof PutMinion || gameState instanceof TurnStart){
            addEffectsAndTriggers();
        }

        if(gameState instanceof Death){
            removeEffectsAndTriggers();
        }
    }

    void addEffectsAndTriggers(){
        getWarrior().getCell().getGame().getWarriorsPlayer(getWarrior()).getWarriors().forEach(warrior ->{
            warrior.getEffects().addAll(effects);
            addTriggersToWarriorFromTrigger(warrior,triggers);
        });
    }

    void removeEffectsAndTriggers(){
        getWarrior().getCell().getGame().getWarriorsPlayer(getWarrior()).getWarriors().forEach(warrior -> {
            warrior.getEffects().removeAll(effects);
            warrior.getTriggers().removeAll(triggers);
        });
    }
}
