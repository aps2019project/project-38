package model.actions;

import model.Cell;
import model.QualityHaver;
import model.cards.Spell;
import model.cards.Warrior;
import model.effects.Dispelablity;
import model.effects.Effect;
import model.gamestate.DispelState;
import model.triggers.Trigger;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class Dispeller implements AutoAction{

    @Override
    public boolean execute(QualityHaver source, QualityHaver target) {
        assert ((target instanceof Cell)||(target instanceof Warrior))&&((source instanceof Trigger)||(source instanceof Spell));

        Game game = QualityHaver.getGameFromQualityHaver(target);

        if(target instanceof Cell){
            dispel(target,Dispelablity.BAD);
        }else if(source instanceof Spell){
            if(game.getWarriorsPlayer((Warrior)target).equals(game.getActivePlayer())){
                dispel(target,Dispelablity.BAD);
            }else {
                dispel(target,Dispelablity.GOOD);
            }
        }else{
            if(game.getWarriorsPlayer((Warrior)target).getWarriors().stream().anyMatch(warrior -> warrior.getTriggers()
                    .stream().anyMatch(trigger -> trigger.equals(source)))){
                dispel(target,Dispelablity.BAD);
            }else {
                dispel(target,Dispelablity.GOOD);
            }
        }

        return true;//todo. you MAY want to fix this so when you cast a useless dispel it doesn't get applied.
    }

    private static void dispel(QualityHaver qualityHaver, Dispelablity dispelType){
        qualityHaver.removeEffect((ArrayList<Effect>) qualityHaver.getEffects().stream().filter(effect -> effect.getDispelablity().equals(dispelType)).collect(Collectors.toCollection(ArrayList::new)));

        UUID id = UUID.randomUUID();
        qualityHaver.setLock(id+"dispeller",true);
        for (Trigger trigger : qualityHaver.getTriggers()) {
            if(trigger.getDispelablity().equals(dispelType)){
                DispelState state = new DispelState(trigger);
                QualityHaver.getGameFromQualityHaver(qualityHaver).iterateAllTriggersCheck(state);
                qualityHaver.removeTrigger(trigger);
            }
        }
        qualityHaver.setLock(id+"dispeller",false);
    }
}