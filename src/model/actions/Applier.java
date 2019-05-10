package model.actions;

import model.QualityHaver;
import model.effects.Effect;
import model.gamestate.EffTriggApplyState;
import model.triggers.Trigger;

import static model.QualityHaver.getGameFromQualityHaver;

public class Applier implements AutoAction {
    @Override
    public void execute(QualityHaver source, QualityHaver target) {
        if (target == null || source == null)
            return;

        for (Trigger trigger : source.getTriggers()) {
            EffTriggApplyState state = new EffTriggApplyState(target, trigger);
            getGameFromQualityHaver(target).iterateAllTriggersCheck(state);
            if (!state.canceled) {
//                getGameFromQualityHaver(target).triggAddBuffer.put(trigger.deepCopy(),target);
                target.addTrigger(trigger);

                state.pending = false;
                getGameFromQualityHaver(target).iterateAllTriggersCheck(state);
            }
        }

        for (Effect effect : source.getEffects()) {
            EffTriggApplyState state = new EffTriggApplyState(target, effect);
            getGameFromQualityHaver(target).iterateAllTriggersCheck(state);
            if (!state.canceled) {
//                getGameFromQualityHaver(target).effAddBuffer.put(effect.deepCopy(),target);
                target.addEffect(effect);

                state.pending = false;
                getGameFromQualityHaver(target).iterateAllTriggersCheck(state);
            }
        }
    }
}
