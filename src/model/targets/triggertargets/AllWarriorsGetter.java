package model.targets.triggertargets;

import model.QualityHaver;
import model.cards.heros.Hero;
import model.cards.warriors.Warrior;
import model.gamestate.GameState;

import java.util.ArrayList;

public class AllWarriorsGetter implements TriggerTarget {
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
            targets.removeIf(warrior1 -> !heroToo && warrior1 instanceof Hero);
            return targets;
        }else {
            targets.addAll(warrior.getCell().getBoard().getGame().getWarriorsEnemyPlayer(warrior).getWarriors());
            targets.removeIf(warrior1 -> !heroToo && warrior1 instanceof Hero);
            return targets;
        }
    }
}
