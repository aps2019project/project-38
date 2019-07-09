package model.triggers;

import model.QualityHaver;
import model.conditions.*;
import model.effects.Dispelablity;
import model.effects.Effect;
import model.gamestate.*;
import model.targets.TriggerTarget;

import java.util.ArrayList;

/*special because of complexity*/
//add triggers and effects with "1" duration to this trigger.
// the target in constructor is meant to be all friends,nearbyEnemies,nearbyFriends
public class Aura extends Trigger {
    private TriggerTarget triggerTarget;
    private ArrayList<Effect> addedEffects = new ArrayList<>();
    private ArrayList<Trigger> addedTriggers = new ArrayList<>();

    {
        Condition anyMove = (Condition) (gameState, trigger, triggerOwner) -> gameState instanceof MoveState;
        Condition anySpawn = (Condition) (gameState, trigger, triggerOwner) -> gameState instanceof PutMinionState;
        Condition anyCard = (Condition) (gameState, trigger, triggerOwner) -> gameState instanceof UseSpellState;

        conditions.add(anySpawn.or(new HasDied()).or(new HasTurnStarted()).or(anyMove)
                .or(new IsBeingDispelled()).or(anyCard));
    }

    public Aura(int duration, Dispelablity dispelablity, TriggerTarget triggerTarget) {
        super(duration, dispelablity);
        this.triggerTarget=triggerTarget;
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        if(gameState instanceof DeathState || gameState instanceof DispelState){
            removeEffectsAndTriggers(owner,gameState);
        }

        if(gameState instanceof MoveState){
            MoveState moveState = (MoveState) gameState;

            if(!moveState.pending) {
                addEffectsAndTriggers(owner,gameState);
            }else {
                removeEffectsAndTriggers(owner,gameState);
            }
        }

        if(gameState instanceof PutMinionState || gameState instanceof TurnStartState || gameState instanceof UseSpellState){
            removeEffectsAndTriggers(owner,gameState);
            addEffectsAndTriggers(owner,gameState);
        }
    }

    private void addEffectsAndTriggers(QualityHaver owner,GameState gameState){
//        Game game = getGameFromQualityHaver(owner);
        triggerTarget.getTarget(owner,gameState).forEach(warrior -> {
//            for (Trigger trigger : triggers) {
//                game.triggAddBuffer.put(trigger,warrior);
//            }
//            for (Effect effect : effects) {
//                game.effAddBuffer.put(effect,warrior);
//            }
            addedEffects.addAll(warrior.addEffect(effects));
            addedTriggers.addAll(warrior.addTrigger(triggers));
        });
    }

    private void removeEffectsAndTriggers(QualityHaver owner,GameState gameState){
//        Game game = getGameFromQualityHaver(owner);
        triggerTarget.getTarget(owner,gameState).forEach(warrior -> {
//            for (Effect effect : effects) {
//                game.effRemoveBuffer.put(effect,warrior);
//            }
//            for (Trigger trigger : triggers) {
//                game.triggRemoveBuffer.put(trigger,warrior);
//            }
//            warrior.addEffect(effects);
//            warrior.addTrigger(triggers);
            warrior.removeTrigger(addedTriggers);
            warrior.removeEffect(addedEffects);
        });
        addedTriggers.clear();
        addedEffects.clear();
    }
}