package model.actions;

import model.QualityHaver;
import model.effects.Effect;
import model.gamestate.EffTriggApplyState;
import model.player.Player;
import model.triggers.Trigger;

import java.io.Serializable;

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
//                target.getTriggers().add(trigger);
                getGameFromQualityHaver(source).triggBuffer.put(trigger,target);

                state.pending = false;
                getGameFromQualityHaver(target).iterateAllTriggersCheck(state);
            }
        }

        for (Effect effect : source.getEffects()) {
            EffTriggApplyState state = new EffTriggApplyState(target, effect);
            getGameFromQualityHaver(target).iterateAllTriggersCheck(state);
            if (!state.canceled) {
//                target.getEffects().add(effect);
                getGameFromQualityHaver(source).effBuffer.put(effect,target);

                state.pending = false;
                getGameFromQualityHaver(target).iterateAllTriggersCheck(state);
            }
        }
    }
}
