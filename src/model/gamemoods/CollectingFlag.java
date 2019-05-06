package model.gamemoods;

import model.Constant;
import model.Game;
import model.player.Player;
import model.triggers.Flag;

import java.util.Random;

public class CollectingFlag extends GameMood {
    private int gameFlags;

    public CollectingFlag(int gameFlags) {
        this.gameFlags = gameFlags;
    }

    @Override
    public boolean checkGameEnd(Game game) {
        for (Player player : game.getPlayers()) {
            if (getNumberOFPlayerFlags(player) > Constant.GameConstants.collectingFlagMoodFlags / 2) {
                winner = player;
                return true;
            }
        }
        return false;
    }

    @Override
    public void applyTriggerToBoard(Game game) {
        if (game.turn % 4 != 0 && gameFlags < Constant.GameConstants.collectingFlagMoodFlags &&
                new Random().nextBoolean()) {
            int row = new Random(Constant.GameConstants.boardRow).nextInt();
            int column = new Random(Constant.GameConstants.boardColumn).nextInt();
            game.getBoard().getCell(row, column).getTriggers().add(new Flag());
            gameFlags++;
        }
    }
}
