package model.triggers;

import model.Cell;
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
    protected void apply(GameState gameState) {
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
            warrior.triggers.addAll(triggers);
        });
    }

    private void removeEffectsAndTriggers(ArrayList<Warrior> warriors){
        warriors.stream().filter(warrior -> warrior.getCell().getGame().getWarriorsPlayer(warrior).getWarriors().
                contains(warrior)).forEach(warrior -> {
            warrior.effects.removeAll(effects);
            warrior.triggers.removeAll(triggers);
        });
    }
}