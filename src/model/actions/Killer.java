package model.actions;

import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.triggers.Trigger;

public class Killer implements TriggerAction{

    @Override
    public void execute(QualityHaver source, QualityHaver target) {
        if(!(target instanceof Warrior))
            return;

        kill((Warrior)target);
    }

    public void kill(Warrior warrior){

    }
}
