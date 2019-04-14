package model.triggers;

import model.effects.Effect;
import model.GameState;
import model.Player;
import model.targets.Target;
import model.conditions.Condition;

import java.util.ArrayList;

public class Trigger {
    Player owner;

    ArrayList<Effect> effects = new ArrayList<>();
    ArrayList<Trigger> triggers = new ArrayList<>();

    ArrayList<Target> targets = new ArrayList<>();

    private ArrayList<Condition> conditions = new ArrayList<>();


    public void check(GameState gameState){
        for (Condition condition : conditions) {
            if(!condition.check()){
                return;
            }
        }
        apply();
    }

    private void apply(){
        
    }
}
