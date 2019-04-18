package model.triggers;

import model.cards.warriors.Warrior;
import model.effects.Effect;
import model.gamestate.GameState;
import model.conditions.Condition;

import java.util.ArrayList;

public abstract class Trigger{
    Warrior warrior;

    public ArrayList<Effect> effects = new ArrayList<>();
    public ArrayList<Trigger> triggers = new ArrayList<>();

    public ArrayList<Condition> conditions = new ArrayList<>();


    public void check(GameState gameState){
        for (Condition condition : conditions) {
            if(!condition.check(gameState,this)){
                return;
            }
        }
        apply();
    }

    abstract void apply();
}
