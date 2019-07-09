package model.actions;

import model.QualityHaver;
import model.cards.Warrior;
import model.effects.Effect;
import model.gamestate.EffTriggApplyState;
import model.player.Player;
import model.triggers.CollectibleMine;
import model.triggers.Trigger;
import view.fxmlControllers.ArenaController;

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

        if(source instanceof CollectibleMine){
            CollectibleMine cMine = (CollectibleMine)source;
            Game game = QualityHaver.getGameFromQualityHaver(target);
            Player player = game.getWarriorsPlayer((Warrior) target);
            player.getCollectibleItems().add(cMine.getCollectible());

            ArenaController.ac.showCollectedCollectibleItems(cMine.getCollectible().getName(),game.getPlayerNumber(player)+1);
        }

        return didSth;
    }
}
