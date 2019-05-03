package model.targets.triggertargets;

import model.Cell;
import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.gamestate.GameState;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NearbyGetter implements TriggerTarget {
    boolean friendMod;

    public NearbyGetter(boolean friendMod) {
        this.friendMod = friendMod;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert triggerOwner instanceof Warrior;

        Warrior warrior=(Warrior)triggerOwner;
        return (warrior.getCell().getBoard().getGame().getBoard().getEightAdjacent(warrior.getCell()).
                stream().map(Cell::getWarrior).filter(warrior1 -> warrior.getCell().getBoard().getGame().getWarriorsPlayer(warrior).
                getWarriors().contains(warrior1)==friendMod).collect(Collectors.toCollection(ArrayList::new)));
    }
}
