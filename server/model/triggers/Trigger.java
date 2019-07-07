package model.triggers;

import model.QualityHaver;
import model.actions.AutoAction;
import model.cards.Spell;
import model.effects.Dispelablity;
import model.gamestate.GameState;
import model.conditions.Condition;
import model.targets.TriggerTarget;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Trigger extends QualityHaver implements Serializable {
    ArrayList<Condition> conditions = new ArrayList<>();
    HashMap<AutoAction, TriggerTarget> actions = new HashMap<>();

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

    public HashMap<AutoAction, TriggerTarget> getActions() {
        return actions;
    }

    public Trigger deepCopy(){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Trigger) ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            System.err.println("could not deep copy in Trigger:");
            e.printStackTrace();
        }
        return this;
    }

    public void check(GameState gameState, QualityHaver owner) {
        for (Condition condition : conditions) {
            if (!condition.check(gameState, this,owner)) {
                return;
            }
        }
        executeActions(gameState,owner);
    }

    protected void executeActions(GameState gameState, QualityHaver owner){
        for (Map.Entry<AutoAction, TriggerTarget> entry : actions.entrySet()) {
            for (QualityHaver qualityHaver : entry.getValue().getTarget(owner, gameState)) {
                entry.getKey().execute(this,qualityHaver);
            }
        }
    }
}