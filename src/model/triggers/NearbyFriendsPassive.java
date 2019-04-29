package model.triggers;

import model.Cell;
import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.conditions.HasDied;
import model.conditions.HasMoved;
import model.conditions.HasSpawned;
import model.conditions.HasTurnStarted;
import model.effects.Dispelablity;
import model.gamestate.*;

import java.util.ArrayList;
import java.util.stream.Collectors;
/*special because of complexity*/
//add triggers and effects with "1" duration to this trigger.
public class NearbyFriendsPassive extends Trigger {
    {
        conditions.add(new HasSpawned().or(new HasDied()).or(new HasMoved()).or(new HasTurnStarted()));
    }

    public NearbyFriendsPassive(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        Warrior warrior = (Warrior) owner;

        if(gameState instanceof Death){
            removeEffectsAndTriggers(warrior.getCell().getGame().getBoard().getEightAdjacent(warrior.getCell()).
                    stream().map(Cell::getWarrior).collect(Collectors.toCollection(ArrayList::new)));
        }

        if(gameState instanceof Move){
            Move move = (Move) gameState;

            addEffectsAndTriggers(warrior.getCell().getGame().getBoard().getEightAdjacent(move.getTargetCell()).
                    stream().map(Cell::getWarrior).collect(Collectors.toCollection(ArrayList::new)));

            removeEffectsAndTriggers(warrior.getCell().getGame().getBoard().getEightAdjacent(move.getOriginCell()).
                    stream().map(Cell::getWarrior).collect(Collectors.toCollection(ArrayList::new)));
        }

        if(gameState instanceof PutMinion || gameState instanceof TurnStart){
            addEffectsAndTriggers(warrior.getCell().getGame().getBoard().getEightAdjacent(warrior.getCell()).
                    stream().map(Cell::getWarrior).collect(Collectors.toCollection(ArrayList::new)));
        }
    }

    private void addEffectsAndTriggers(ArrayList<Warrior> warriors){
        warriors.stream().filter(warrior -> warrior.getCell().getGame().getWarriorsPlayer(warrior).getWarriors().
                contains(warrior)).forEach(warrior -> {
            warrior.getEffects().addAll(effects);
            warrior.getTriggers().addAll(triggers);
        });
    }

    private void removeEffectsAndTriggers(ArrayList<Warrior> warriors){
        warriors.stream().filter(warrior -> warrior.getCell().getGame().getWarriorsPlayer(warrior).getWarriors().
                contains(warrior)).forEach(warrior -> {
            warrior.getEffects().removeAll(effects);
            warrior.getTriggers().removeAll(triggers);
        });
    }
}