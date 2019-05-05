package model.actions;

import model.QualityHaver;
import model.player.Player;

public interface SpellAction {
    void execute(Player spellOwner, QualityHaver target);
}
