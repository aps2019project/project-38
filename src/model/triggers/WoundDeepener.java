package model.triggers;

import model.Constant;
import model.Game;
import model.QualityHaver;
import model.conditions.HasAttacked;
import model.effects.Dispelablity;
import model.gamestate.AttackState;
import model.gamestate.GameState;
//special because the trigger in it needs gameState to be built.
public class WoundDeepener extends Trigger {
    {
        conditions.add(new HasAttacked());
    }

    public WoundDeepener(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        AttackState attackState = (AttackState) gameState;
        Game game = getGameFromQualityHaver(owner);

//        game.triggAddBuffer.put(new AttackAdvantage(-1,Dispelablity.GOOD,
//                Constant.EffectsTriggersConstants.WoundDeepener.additionalDamage, attackState.getAttacked()),owner);
        owner.addTrigger(new AttackAdvantage(-1,Dispelablity.GOOD,
                Constant.EffectsTriggersConstants.WoundDeepener.additionalDamage, attackState.getAttacked()));
    }
}