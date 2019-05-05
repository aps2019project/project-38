package model.targets;

import model.Cell;
import model.QualityHaver;
import model.cards.Hero;
import model.cards.Warrior;
import model.gamestate.GameState;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NearbyGetter implements TriggerTarget {
    private boolean friendMod;
    private boolean heroToo;

    public NearbyGetter(boolean friendMod, boolean heroToo) {
        this.friendMod = friendMod;
        this.heroToo = heroToo;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert triggerOwner instanceof Warrior;

        Warrior warrior = (Warrior) triggerOwner;
        return (warrior.getCell().getBoard().getGame().getBoard().getEightAdjacent(warrior.getCell()).
                stream().map(Cell::getWarrior).filter(warrior1 -> warrior.getCell().getBoard().getGame()
                .getWarriorsPlayer(warrior).getWarriors().contains(warrior1) == friendMod)
                .filter(warrior1 -> !(warrior instanceof Hero) || heroToo).collect(Collectors.toCollection(ArrayList::new)));
    }
}
