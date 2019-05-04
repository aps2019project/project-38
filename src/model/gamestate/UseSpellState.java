package model.gamestate;

import model.QualityHaver;
import model.cards.Spell;

public class UseSpellState extends GameState {
    public boolean pending = true;
    public QualityHaver target;
    public Spell spell;
    public boolean canceled = false;

    public UseSpellState(QualityHaver target, Spell spell) {
        this.target=target;
        this.spell = spell;
    }
}
