package model.targets;

import model.Cell;
import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.gamestate.GameState;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class NearbyFriendsGetter implements TriggerTarget {

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert triggerOwner instanceof Warrior;

        Warrior warrior=(Warrior)triggerOwner;
        return (warrior.getCell().getGame().getBoard().getEightAdjacent(warrior.getCell()).
                stream().map(Cell::getWarrior).filter(warrior1 -> warrior.getCell().getGame().getWarriorsPlayer(warrior).
                getWarriors().contains(warrior1)).collect(Collectors.toCollection(ArrayList::new)));
    }
}
