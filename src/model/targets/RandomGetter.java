package model.targets;

import model.Cell;
import model.QualityHaver;
import model.gamestate.GameState;
import model.player.Player;
import model.targets.spelltargets.SpellTarget;
import model.targets.triggertargets.TriggerTarget;

import java.util.ArrayList;
import java.util.Random;

public class RandomGetter implements TriggerTarget ,SpellTarget{
    private TriggerTarget triggerTarget;
    private SpellTarget spellTarget;

    public RandomGetter(SpellTarget spellTarget) {
        this.spellTarget = spellTarget;
    }

    public RandomGetter(TriggerTarget triggerTarget) {
        this.triggerTarget = triggerTarget;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        return returnSORandom(triggerTarget.getTarget(triggerOwner,gameState));
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(Player spellOwner, Cell cell) {
        return returnSORandom(spellTarget.getTarget(spellOwner,cell));
    }

    private ArrayList<? extends QualityHaver> returnSORandom(ArrayList<? extends QualityHaver> targets){
        Random random = new Random(System.currentTimeMillis());
        ArrayList<QualityHaver> arrayList = new ArrayList<>();//i don't get it. why cant i write <? extend QualityHaver> in generic.
        arrayList.add(targets.get(random.nextInt(targets.size())));
        return arrayList;
    }
}
