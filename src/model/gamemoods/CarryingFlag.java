package model.gamemoods;

import model.Constant;
import model.Game;
import model.cards.warriors.Warrior;
import model.triggers.Flag;

public class CarryingFlag extends GameMood {
    private int[] playersScore = {0, 0};
    private int previousTurn = 0;

    @Override
    public boolean checkGameEnd(Game game) {
        updateScores(game);
        for (int i = 0; i < 2; i++) {
            if (playersScore[i] > Constant.GameConstants.carryingFlagMoodWinScore) {
                winner = game.getPlayers()[i];
                return true;
            }
        }
        return false;
    }

    private void updateScores(Game game) {
        if (game.turn > previousTurn) {
            for (int i = 0; i < 2; i++) {
                for (Warrior warrior : game.getPlayers()[i].getWarriors()) {
                    playersScore[i] += warrior.getTriggers().stream()
                            .filter(trigger -> trigger instanceof Flag).count();
                }
            }
            previousTurn++;
        }
    }

    @Override
    public void applyTriggerToBoard(Game game) {

    }
}
