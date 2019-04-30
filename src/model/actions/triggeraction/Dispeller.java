package model.actions.triggeraction;

import model.QualityHaver;
import model.triggers.Trigger;

public class Dispeller implements TriggerAction{

    @Override
    public void execute(Trigger ownerTrigger, QualityHaver target) {
        dispel(target);
    }

    public static void dispel(QualityHaver qualityHaver){

    }
}
