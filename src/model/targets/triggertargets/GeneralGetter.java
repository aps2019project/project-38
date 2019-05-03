package model.targets.triggertargets;

import model.QualityHaver;
import model.cards.heros.Hero;
import model.cards.warriors.Warrior;
import model.gamestate.GameState;

import java.util.ArrayList;

public class GeneralGetter implements TriggerTarget{
    boolean friendMod;

    public GeneralGetter(boolean friendMod) {
        this.friendMod = friendMod;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert triggerOwner instanceof Warrior;

        Warrior warrior = (Warrior)triggerOwner;

        ArrayList<Hero> targets = new ArrayList<>();
        return null;//todo
    }
}
