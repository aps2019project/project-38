package model.targets;

import model.Cell;
import model.QualityHaver;
import model.cards.Hero;
import model.cards.Warrior;
import model.gamestate.GameState;
import model.player.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AdjacentGetter implements TriggerTarget , SpellTarget {
    private boolean friendMod;
    private boolean heroToo;

    //use this for trigger target
    public AdjacentGetter(boolean friendMod, boolean heroToo) {
        this.friendMod = friendMod;
        this.heroToo = heroToo;
    }

    SpellTarget spellTarget;
    //use this for spellTarget. give a spell target that only returns a single warrior.
    public AdjacentGetter(SpellTarget spellTarget) {
        this.spellTarget = spellTarget;
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

    @Override
    public ArrayList<? extends QualityHaver> getTarget(Player spellOwner, Cell cell) {
        ArrayList<? extends QualityHaver> targets = spellTarget.getTarget(spellOwner,cell);
        if(targets.size()>0) {
            return getTarget(spellTarget.getTarget(spellOwner, cell).get(0), null);
        }else {
            return targets;
        }
    }
}
