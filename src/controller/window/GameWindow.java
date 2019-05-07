package controller.window;

import model.*;
import model.cards.Card;
import model.cards.Description;
import model.cards.Spell;
import model.gamemoods.CarryingFlag;
import model.gamemoods.CollectingFlag;
import model.gamemoods.KillingEnemyHero;
import model.player.AIPlayer;
import model.player.HumanPlayer;
import view.Message;
import view.Request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameWindow extends Window {
    private Game game;
    private MoodData moodData = new MoodData();//todo

    @Override
    public void main() {
        if (!initialiseGame()) {
            System.out.println("you have no main deck");
            return;
        }
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
        String request = Request.getNextRequest();
        if (request.matches("Help")) {
            help();
        } else if (request.matches("Select \\d \\d")) {
            selectWarrior(request);
        } else if (request.matches("Select \\d")) {
            int index = Integer.parseInt(request.replace("Select ", ""));
            if (index < Constant.GameConstants.handSize) {
                if (game.getActivePlayer().getHand().get(index) != null) {
                    game.getSelecteds().selectCard(game, index);
                } else {
                    System.out.println("you selected null cart");
                }
            } else {
                System.out.println("wrong index");
            }
        } else if (request.equals("Select SPP")) {
            game.getSelecteds().selectSpecialPower(game);
        } else if (request.equals("Deselect warriors")) {
            game.getSelecteds().deselectAll();
        } else if (request.matches("Attack \\d \\d")) {
            Pattern pattern = Pattern.compile("(\\d)");
            Matcher matcher = pattern.matcher(request);
            matcher.find();
            int row = Integer.parseInt(matcher.group(1));
            matcher.find();
            int column = Integer.parseInt(matcher.group());
            if (game.getSelecteds().getWarriorsCell().size() == 1) {
                if (row < Constant.GameConstants.boardRow && column < Constant.GameConstants.boardColumn) {
                    Cell cell = game.getBoard().getCell(row, column);
                    if (cell.getWarrior() != null && game.getActivePlayer() == game.getWarriorsPlayer(cell.getWarrior())) {
                        game.attack(game.getSelecteds().getWarriorsCell().get(0), cell);
                    } else {
                        Message.GameWindow.insideGame.failCommand.thereIsNoEnemyWarriorInThisCell();
                    }
                } else {
                    Message.GameWindow.insideGame.failCommand.indexOutOfBoard();
                }
            } else {
                System.out.println("you should select just one warrior for attack");
            }
        } else if (request.matches("Move \\d \\d")) {
            Pattern pattern = Pattern.compile("(\\d)");
            Matcher matcher = pattern.matcher(request);
            matcher.find();
            int row = Integer.parseInt(matcher.group(1));
            matcher.find();
            int column = Integer.parseInt(matcher.group());
            if (game.getSelecteds().getWarriorsCell().size() == 1) {
                if (row < Constant.GameConstants.boardRow && column < Constant.GameConstants.boardColumn) {
                    Cell cell = game.getBoard().getCell(row, column);
                    if (cell.getWarrior() == null) {
                        game.move(game.getSelecteds().getWarriorsCell().get(0), cell);
                    } else {
                        System.out.println("target cell is filled");
                    }
                } else {
                    Message.GameWindow.insideGame.failCommand.indexOutOfBoard();
                }
            } else {
                System.out.println("you should select just one warrior for attack");
            }
        } else if (request.matches("Attack combo \\d \\d")) {
            Pattern pattern = Pattern.compile("(\\d)");
            Matcher matcher = pattern.matcher(request);
            matcher.find();
            int row = Integer.parseInt(matcher.group(1));
            matcher.find();
            int column = Integer.parseInt(matcher.group());
            if (game.getSelecteds().getWarriorsCell().size() > 1) {
                if (row < Constant.GameConstants.boardRow && column < Constant.GameConstants.boardColumn) {
                    Cell cell = game.getBoard().getCell(row, column);
                    if (cell.getWarrior() != null && game.getActivePlayer() == game.getWarriorsPlayer(cell.getWarrior())) {
                        game.comboAttack(game.getSelecteds().getWarriorsCell(), cell);
                    } else {
                        Message.GameWindow.insideGame.failCommand.thereIsNoEnemyWarriorInThisCell();
                    }
                } else {
                    Message.GameWindow.insideGame.failCommand.indexOutOfBoard();
                }
            } else {
                System.out.println("you should select more than one warrior for attack");
            }
        } else if (request.matches("Insert in \\d \\d")) {
            Pattern pattern = Pattern.compile("(\\d)");
            Matcher matcher = pattern.matcher(request);
            matcher.find();
            int row = Integer.parseInt(matcher.group(1));
            matcher.find();
            int column = Integer.parseInt(matcher.group());
            if (game.getSelecteds().cardHandIndex != null) {
                if (row < Constant.GameConstants.boardRow && column < Constant.GameConstants.boardColumn) {
                    Cell cell = game.getBoard().getCell(row, column);
                    game.useCard(game.getSelecteds().cardHandIndex, cell);
                } else {
                    Message.GameWindow.insideGame.failCommand.indexOutOfBoard();
                }
            } else {
                System.out.println("no card selected");
            }
        } else if (request.matches("Use special power \\d \\d")) {
            game.getSelecteds().deselectAll();
            Pattern pattern = Pattern.compile("(\\d)");
            Matcher matcher = pattern.matcher(request);
            matcher.find();
            int row = Integer.parseInt(matcher.group(1));
            matcher.find();
            int column = Integer.parseInt(matcher.group());
            if (row < Constant.GameConstants.boardRow && column < Constant.GameConstants.boardColumn) {
                Cell cell = game.getBoard().getCell(row, column);
                //todo use special power
            } else {
                Message.GameWindow.insideGame.failCommand.indexOutOfBoard();
            }
        } else if (request.matches("Show card info \\d{2,3}")) {
            Pattern pattern = Pattern.compile("(\\d{2,3})");
            Matcher matcher = pattern.matcher(request);
            matcher.find();
            int cardID = Integer.parseInt(matcher.group(1));
            if (Card.getAllCards().containsKey(cardID)) {
                Description description = Card.getAllCards().get(cardID).description;
                System.out.println("Description Of Card Ability: " + description.descriptionOfCardSpecialAbility);
                System.out.println("Target Type: " + description.targetType);

            } else {
                System.out.println("no card matched");
            }
        } else if (request.equals("End turn")) {
            game.endTurn();
        } else if (request.equals("Show collectables")) {
            collatableItemsMenu();
        } else if (request.equals("Show collectable item info")) {
            if (game.getSelecteds().collectableItem != null) {
                Spell item = game.getSelecteds().collectableItem;
                System.out.println("Description Of Card Ability: " + item.description.descriptionOfCardSpecialAbility);
                System.out.println("Target Type: " + item.description.targetType);
            } else {
                System.out.println("no selected collectable item");
            }
        } else if (request.matches("Use collectable item \\d \\d")) {
            Pattern pattern = Pattern.compile("(\\d)");
            Matcher matcher = pattern.matcher(request);
            matcher.find();
            int row = Integer.parseInt(matcher.group(1));
            matcher.find();
            int column = Integer.parseInt(matcher.group());
            if (game.getSelecteds().collectableItem != null) {
                if (row < Constant.GameConstants.boardRow && column < Constant.GameConstants.boardColumn) {
                    Cell cell = game.getBoard().getCell(row, column);
                    game.useCollectable(game.getSelecteds().collectableItem, cell);
                } else {
                    Message.GameWindow.insideGame.failCommand.indexOutOfBoard();
                }
            } else {
                System.out.println("no collectable item selected");
            }
        } else if (request.equals("Show next card")) {
            //todo in game
        } else if (request.equals("Enter graveyard")) {
            graveyardMenu();
        }
    }

    private void graveyardMenu() {
        while (true) {
            Message.GameWindow.insideGame.graveyardWindow(game);
            String request = Request.getNextRequest();
            if (request.equals("Exit")) {
                return;
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void collatableItemsMenu() {
        game.getSelecteds().deselectAll();
        while (true) {
            Message.GameWindow.insideGame.colletableWindow(game);
            String request = Request.getNextRequest();
            if (request.equals("exit")) {
                return;
            } else if (request.matches("Select \\d+")) {
                int index = Integer.parseInt(request.replace("Select ", ""));
                if (index < game.getCollectibleItems().size()) {
                    game.getSelecteds().selectColletableItem(game.getCollectibleItems().get(index));
                } else {
                    System.out.println("index is too big");
                }
            } else {
                System.out.println("invalid command");
            }
        }
    }


    private void help() {
        while (true) {
            Message.GameWindow.insideGame.help();
            String input = Request.getNextRequest();
            if (input.matches("exit")) {
                break;
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void selectWarrior(String request) {
        Pattern pattern = Pattern.compile("(\\d)");
        Matcher matcher = pattern.matcher(request);
        matcher.find();
        int row = Integer.parseInt(matcher.group(1));
        matcher.find();
        int column = Integer.parseInt(matcher.group());
        if (row < Constant.GameConstants.boardRow && column < Constant.GameConstants.boardColumn) {
            Cell cell = game.getBoard().getCell(row, column);
            if (cell.getWarrior() != null && game.getActivePlayer() == game.getWarriorsPlayer(cell.getWarrior())) {
                game.getSelecteds().seletWarrior(game.getBoard().getCell(row, column));
            } else {
                Message.GameWindow.insideGame.failCommand.youHaveNoOwnWarriorInThisCell();
            }
        } else {
            Message.GameWindow.insideGame.failCommand.indexOutOfBoard();
        }
    }

    private boolean initialiseGame() {
//        MoodData moodData = new MoodData();//todo
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
                if (Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
                    game = new Game(new CollectingFlag(numberOfFlags),
                            Account.getActiveAccount(), Account.getActiveAccount().getCollection().getAllDecks().get(deckName));
                    return true;
                }
            } else if (request.matches("Start game \\w+ (KillingEnemyHero|CarryingFlag)")) {
                Pattern pattern = Pattern.compile("(\\w+) (KillingEnemyHero|CarryingFlag)");
                Matcher matcher = pattern.matcher(request);
                matcher.find();
                String mood = matcher.group(2);
                String deckName = matcher.group(1);
                if (Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
                    if (mood.equals("KillingEnemyHero")) {
                        game = new Game(new KillingEnemyHero(), Account.getActiveAccount(), Account.getActiveAccount().getCollection().getAllDecks().get(deckName));
                        return true;
                    } else {
                        game = new Game(new CarryingFlag(), Account.getActiveAccount(), Account.getActiveAccount().getCollection().getAllDecks().get(deckName));
                        return true;
                    }
                }
            } else if (request.equals("exit")) {
                return false;
            }
        }
    }

    private boolean chooseOtherAccount() {
        while (true) {
            Message.GameWindow.beforeGame.accountMenu(Account.getUsernameToAccountObject());
            String request = Request.getNextRequest();
            if (request.matches("Select user \\w+")) {
                String userName = request.replaceFirst("Select user ", "");
                if (Account.getUsernameToAccountObject().keySet().contains(userName)) {
                    Account account = Account.getUsernameToAccountObject().get(userName);
                    if (account.getCollection().getMainDeck() != null) {
                        moodData.secondAccount = Account.getUsernameToAccountObject().get(userName);
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
