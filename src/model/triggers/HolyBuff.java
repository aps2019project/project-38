package model.triggers;

import model.Constant;
import model.cards.warriors.Warrior;
import model.conditions.BeenAttacked;
import model.conditions.BeingAttacked;
import model.conditions.HeHasAntiHolyBuff;
import model.effects.Dispelablity;
import model.gamestate.Attack;
import model.gamestate.GameState;

public class HolyBuff extends Trigger {
    private int reducedDamage;

    {
        conditions.add(new BeingAttacked());
        conditions.add(new HeHasAntiHolyBuff().not());
    }

    public HolyBuff(Warrior warrior, int duration, Dispelablity dispelablity,int reducedDamage) {
        super(warrior, duration, dispelablity);
        this.reducedDamage=reducedDamage;
    }

    @Override
    void apply(GameState gameState) {
        Attack attack = (Attack) gameState;
        attack.setAp(attack.getAp()- reducedDamage);
    }
}
