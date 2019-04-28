package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.HasDied;
import model.conditions.HasSpawned;
import model.conditions.HasTurnStarted;
import model.effects.Dispelablity;
import model.gamestate.Death;
import model.gamestate.GameState;
import model.gamestate.PutMinion;
import model.gamestate.TurnStart;

// add effects and triggers with "1" duration to this trigger.
public class AllFriendsPassive extends Trigger {
    {
        conditions.add(new HasSpawned().or(new HasTurnStarted()).or(new HasDied()));
    }

    public AllFriendsPassive(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
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
            warrior.effects.addAll(effects);
            warrior.triggers.addAll(triggers);
        });
    }

    void removeEffectsAndTriggers(){
        getWarrior().getCell().getGame().getWarriorsPlayer(getWarrior()).getWarriors().forEach(warrior -> {
            warrior.effects.removeAll(effects);
            warrior.triggers.removeAll(triggers);
        });
    }
}
