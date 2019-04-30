package model.triggers;

import model.QualityHaver;
import model.conditions.IsBeingAttacked;
import model.conditions.HasAttackerAntiHolyBuff;
import model.effects.Dispelablity;
import model.gamestate.AttackState;
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
        AttackState attackState = (AttackState) gameState;

        if(attackState.ap-reducedDamage>=0) {
            attackState.ap-=reducedDamage;
        }
    }
}
