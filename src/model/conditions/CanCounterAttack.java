package model.conditions;

import model.QualityHaver;
import model.effects.Melee;
import model.effects.Ranged;
import model.gamestate.AttackState;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class CanCounterAttack implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        if(!(gameState instanceof AttackState)){
            return false;
        }

        if(((AttackState) gameState).getAttacker().getEffects().stream().anyMatch(effect -> effect instanceof Ranged)){
            if(((AttackState) gameState).getAttacked().getEffects().stream().anyMatch(effect -> effect instanceof Ranged)){
                return true;
            }
        }

        if(((AttackState) gameState).getAttacker().getEffects().stream().anyMatch(effect -> effect instanceof Melee)){
            if(((AttackState) gameState).getAttacked().getEffects().stream().anyMatch(effect -> effect instanceof Melee)){
                return true;
            }
        }

        return false;
    }
}
