package controller.window;

import model.Account;
import model.Deck;
import model.Game;
import model.gamemoods.CarryingFlag;
import model.gamemoods.CollectingFlag;
import model.gamemoods.KillingEnemyHero;
import model.player.AIPlayer;
import model.player.HumanPlayer;
import view.Message;
import view.Request;

public class GameWindow extends Window {
    private Game game;
    private MoodData moodData;//todo

    @Override
    public void main() {
        initialiseGame();
        while (true) {
            Message.GameWindow.insideGame.showMainView(game);
            if (game.getActivePlayer() instanceof HumanPlayer) {
                getPlayerAction();
            } else {
                ((AIPlayer) game.getActivePlayer()).doSomething();
            }
        }
    }

    private void getPlayerAction() {
        String input = Request.getNextRequest();
        if (input.matches("Help")) {
            help();
        }

    }

    private void help() {
        while (true) {
            String input = Request.getNextRequest();
            if (input.matches("exit")) {
                break;
            }
        }
    }

    private boolean initialiseGame() {
        MoodData moodData = new MoodData();//todo
        if (!checkDeck()) {
            this.closeWindow();
            return false;
        }
        if (!chooseSingleOrMulti()) {
            this.closeWindow();
            return false;
        }
        if (moodData.singlePlayer) { //single player
            if (!initializeSinglePlayer()) {
                this.closeWindow();
                return false;
            }
        } else { //multi player
            if (!initializeMultiPlayer()) {
                this.closeWindow();
                return false;
            }
        }
        return true;
    }

    private boolean initializeSinglePlayer() {
        if (!chooseStoryOrCustom()) {
            this.closeWindow();
            return false;
        }
        if (moodData.story) { // story mood
            if (!chooseLevel()) {
                this.closeWindow();
                return false;
            }
        } else { // custom mood
            if (!chooseMoodAndEnemyDeck()) {
                this.closeWindow();
                return false;
            }
        }
        return true;
    }

    private boolean initializeMultiPlayer() {
        if (!chooseOtherAccount()) {
            this.closeWindow();
            return false;
        }
        if (!chooseMood()) {
            this.closeWindow();
            return false;
        }
        return true;
    }

    private boolean checkDeck() {
        if (Account.getActiveAccount().getCollection().getMainDeck() == null) {
            Message.GameWindow.beforeGame.invalidDeckForPlayerOne();
            return false;
        }
        return true;
    }

    private boolean chooseSingleOrMulti() {
        while (true) {
            Message.GameWindow.beforeGame.singleOrMultiMenu();
            String request = Request.getNextRequest();
            switch (request) {
                case "1":
                    moodData.singlePlayer = true;
                    return true;
                case "2":
                    moodData.singlePlayer = false;
                    return true;
                case "exit":
                    return false;
            }
        }
    }

    private boolean chooseStoryOrCustom() {
        while (true) {
            Message.GameWindow.beforeGame.StoryOrCustomMenu();
            String request = Request.getNextRequest();
            switch (request) {
                case "1":
                    moodData.story = true;
                    return true;
                case "2":
                    moodData.story = false;
                    return true;
                case "exit":
                    return false;
            }
        }
    }

    private boolean chooseLevel() {
        return false;//todo
    }

    private boolean chooseMoodAndEnemyDeck() {
        while (true) {
            Message.GameWindow.beforeGame.moodAndDeckMenu();
            String request = Request.getNextRequest();
            if (request.matches("Start game \\w+ CollectingFlag \\d+")) {
                String deckName = request.replaceFirst("Start game ", "")
                        .replaceFirst(" CollectingFlag \\d+", "");
                int numberOfFlags = Integer.parseInt
                        (request.replaceFirst("Start game \\w+ CollectingFlag ", ""));
                if (Deck.getAllDecks().containsKey(deckName)) {
                    game = new Game(new CollectingFlag(numberOfFlags),
                            Account.getActiveAccount(), Deck.getAllDecks().get(deckName));
                    return true;
                }
            } else if (request.matches("Start game \\w+ (KillingEnemyHero|CarryingFlag)")) {
                String mood = request.replaceFirst("Start game \\w+ ", "");
                String deckName = request.replaceFirst("Start game ", "")
                        .replaceFirst(" (KillingEnemyHero|CarryingFlag)", "");
                if (Deck.getAllDecks().containsKey(deckName)) {
                    if (mood.equals("KillingEnemyHero")) {
                        game = new Game(new KillingEnemyHero(), Account.getActiveAccount(), moodData.aIDeck);
                    } else {
                        game = new Game(new CarryingFlag(), Account.getActiveAccount(), moodData.aIDeck);
                    }
                }
            } else if (request.equals("exit")) {
                return false;
            }
        }
    }

    private boolean chooseOtherAccount() {
        while (true) {
            Message.GameWindow.beforeGame.accountMenu(Account.getusernameToAccountObject());
            String request = Request.getNextRequest();
            if (request.matches("Select user \\w+")) {
                String userName = request.replaceFirst("Select user ", "");
                if (Account.getusernameToAccountObject().keySet().contains(userName)) {
                    Account account = Account.getusernameToAccountObject().get(userName);
                    if (account.getCollection().getMainDeck() != null) {
                        moodData.secondAccount = Account.getusernameToAccountObject().get(userName);
                        return true;
                    } else {
                        Message.GameWindow.beforeGame.invalidDeckForPlayerTwo();
                    }
                }
            } else if (request.matches("exit")) {
                return false;
            }
        }
    }

    private boolean chooseMood() {
        while (true) {
            Message.GameWindow.beforeGame.moodMenu();
            String request = Request.getNextRequest();
            if (request.matches("Start multiplayer game CollectingFlag \\d+")) {
                int numberOfFlags = Integer.parseInt
                        (request.replaceFirst("Start multiplayer game CollectingFlag ", ""));
                game = new Game(new CollectingFlag(numberOfFlags), Account.getActiveAccount(), moodData.secondAccount);

            } else if (request.matches("Start multiplayer game (KillingEnemyHero|CarryingFlag)")) {
                String gameMood = request.replaceFirst("Start multiplayer game ", "");
                if (gameMood.equals("KillingEnemyHero")) {
                    game = new Game(new KillingEnemyHero(), Account.getActiveAccount(), moodData.secondAccount);
                } else {
                    game = new Game(new CarryingFlag(), Account.getActiveAccount(), moodData.secondAccount);
                }
            } else if (request.equals("exit")) {
                return false;
            }
        }
    }
}

class MoodData {
    boolean singlePlayer;
    boolean story;
    Deck aIDeck;
    Account secondAccount;
}