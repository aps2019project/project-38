package model.triggers;

import model.QualityHaver;
import model.conditions.IsBeingAttacked;
import model.conditions.HasAntiHolyBuff;
import model.effects.Dispelablity;
import model.gamestate.Attack;
import model.gamestate.GameState;

public class HolyBuff extends Trigger {
    private int reducedDamage;

    {
        conditions.add(new IsBeingAttacked());
        conditions.add(new HasAntiHolyBuff().not());
    }

    public HolyBuff(int duration, Dispelablity dispelablity,int reducedDamage) {
        super(duration, dispelablity);
        this.reducedDamage=reducedDamage;
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        Attack attack = (Attack) gameState;

        if(attack.getAp()-reducedDamage>=0) {
            attack.setAp(attack.getAp() - reducedDamage);
        }
    }
}
