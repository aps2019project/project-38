package model.actions;

import model.QualityHaver;
import model.effects.Effect;
import model.gamestate.EffTriggApplyState;
import model.triggers.Trigger;

import static model.QualityHaver.getGameFromQualityHaver;

public class Applier implements AutoAction {
    @Override
    public boolean execute(QualityHaver source, QualityHaver target) {
        boolean didSth = false;

        if (target == null || source == null)
            return didSth;

        for (Trigger trigger : source.getTriggers()) {
            EffTriggApplyState state = new EffTriggApplyState(target, trigger);
            getGameFromQualityHaver(target).iterateAllTriggersCheck(state);
            if (!state.canceled) {
                target.addTrigger(trigger);

                didSth = true;
                state.pending = false;
                getGameFromQualityHaver(target).iterateAllTriggersCheck(state);
            }
        }

        for (Effect effect : source.getEffects()) {
            EffTriggApplyState state = new EffTriggApplyState(target, effect);
            getGameFromQualityHaver(target).iterateAllTriggersCheck(state);
            if (!state.canceled) {
                target.addEffect(effect);

                didSth = true;
                state.pending = false;
                getGameFromQualityHaver(target).iterateAllTriggersCheck(state);
            }
        }

        return didSth;
    }
}
