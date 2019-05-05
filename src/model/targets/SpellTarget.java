package model.targets;

import model.Cell;
import model.QualityHaver;
import model.player.Player;

import java.util.ArrayList;

public interface SpellTarget {
    ArrayList<? extends QualityHaver> getTarget(Player spellOwner, Cell cell);
}
