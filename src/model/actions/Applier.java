package model.actions;

import model.QualityHaver;
import model.effects.Effect;
import model.gamestate.EffTriggApplyState;
import model.player.Player;
import model.triggers.Trigger;

import static model.QualityHaver.getGameFromQualityHaver;

public class Applier implements TriggerAction, SpellAction {
    @Override
    public void execute(QualityHaver source, QualityHaver target) {
        if (target == null || source == null)
            return;

        for (Trigger trigger : source.getTriggers()) {
            EffTriggApplyState state = new EffTriggApplyState(target, trigger);
            getGameFromQualityHaver(target).iterateAllTriggersCheck(state);
            if (!state.canceled) {
                target.getTriggers().add(trigger);

                state.pending = false;
                getGameFromQualityHaver(target).iterateAllTriggersCheck(state);
            }
        }

        for (Effect effect : source.getEffects()) {
            EffTriggApplyState state = new EffTriggApplyState(target, effect);
            getGameFromQualityHaver(target).iterateAllTriggersCheck(state);
            if (!state.canceled) {
                target.getEffects().add(effect);

                state.pending = false;
                getGameFromQualityHaver(target).iterateAllTriggersCheck(state);
            }
        }
    }

    @Override
    public void execute(Player spellOwner, QualityHaver target) {
        //todo
    }
}
