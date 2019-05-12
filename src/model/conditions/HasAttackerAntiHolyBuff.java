package model.conditions;

import model.QualityHaver;
import model.effects.AntiHolyBuff;
import model.gamestate.AttackState;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class HasAttackerAntiHolyBuff implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        if (!(gameState instanceof AttackState)) {
            return false;
        }
        AttackState attackState = (AttackState) gameState;

        return attackState.getAttacker().getEffects().stream().anyMatch(effect -> effect instanceof AntiHolyBuff)
                || attackState.getAttacker().getEffectsBuffer().stream().anyMatch(effect -> effect instanceof AntiHolyBuff);
    }
}
