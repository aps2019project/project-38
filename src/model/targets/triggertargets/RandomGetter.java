package model.targets.triggertargets;

import model.QualityHaver;
import model.gamestate.GameState;

import java.util.ArrayList;
import java.util.Random;
//doesn't get hero.
public class RandomGetter implements TriggerTarget {
    private TriggerTarget targetsGetter;

    public RandomGetter(TriggerTarget targetsGetter) {
        this.targetsGetter = targetsGetter;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        Random random = new Random(System.currentTimeMillis());
        ArrayList<QualityHaver> arrayList = new ArrayList<>();
        ArrayList<QualityHaver> targets = new ArrayList<>(targetsGetter.getTarget(triggerOwner,gameState));
        arrayList.add(targets.get(random.nextInt(targets.size())));
        return arrayList;
    }
}
