package model.conditions;

import model.QualityHaver;
import model.cards.Warrior;
import model.effects.AP;
import model.effects.Dispelablity;
import model.effects.Effect;
import model.effects.HP;
import model.gamestate.GameState;
import model.gamestate.UseSpellState;
import model.triggers.Trigger;

public class IsBadSpellComin implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        assert triggerOwner instanceof Warrior;
        if (!(gameState instanceof UseSpellState)) {
            return false;
        }
        UseSpellState useSpellState = (UseSpellState) gameState;
        Warrior warrior = (Warrior)triggerOwner;

        boolean isBad = false;
        for (Effect effect : useSpellState.spell.getEffects()) {
            if (effect.getDispelablity() == Dispelablity.BAD) {
                isBad = true;
                break;
            } else if (effect instanceof HP && ((HP) effect).getHp() < 0) {
                isBad = true;
                break;
            } else if (effect instanceof AP && ((AP) effect).getAp() < 0) {
                isBad = true;
                break;
            }
        }
        for (Trigger spellTrigger : useSpellState.spell.getTriggers()) {
            if (spellTrigger.getDispelablity() == Dispelablity.BAD) {
                isBad = true;
                break;
            }
        }

        return isBad && useSpellState.targetCell.equals(warrior.getCell());
    }
}
