package model.conditions;

import model.QualityHaver;
import model.gamestate.AttackState;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class IsBeingAttacked implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        if(!(gameState instanceof AttackState)){
            return false;
        }
        AttackState attackState =(AttackState)gameState;

        return attackState.getAttacked().equals(triggerOwner) && attackState.isPending();
    }
}
