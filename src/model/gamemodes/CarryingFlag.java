package model.gamemodes;

import model.Constant;
import model.Game;
import model.cards.Warrior;
import model.triggers.Flag;

import java.util.Random;

public class CarryingFlag extends GameMode {
    private int[] playersScore = {0, 0};
    private int previousTurn = 0;
    private  boolean gameHasFlag = false;

    @Override
    public boolean checkGameEnd(Game game) {
        updateScores(game);
        for (int i = 0; i < 2; i++) {
            if (playersScore[i] > Constant.GameConstants.carryingFlagModeWinScore) {
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
        if (!gameHasFlag) {
            int randomRow = new Random(System.currentTimeMillis()).nextInt(Constant.GameConstants.boardRow);
            game.getBoard().getCell(randomRow, Constant.GameConstants.boardColumn / 2 + 1)
                    .getTriggers().add(new Flag());
            gameHasFlag = true;
        }
    }

    public int[] getPlayersScore() {
        return playersScore;
    }
}
