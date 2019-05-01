package model.triggers;

import model.QualityHaver;
import model.actions.triggeractions.TriggerAction;
import model.effects.Dispelablity;
import model.gamestate.GameState;
import model.conditions.Condition;
import model.targets.TriggerTarget;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Trigger extends QualityHaver implements Serializable {
    ArrayList<Condition> conditions = new ArrayList<>();
    HashMap<TriggerAction, TriggerTarget> actions = new HashMap<>();

    public int duration;
    Dispelablity dispelablity;

    public Trigger(int duration, Dispelablity dispelablity) {
        this.dispelablity = dispelablity;
        this.duration = duration;
    }

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    public Dispelablity getDispelablity() {
        return dispelablity;
    }

    public HashMap<TriggerAction, TriggerTarget> getActions() {
        return actions;
    }

    public void putInActions(TriggerAction triggerAction,TriggerTarget triggerTarget){
        actions.put(triggerAction,triggerTarget);
    }

    public void check(GameState gameState, QualityHaver owner) {
        for (Condition condition : conditions) {
            if (condition.check(gameState, this,owner)) {
                return;
            }
        }
        executeActions(gameState,owner);
    }

    protected void executeActions(GameState gameState, QualityHaver owner){
        for (Map.Entry<TriggerAction, TriggerTarget> entry : actions.entrySet()) {
            for (QualityHaver qualityHaver : entry.getValue().getTarget(owner, gameState)) {
                entry.getKey().execute(this,qualityHaver);
            }
        }
    }
}
