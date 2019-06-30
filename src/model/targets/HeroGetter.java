package model.targets;

import model.Cell;
import model.QualityHaver;
import model.cards.Hero;
import model.cards.Warrior;
import model.gamestate.GameState;
import model.player.Player;

import java.util.ArrayList;

public class HeroGetter implements TriggerTarget,SpellTarget{
    private boolean friendMod;

    public HeroGetter(boolean friendMod) {
        this.friendMod = friendMod;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert triggerOwner instanceof Warrior;

        Warrior warrior = (Warrior)triggerOwner;
        ArrayList<Hero> target = new ArrayList<>();
        if(friendMod){
            target.add(warrior.getCell().getBoard().getGame().getWarriorsPlayer(warrior).getPlayerHero());
        }else {
            target.add(warrior.getCell().getBoard().getGame().getWarriorsEnemyPlayer(warrior).getPlayerHero());
        }
        return target;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(Player spellOwner, Cell cell) {
        ArrayList<Hero> targets = new ArrayList<>();
        targets.add(spellOwner.getPlayerHero());
        return targets;
    }
}
