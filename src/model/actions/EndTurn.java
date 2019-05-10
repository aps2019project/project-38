package model.actions;

import model.Constant;
import model.Game;
import model.gamestate.TurnEndState;

public class EndTurn {
    public static void doIt(Game game) {
        game.decreaseSpecialPowerCoolDown();
        game.getSelectedThings().deselectAll();
        TurnEndState turnEnd = new TurnEndState();
        game.iterateAllTriggersCheck(turnEnd);
        game.iterateAndExpireAllTriggers();
        game.iterateAndExpireAllEffects();
        game.turn++;
        game.addNextCardToPlayerHand(game.getActivePlayer());
        game.getActivePlayer().mana = Constant.GameConstants.getTurnMana(game.turn);
        game.getActivePlayer().ableToReplaceCard = true;
//        game.timer.restart();
    }
}
