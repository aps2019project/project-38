package model.conditions;

import model.QualityHaver;
import model.effects.Melee;
import model.effects.Ranged;
import model.gamestate.Attack;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class CanCounterAttack implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        if(!(gameState instanceof Attack)){
            return false;
        }

        if(((Attack) gameState).getAttacker().getEffects().stream().anyMatch(effect -> effect instanceof Ranged)){
            if(((Attack) gameState).getAttacked().getEffects().stream().anyMatch(effect -> effect instanceof Ranged)){
                return true;
            }
        }

        if(((Attack) gameState).getAttacker().getEffects().stream().anyMatch(effect -> effect instanceof Melee)){
            if(((Attack) gameState).getAttacked().getEffects().stream().anyMatch(effect -> effect instanceof Melee)){
                return true;
            }
        }

        return false;
    }
}
