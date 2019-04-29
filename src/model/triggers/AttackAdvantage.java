package model.triggers;

import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.conditions.IsAttacking;
import model.effects.Dispelablity;
import model.gamestate.Attack;
import model.gamestate.GameState;
/*special because another special trigger needs it(WoundDeepener)*/
//this trigger causes a special warrior to do more damage against a special warrior.
public class AttackAdvantage extends Trigger {
    private int additionalDamage;
    private Warrior warriorAgainst;

    {
        conditions.add(new IsAttacking());
        //this condition check if this minion is attacking the warrior against.
        // isPending is not checked because it is checked in attacking.
        conditions.add(((gameState, trigger,triggerOwner) -> {
            if(!(gameState instanceof Attack))
                return false;
            return ((Attack) gameState).getAttacked() == warriorAgainst;
        }));
    }

    public AttackAdvantage(int duration, Dispelablity dispelablity, int additionalDamage, Warrior warriorAgainst) {
        super(duration,dispelablity);
        this.additionalDamage=additionalDamage;
        this.warriorAgainst =warriorAgainst;
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        Attack attack=(Attack) gameState;

        attack.setAp(attack.getAp()+additionalDamage);
    }
}
