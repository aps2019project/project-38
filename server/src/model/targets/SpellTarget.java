package model.targets;

import model.Cell;
import model.QualityHaver;
import model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public interface SpellTarget extends Serializable {
    ArrayList<? extends QualityHaver> getTarget(Player spellOwner, Cell cell);
}
