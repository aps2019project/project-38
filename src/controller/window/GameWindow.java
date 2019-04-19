package controller.window;

import model.Game;
import model.player.HumanPlayer;

public class GameWindow extends Window {
    private Game game;

    public GameWindow (Game game) {
        this.game = game;
    }

    public void main() {
        while (true) {
            if (game.getActivePlayer() instanceof HumanPlayer) {

            }
            else {

            }
        }
    }
}
