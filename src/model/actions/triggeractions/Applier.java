package model.actions.triggeractions;

import model.Cell;
import model.Game;
import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.effects.Effect;
import model.gamestate.EffTriggApplyState;
import model.triggers.Trigger;

public class Applier implements TriggerAction {
    @Override
    public void execute(QualityHaver source, QualityHaver target) {
        if(target==null || source==null)
            return;

        for (Trigger trigger : source.getTriggers()) {
            EffTriggApplyState state = new EffTriggApplyState(target,trigger);
            getGameFromTarget(target).iterateAllTriggers(state);
            if(!state.canceled){
                target.getTriggers().add(trigger);
            }
        }

        for (Effect effect : source.getEffects()) {
            EffTriggApplyState state = new EffTriggApplyState(target,effect);
            getGameFromTarget(target).iterateAllTriggers(state);
            if(!state.canceled){
                target.getEffects().add(effect);
            }
        }
    }

    private Game getGameFromTarget(QualityHaver target){
        assert ((target instanceof Warrior)|(target instanceof Cell));

        if(target instanceof Warrior){
            return ((Warrior)target).getCell().getBoard().getGame();
        }else {
            return ((Cell)target).getBoard().getGame();
        }
    }
}
