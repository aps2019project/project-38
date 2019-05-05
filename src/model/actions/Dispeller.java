package model.actions;

import model.Cell;
import model.Game;
import model.QualityHaver;
import model.cards.Spell;
import model.cards.Warrior;
import model.effects.Dispelablity;
import model.gamestate.DispelState;
import model.player.Player;
import model.triggers.Trigger;

public class Dispeller implements AutoAction{

    @Override
    public void execute(QualityHaver source, QualityHaver target) {
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
            if(game.getWarriorsPlayer((Warrior)target).equals(game.getWarriorsPlayer((Warrior)source))){
                dispel(target,Dispelablity.BAD);
            }else {
                dispel(target,Dispelablity.GOOD);
            }
        }
    }

    public static void dispel(QualityHaver qualityHaver,Dispelablity dispelType){
        qualityHaver.getEffects().removeIf(effect -> effect.getDispelablity().equals(dispelType));

        for (Trigger trigger : qualityHaver.getTriggers()) {
            if(trigger.getDispelablity().equals(dispelType)) {
                DispelState state = new DispelState(trigger);
                QualityHaver.getGameFromQualityHaver(qualityHaver).iterateAllTriggersCheck(state);
                qualityHaver.getTriggers().remove(trigger);
            }
        }
    }
}
