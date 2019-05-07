package model.gamemoods;

import model.Cell;
import model.Constant;
import model.Game;
import model.player.Player;
import model.triggers.Flag;

import java.util.Random;

public class CollectingFlag extends GameMood {
    private int gameMaxFlags;
    private int gameFlags = 0;

    public CollectingFlag(int gameMaxFlags) {
        if (6 < gameMaxFlags && gameMaxFlags < 20) {
            this.gameMaxFlags = gameMaxFlags;
        }else {
            gameMaxFlags = Constant.GameConstants.collectingFlagMoodFlags;
        }
    }

    @Override
    public boolean checkGameEnd(Game game) {
        for (Player player : game.getPlayers()) {
            if (getNumberOFPlayerFlags(player) > gameFlags / 2) {
                winner = player;
                return true;
            }
        }
        return false;
    }

    @Override
    public void applyTriggerToBoard(Game game) {
        if (game.turn == 0) {
            Random random = new Random(System.currentTimeMillis());
            for (int i = 0; i < gameMaxFlags; i++) {
                while (true) {
                    int randomRow = random.nextInt(Constant.GameConstants.boardRow);
                    int randomColumn;
                    if (i % 2 == 0) {
                        randomColumn = random.nextInt(4) + 5;
                    }
                    else {
                        randomColumn = random.nextInt(5);
                    }
                    Cell cell = game.getBoard().getCell(randomRow, randomColumn);
                    if (cell.getWarrior() == null && cell.getTriggers().stream().noneMatch(trigger -> trigger instanceof Flag)) {
                        cell.getTriggers().add(new Flag());
                        break;
                    }
                }
            }
        }
    }
}
