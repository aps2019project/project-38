package model.actions;

import model.QualityHaver;
import model.triggers.Trigger;

public class Dispeller implements TriggerAction{

    @Override
    public void execute(QualityHaver source, QualityHaver target) {
        dispel(target);
    }

    public static void dispel(QualityHaver qualityHaver){

    }
}
