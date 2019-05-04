package model.actions.triggeractions;

import model.QualityHaver;
import model.effects.Dispelablity;
import model.gamestate.DispelState;
import model.triggers.Trigger;

public class Dispeller implements TriggerAction{
    private Dispelablity dispelType;

    public Dispeller(Dispelablity dispelType) {
        this.dispelType = dispelType;
    }

    @Override
    public void execute(QualityHaver source, QualityHaver target) {
        dispel(target,dispelType);
    }

    public static void dispel(QualityHaver qualityHaver,Dispelablity dispelType){
        qualityHaver.getEffects().removeIf(effect -> effect.getDispelablity().equals(dispelType));

        for (Trigger trigger : qualityHaver.getTriggers()) {
            if(trigger.getDispelablity().equals(dispelType)) {
                DispelState state = new DispelState(trigger);
                QualityHaver.getGameFromQualityHaver(qualityHaver).iterateAllTriggers(state);
                qualityHaver.getTriggers().remove(trigger);
            }
        }
    }
}
