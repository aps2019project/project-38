package model.gamestate;

import model.Cell;
import model.QualityHaver;
import model.cards.Spell;

public class UseSpellState extends GameState {
    public boolean pending = true;
    public Cell targetCell;
    public Spell spell;
    public boolean canceled = false;

    public UseSpellState(Cell target, Spell spell) {
        this.targetCell=target;
        this.spell = spell;
    }
}
