package model.triggers;

import model.Cell;
import model.cards.warriors.Warrior;
import model.effects.Dispelablity;
import model.effects.Effect;
import model.gamestate.GameState;
import model.conditions.Condition;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Trigger {
    //used for adding triggers stored in a trigger to another warrior because their owners should be set again.
    private Warrior warrior;
    private Cell cell;
    ArrayList<Effect> effects = new ArrayList<>();
    ArrayList<Trigger> triggers = new ArrayList<>();
    ArrayList<Condition> conditions = new ArrayList<>();
    int duration;
    Dispelablity dispelablity;

    public Trigger(int duration, Dispelablity dispelablity) {
        this.dispelablity = dispelablity;
        this.duration = duration;
    }

    public Warrior getWarrior() {
        return warrior;
    }

    public Cell getCell() {
        return cell;
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public ArrayList<Trigger> getTriggers() {
        return triggers;
    }

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    public void addToEffects(Effect effect) {
        effects.add(effect);
    }

    public void addToConditions(Condition condition) {
        conditions.add(condition);
    }

    public void addToTriggers(Trigger trigger) {
        triggers.add(trigger);
    }

    public void check(GameState gameState, Object owner) {
        setOwner(owner);
        for (Condition condition : conditions) {
            if (condition.check(gameState, this)) {
                return;
            }
        }
        apply(gameState);
    }

    private void setOwner(Object owner) {
        cell = null;
        warrior = null;

        if (owner instanceof Cell) {
            cell = (Cell) owner;
        } else if (owner instanceof Warrior) {
            warrior = (Warrior) owner;
        }
    }

    protected abstract void apply(GameState gameState);
}
