package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.Attacking;
import model.conditions.BeingAttacked;
import model.effects.Dispelablity;
import model.gamestate.Attack;
import model.gamestate.GameState;
/*
this trigger causes a special warrior to do more damage against a special warrior.
 */
public class AttackAdvantage extends Trigger {
    private int additionalDamage;
    private Warrior warriorAgainst;

    {
        conditions.put(new Attacking(),true);
        //this condition check if this minion is attacking the warrior against.
        // isPending is not checked because it is checked in attacking.
        conditions.put(((gameState, trigger) -> {
            if(!(gameState instanceof Attack))
                return false;
            return ((Attack) gameState).getAttecked() == warriorAgainst;
        }),true);
    }

    public AttackAdvantage(Warrior warrior, int duration, Dispelablity dispelablity, int additionalDamage, Warrior warriorAgainst) {
        super(warrior, duration,dispelablity);
        this.additionalDamage=additionalDamage;
        this.warriorAgainst =warriorAgainst;
    }

    @Override
    void apply(GameState gameState) {
        Attack attack=(Attack) gameState;

        attack.setAp(attack.getAp()+additionalDamage);
    }
}
