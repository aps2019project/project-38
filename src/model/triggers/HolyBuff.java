package model.triggers;

import model.QualityHaver;
import model.conditions.IsBeingAttacked;
import model.conditions.HasAttackerAntiHolyBuff;
import model.effects.Dispelablity;
import model.gamestate.Attack;
import model.gamestate.GameState;

public class HolyBuff extends Trigger {
    private int reducedDamage;

    {
        conditions.add(new IsBeingAttacked());
        conditions.add(new HasAttackerAntiHolyBuff().not());
    }

    public HolyBuff(int duration, Dispelablity dispelablity,int reducedDamage) {
        super(duration, dispelablity);
        this.reducedDamage=reducedDamage;
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        Attack attack = (Attack) gameState;

        if(attack.ap-reducedDamage>=0) {
            attack.ap-=reducedDamage;
        }
    }
}
