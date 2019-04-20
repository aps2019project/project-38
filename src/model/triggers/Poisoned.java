package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.TurnEnded;
import model.effects.Dispelablity;
import model.effects.HP;
import model.gamestate.GameState;
import model.gamestate.Move;

import javax.management.loading.MLet;

public class Poisoned extends Trigger{
    {
        conditions.put(new TurnEnded(),true);
        effects.add(new HP(-1, Dispelablity.UNDISPELLABLE,-1));
    }

    public Poisoned(Warrior warrior, int duration, Dispelablity dispelablity) {
        super(warrior, duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {
        Move move=(Move)gameState;
        move.getWarrior().effects.addAll(effects);
    }
}
