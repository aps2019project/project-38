package controller.window;

import model.Game;
import model.player.AIPlayer;
import model.player.HumanPlayer;
import view.Request;

public class GameWindow extends Window {
    private Game game;

    public GameWindow(Game game) {
        this.game = game;
    }

    public void main() {
        while (true) {
            if (game.getActivePlayer() instanceof HumanPlayer) {
                getPlayerAction();
            }
            else {
                ((AIPlayer)game.getActivePlayer()).doSomething();
            }
        }
    }

    private void getPlayerAction() {
        String input = Request.getNextRequest();
        switch (input) {
        }
    }
}
