package model.targets;

import model.Cell;
import model.QualityHaver;
import model.player.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ByEffTriggGetter implements SpellTarget {
    private Class aClass;
    private SpellTarget spellTarget;

    public ByEffTriggGetter(Class aClass, SpellTarget spellTarget) {
        this.aClass=aClass;
        this.spellTarget = spellTarget;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(Player spellOwner, Cell cell) {
        return spellTarget.getTarget(spellOwner,cell).stream().filter(qualityHaver -> qualityHaver
                .getEffects().stream().anyMatch(effect1 -> effect1.getClass().equals(aClass))||
                qualityHaver.getTriggers().stream().anyMatch(trigger1 -> trigger1.getClass().equals(aClass)))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
