package model.gamestate;

import model.QualityHaver;
import model.cards.spells.Spell;
import model.cards.warriors.Warrior;

public class UseSpell extends GameState {
    public QualityHaver target;
    public Spell spell;
    public boolean canceled = false;

    public UseSpell(QualityHaver target, Spell spell) {
        this.target=target;
        this.spell = spell;
    }
}
