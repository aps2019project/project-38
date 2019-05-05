package model.targets;

import model.Cell;
import model.QualityHaver;
import model.cards.Hero;
import model.cards.Warrior;
import model.gamestate.GameState;
import model.player.Player;

import java.util.ArrayList;

public class AllWarriorsGetter implements TriggerTarget, SpellTarget {
    private boolean friendMod;
    private boolean heroToo;

    public AllWarriorsGetter(boolean friendMod,boolean heroToo) {
        this.friendMod = friendMod;
        this.heroToo = heroToo;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert triggerOwner instanceof Warrior;

        Warrior warrior = (Warrior)triggerOwner;
        ArrayList<QualityHaver> targets = new ArrayList<>();
        if(friendMod){
            targets.addAll(warrior.getCell().getBoard().getGame().getWarriorsPlayer(warrior).getWarriors());
        }else {
            targets.addAll(warrior.getCell().getBoard().getGame().getWarriorsEnemyPlayer(warrior).getWarriors());
        }
        targets.removeIf(warrior1 -> !heroToo && warrior1 instanceof Hero);
        return targets;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(Player spellOwner, Cell cell) {
        ArrayList<QualityHaver> targets = new ArrayList<>();
        if(friendMod){
            targets.addAll(spellOwner.getWarriors());
        }else {
            //todo don't have the method yet
        }
        targets.removeIf(warrior1 -> !heroToo && warrior1 instanceof Hero);
        return targets;
    }
}
