package model.triggers;

import model.effects.Effect;
import model.conditions.Condition;
import model.gamestate.GameState;
import model.player.Player;

import java.util.ArrayList;

public abstract class Trigger {
    Player owner;

    ArrayList<Effect> effects = new ArrayList<>();
    ArrayList<Trigger> triggers = new ArrayList<>();

    private ArrayList<Condition> conditions = new ArrayList<>();


    public void check(GameState gameState){
        for (Condition condition : conditions) {
            if(!condition.check()){
                return;
            }
        }
        apply();
    }

    abstract void apply();
}
