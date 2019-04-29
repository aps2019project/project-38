package model.triggers;

import model.QualityHaver;
import model.effects.Dispelablity;
import model.gamestate.GameState;
import model.conditions.Condition;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Trigger extends QualityHaver implements Serializable {
    //used for adding triggers stored in a trigger to another warrior because their owners should be set again.
//    private Warrior warrior;
//    private Cell cell;
    ArrayList<Condition> conditions = new ArrayList<>();
    int duration;
    Dispelablity dispelablity;

    public Trigger(int duration, Dispelablity dispelablity) {
        this.dispelablity = dispelablity;
        this.duration = duration;
    }

//    public Warrior getWarrior() {
//        return warrior;
//    }
//
//    public Cell getCell() {
//        return cell;
//    }

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    public void addToConditions(Condition condition) {
        conditions.add(condition);
    }

    public void check(GameState gameState, QualityHaver owner) {
//        setOwner(owner);
        for (Condition condition : conditions) {
            if (condition.check(gameState, this,owner)) {
                return;
            }
        }
        apply(gameState,owner);
    }

//    private void setOwner(Object owner) {
//        cell = null;
//        warrior = null;
//
//        if (owner instanceof Cell) {
//            cell = (Cell) owner;
//        } else if (owner instanceof Warrior) {
//            warrior = (Warrior) owner;
//        }
//    }

    protected abstract void apply(GameState gameState, QualityHaver owner);
}
