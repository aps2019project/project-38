package model.triggers;

import model.QualityHaver;
import model.conditions.IsBadSpellComin;
import model.effects.Dispelablity;
import model.gamestate.GameState;
import model.gamestate.UseSpellState;

public class SpellImmune extends Trigger{
    {
        conditions.add(new IsBadSpellComin());
    }
    public SpellImmune(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        ((UseSpellState)gameState).canceled=true;
    }
}
