package model.gamemodes;


import model.cards.Hero;
import model.player.Player;

public class KillingEnemyHero extends GameMode {
    @Override
    public void checkGameEnd(Game game) {
        for (Player player : game.getPlayers()) {
            if (player.getWarriors().stream().noneMatch(warrior -> warrior instanceof Hero) /*||
                    player.getPlayerHero().getHp() <= 0*/ ) {
                winner = player != game.getPlayers()[0] ? game.getPlayers()[0] : game.getPlayers()[1];
            }
        }
    }

    @Override
    public void applyTriggerToBoard(Game game) {}

    @Override
    public GameMode deepCopy() {
        return new KillingEnemyHero();
    }


}
