package model.gamemoods;

import model.Board;
import model.Game;
import model.cards.heros.Hero;
import model.player.Player;

public class KillingEnemyHero extends GameMood {
    @Override
    public boolean checkGameEnd(Game game) {
        for (Player player : game.getPlayers()) {
            if (player.getWarriors().stream().noneMatch(warrior -> warrior instanceof Hero) ||
                    player.getPlayerHero().getHp() <= 0 ) {
                winner = player != game.getPlayers()[0] ? game.getPlayers()[0] : game.getPlayers()[1];
                return true;
            }
        }
        return false;
    }

    @Override
    public void applyTriggerToBoard(Game game) {}
}
