package model;

import model.conditions.Condition;
import model.effects.Effect;
import model.triggers.Trigger;

import java.util.ArrayList;

public abstract class QualityHaver {
    protected ArrayList<Effect> effects = new ArrayList<>();
    protected ArrayList<Trigger> triggers = new ArrayList<>();

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public ArrayList<Trigger> getTriggers() {
        return triggers;
    }

    public void addToEffects(Effect effect) {
        effects.add(effect);
    }

    public void addToTriggers(Trigger trigger) {
        triggers.add(trigger);
    }
}
