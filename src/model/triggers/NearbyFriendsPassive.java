package model.triggers;

import model.Cell;
import model.cards.warriors.Warrior;
import model.conditions.Died;
import model.conditions.Moved;
import model.conditions.Spawned;
import model.conditions.TurnStarted;
import model.effects.Dispelablity;
import model.gamestate.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

//add triggers and effects with "1" duration to this trigger.
public class NearbyFriendsPassive extends Trigger {
    {
        conditions.add(new Spawned().or(new Died()).or(new Moved()).or(new TurnStarted()));
    }
    public NearbyFriendsPassive(Warrior warrior, int duration, Dispelablity dispelablity) {
        super(warrior, duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {
        if(gameState instanceof Death){
            removeEffectsAndTriggers(getWarrior().getCell().getGame().getEightAdjacents(getWarrior().getCell()).
                    stream().map(Cell::getWarrior).collect(Collectors.toCollection(ArrayList::new)));
        }

        if(gameState instanceof Move){
            Move move = (Move) gameState;

            addEffectsAndTriggers(getWarrior().getCell().getGame().getEightAdjacents(move.getDestinationCell()).
                    stream().map(Cell::getWarrior).collect(Collectors.toCollection(ArrayList::new)));

            removeEffectsAndTriggers(getWarrior().getCell().getGame().getEightAdjacents(move.getOriginCell()).
                    stream().map(Cell::getWarrior).collect(Collectors.toCollection(ArrayList::new)));
        }

        if(gameState instanceof PutMinion || gameState instanceof TurnStart){
            addEffectsAndTriggers(getWarrior().getCell().getGame().getEightAdjacents(getWarrior().getCell()).
                    stream().map(Cell::getWarrior).collect(Collectors.toCollection(ArrayList::new)));
        }
    }

    private void addEffectsAndTriggers(ArrayList<Warrior> warriors){
        warriors.stream().filter(warrior -> warrior.getCell().getGame().getWarriorsPlayer(warrior).getWarriors().
                contains(warrior)).forEach(warrior -> {
            warrior.effects.addAll(effects);
            addTriggers(warrior,triggers);
        });
    }

    //checkIt because im not sure when i change the owner of the trigger the object is still the same and gets removed with the second line.
    private void removeEffectsAndTriggers(ArrayList<Warrior> warriors){
        warriors.stream().filter(warrior -> warrior.getCell().getGame().getWarriorsPlayer(warrior).getWarriors().
                contains(warrior)).forEach(warrior -> {
            warrior.effects.removeAll(effects);
            warrior.triggers.removeAll(triggers);
        });
    }
}