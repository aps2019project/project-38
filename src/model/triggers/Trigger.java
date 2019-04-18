package model.triggers;

import model.Cell;
import model.cards.warriors.Warrior;
import model.effects.Effect;
import model.gamestate.GameState;
import model.conditions.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public abstract class Trigger {
    static void addTriggers(Warrior owner, ArrayList<Trigger> triggers){
        owner.triggers.addAll(triggers.stream().peek(trigger -> trigger.warrior=owner).collect(Collectors.toList()));
    }

    private Warrior warrior;
    private Cell cell;
    public ArrayList<Effect> effects = new ArrayList<>();
    public ArrayList<Trigger> triggers = new ArrayList<>();
    public HashMap<Condition, Boolean> conditions = new HashMap<>();

    public Trigger(Warrior warrior) {
        this.warrior = warrior;
    }

    public Trigger(Cell cell) {
        this.cell = cell;
    }

    public Warrior getWarrior() {
        return warrior;
    }

    public Cell getCell() {
        return cell;
    }

    public void check(GameState gameState) {
        for (Condition condition : conditions.keySet()) {
            if (condition.check(gameState, this) == conditions.get(condition)) {
                return;
            }
        }
        apply(gameState);
    }


    abstract void apply(GameState gameState);
}
