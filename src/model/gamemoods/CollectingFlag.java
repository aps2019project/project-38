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
        if (game.turn % 4 != 0 && gameFlags < gameMaxFlags && new Random().nextBoolean()) {
            int randomNumberOfTurnFlags = (new Random(System.currentTimeMillis())).nextInt(gameMaxFlags / 3);
            for (int i = 0; i < randomNumberOfTurnFlags; i++) {
                for (int j = 0; j < 100; j++) {
                    int row = new Random(System.currentTimeMillis()).nextInt(Constant.GameConstants.boardRow);
                    int column = new Random(System.currentTimeMillis()).nextInt(Constant.GameConstants.boardColumn);
                    Cell cell = game.getBoard().getCell(row, column);
                    if(!cell.getTriggers().stream().anyMatch(trigger -> trigger instanceof Flag) && cell.getWarrior() == null) {
                        game.getBoard().getCell(row, column).getTriggers().add(new Flag());
                        gameFlags++;
                    }
                }
            }
        }
    }
}
