package model.conditions;

import model.effects.AntiHolyBuff;
import model.gamestate.Attack;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class HasAntiHolyBuff implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger) {
        if (!(gameState instanceof Attack)) {
            return false;
        }
        Attack attack = (Attack) gameState;

        return attack.getAttacker().getEffects().stream().anyMatch(effect -> effect instanceof AntiHolyBuff);
    }
}
