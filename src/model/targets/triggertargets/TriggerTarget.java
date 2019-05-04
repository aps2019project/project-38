package model.targets.triggertargets;

import model.QualityHaver;
import model.gamestate.GameState;

import java.util.ArrayList;

public interface TriggerTarget {
    ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState);

    default TriggerTarget and (TriggerTarget other){
        return new TriggerTarget(){

            @Override
            public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
                ArrayList targets = new ArrayList<>();
                try {
                    targets.addAll(this.getTarget(triggerOwner, gameState));
                    targets.addAll(other.getTarget(triggerOwner, gameState));
                }catch (Exception e){
                    System.err.println("Trying to add different types of targets to each other.");
                }
                return targets;
            }
        };
    }
}
