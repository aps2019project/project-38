package model.triggers;

import model.cards.warriors.Warrior;
import model.effects.Effect;
import model.gamestate.GameState;
import model.conditions.Condition;
import model.gamestate.GameState;
import model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Trigger {
    private Warrior warrior;
    public ArrayList<Effect> effects = new ArrayList<>();
    public ArrayList<Trigger> triggers = new ArrayList<>();
    public HashMap<Condition, Boolean> conditions = new HashMap<>();

    public Trigger(Warrior warrior) {
        this.warrior = warrior;
    }

    public Warrior getWarrior() {
        return warrior;
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
