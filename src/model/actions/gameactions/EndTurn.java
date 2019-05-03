package model.actions.gameactions;

import model.Constant;
import model.Game;
import model.gamestate.TurnEndState;

public abstract class EndTurn {
    public static void doIt(Game game) {
        TurnEndState turnEnd = new TurnEndState();
        game.iterateAndExpireAllEffects();
        game.iterateAllTriggers(turnEnd);
        game.turn ++;
        game.addNewCardToPlayerHand(game.getActivePlayer());
        game.getActivePlayer().mana = Constant.GameConstants.getTurnMana(game.turn);
        game.getActivePlayer().ableToReplaceCard = true;
        game.timer.restart();
    }
}
