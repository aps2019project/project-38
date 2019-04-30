package model.conditions;

import model.QualityHaver;
import model.effects.Dispelablity;
import model.effects.Effect;
import model.gamestate.GameState;
import model.gamestate.UseSpellState;
import model.triggers.Trigger;

public class IsBadSpellComin implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        if(!(gameState instanceof UseSpellState)){
            return false;
        }
        UseSpellState useSpellState = (UseSpellState)gameState;

        boolean isBad=false;
        for (Effect effect : useSpellState.spell.getEffects()) {
            if(effect.getDispelablity() == Dispelablity.BAD){
                isBad=true;
                break;
            }
        }
        for (Trigger spellTrigger : useSpellState.spell.getTriggers()) {
            if(spellTrigger.getDispelablity() == Dispelablity.BAD){
                isBad=true;
                break;
            }
        }

        return isBad && useSpellState.target.equals(triggerOwner);
    }
}
