package model.gamestate;

import model.QualityHaver;
import model.cards.spells.Spell;
import model.cards.warriors.Warrior;

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
