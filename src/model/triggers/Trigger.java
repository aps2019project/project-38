package model.triggers;

import model.QualityHaver;
import model.effects.Dispelablity;
import model.gamestate.GameState;
import model.conditions.Condition;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Trigger extends QualityHaver implements Serializable {
    ArrayList<Condition> conditions = new ArrayList<>();
    int duration;
    Dispelablity dispelablity;

    public Trigger(int duration, Dispelablity dispelablity) {
        this.dispelablity = dispelablity;
        this.duration = duration;
    }

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    public void addToConditions(Condition condition) {
        conditions.add(condition);
    }

    public void check(GameState gameState, QualityHaver owner) {
        for (Condition condition : conditions) {
            if (condition.check(gameState, this,owner)) {
                return;
            }
        }
        apply(gameState,owner);
    }

    protected abstract void apply(GameState gameState, QualityHaver owner);
}
