package model.actions;

import model.gamestate.TurnStartState;

public class StartTurn {
    public static void doIt(Game game) {
        TurnStartState turnStartState = new TurnStartState();
        game.iterateAllTriggersCheck(turnStartState);
        game.getGameMode().applyTriggerToBoard(game);
    }
}
