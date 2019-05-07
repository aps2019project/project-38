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
import model.player.Player;
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
            return;
        }
        while (game.getGameMood().getWinner() == null) {
            Message.GameWindow.insideGame.showMainView(game);
            if (game.getActivePlayer() instanceof HumanPlayer) {
                getPlayerAction();
            } else {
                ((AIPlayer) game.getActivePlayer()).doSomething();
            }
        }
        endGame(game);
    }

    private void endGame(Game game) {
        Player winner = game.getGameMood().getWinner();
        if (winner instanceof HumanPlayer) {
            ((HumanPlayer)winner).getAccount().derrick += game.prise;
        }
        while (true) {
            Message.GameWindow.AfterGame.showWinner(winner);
            String request = Request.getNextRequest();
            if (request.equals("exit")) {
                break;
            }
        }
        this.closeWindow();
        new MainMenu().openWindow();
    }

    private void getPlayerAction() {
        String request = Request.getNextRequest();
        if (request.matches("Help")) {
            help();
        } else if (request.matches("Select \\d \\d")) {
            selectWarrior(request);
        } else if (request.matches("Select \\d")) {
            selectCard(request);
        } else if (request.equals("Select SPP")) {
            game.getSelectedThings().selectSpecialPower(game);
        } else if (request.equals("Deselect warriors")) {
            game.getSelectedThings().deselectAll();
        } else if (request.matches("Attack \\d \\d")) {
            attack(request);
        } else if (request.matches("Move \\d \\d")) {
            move(request);
        } else if (request.matches("Attack combo \\d \\d")) {
            comboAttack(request);
        } else if (request.matches("Insert in \\d \\d")) {
            insertCardinGame(request);
        } else if (request.equals("Replace")) {
            replaceCard();
        } else if (request.matches("Use special power \\d \\d")) {
            useSpecialPower(request);
        } else if (request.matches("Show card info \\d{2,3}")) {
            showCardInfo(request);
        } else if (request.equals("End turn")) {
            game.endTurn();
        } else if (request.equals("Show collectibles")) {
            collectibleItemsMenu();
        } else if (request.equals("Show collectible item info")) {
            showCollectibleItemInfo();
        } else if (request.matches("Use collectible item \\d \\d")) {
            useCollectibleItem(request);
        } else if (request.equals("Show next card")) {
            showNextCard();
        } else if (request.equals("Enter graveyard")) {
            graveyardMenu();
        } else {
            Message.GameWindow.failMessage.invalidCommand();
        }
    }

    private void showNextCard() {

    }

    private void useCollectibleItem(String request) {
        Cell cell = getCellByMessage(request);
        if (cell == null) {
            return;
        }
        if (game.getSelectedThings().collectibleItem != null) {
            game.useCollectible(game.getSelectedThings().collectibleItem, cell);
        } else {
            Message.GameWindow.failMessage.noSelectedCollectibleItem();
        }
        game.getSelectedThings().deselectAll();
    }

    private void showCollectibleItemInfo() {
        if (game.getSelectedThings().collectibleItem != null) {
            Spell item = game.getSelectedThings().collectibleItem;
            System.out.println("Description Of Card Ability: " + item.description.descriptionOfCardSpecialAbility);
            System.out.println("Target Type: " + item.description.targetType);
        } else {
            System.out.println("no selected collectible item");
        }
    }

    private void showCardInfo(String request) {
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
    }

    private void useSpecialPower(String request) {
        Cell cell = getCellByMessage(request);
        if (cell == null) {
            return;
        }
        game.useSpecialPower(cell);
        game.getSelectedThings().deselectAll();
    }

    private void replaceCard() {
        if (game.getSelectedThings().cardHandIndex != null) {
            game.replaceCard(game.getSelectedThings().cardHandIndex);
        }else {
            System.out.println("no selected card");
        }
        game.getSelectedThings().deselectAll();
    }

    private void insertCardinGame(String request) {
        Cell cell = getCellByMessage(request);
        if (cell == null) {
            return;
        }
        if (game.getSelectedThings().cardHandIndex != null) {
            game.useCard(game.getSelectedThings().cardHandIndex, cell);
        } else {
            System.out.println("no card selected");
        }
        game.getSelectedThings().deselectAll();
    }

    private void comboAttack(String request) {
        Cell cell = getCellByMessage(request);
        if (cell == null) {
            return;
        }
        if (game.getSelectedThings().getWarriorsCell().size() > 1) {
            if (cell.getWarrior() != null && game.getActivePlayer() == game.getWarriorsPlayer(cell.getWarrior())) {
                game.comboAttack(game.getSelectedThings().getWarriorsCell(), cell);
            } else {
                Message.GameWindow.failMessage.thereIsNoEnemyWarriorInThisCell();
            }
        } else {
            System.out.println("you should select more than one warrior for attack");
        }
        game.getSelectedThings().deselectAll();
    }

    private void move(String request) {
        Cell cell = getCellByMessage(request);
        if (cell == null) {
            return;
        }
        if (game.getSelectedThings().getWarriorsCell().size() == 1) {
            if (cell.getWarrior() == null) {
                game.move(game.getSelectedThings().getWarriorsCell().get(0), cell);
            } else {
                System.out.println("target cell is filled");
            }
        } else {
            System.out.println("you should select just one warrior for attack");
        }
        game.getSelectedThings().deselectAll();
    }

    private void attack(String request) {
        Cell cell = getCellByMessage(request);
        if (cell == null) {
            return;
        }
        if (game.getSelectedThings().getWarriorsCell().size() == 1) {
            if (cell.getWarrior() != null && game.getActivePlayer() == game.getWarriorsPlayer(cell.getWarrior())) {
                game.attack(game.getSelectedThings().getWarriorsCell().get(0), cell);
            } else {
                Message.GameWindow.failMessage.thereIsNoEnemyWarriorInThisCell();
            }
        } else {
            System.out.println("you should select just one warrior for attack");
        }
        game.getSelectedThings().deselectAll();
    }

    private void selectCard(String request) {
        int index = Integer.parseInt(request.replace("Select ", ""));
        if (index < Constant.GameConstants.handSize) {
            if (game.getActivePlayer().getHand().get(index) != null) {
                game.getSelectedThings().selectCard(game, index);
            } else {
                System.out.println("you selected null cart");
            }
        } else {
            System.out.println("wrong index");
        }
    }

    private Cell getCellByMessage(String request) {
        Pattern pattern = Pattern.compile("(\\d)");
        Matcher matcher = pattern.matcher(request);
        matcher.find();
        int row = Integer.parseInt(matcher.group(1));
        matcher.find();
        int column = Integer.parseInt(matcher.group());
        if (row < Constant.GameConstants.boardRow && column < Constant.GameConstants.boardColumn) {
            return game.getBoard().getCell(row, column);
        }
        Message.GameWindow.failMessage.indexOutOfBoard();
        return null;
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

    private void collectibleItemsMenu() {
        game.getSelectedThings().deselectAll();
        while (true) {
            Message.GameWindow.insideGame.collectiblesWindow(game);
            String request = Request.getNextRequest();
            if (request.equals("exit")) {
                return;
            } else if (request.matches("Select \\d+")) {
                int index = Integer.parseInt(request.replace("Select ", ""));
                if (index < game.getCollectibleItems().size()) {
                    game.getSelectedThings().selectColletableItem(game.getCollectibleItems().get(index));
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
        Cell cell = getCellByMessage(request);
        if(cell == null) {
            return;
        }
        if (!game.getSelectedThings().getWarriorsCell().contains(cell) && cell.getWarrior() != null &&
                game.getActivePlayer() == game.getWarriorsPlayer(cell.getWarrior())) {
            game.getSelectedThings().seletWarrior(game.getBoard().getCell(cell.getRow(), cell.getColumn()));
        } else {
            Message.GameWindow.failMessage.youHaveNoOwnWarriorInThisCell();
        }
    }

    private boolean initialiseGame() {
//        MoodData moodData = new MoodData();//todo
        if (!checkDeck()) {
            System.out.println("you have no main deck");
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
        while (true) {
            Message.GameWindow.beforeGame.showLevelsForStoryMode();
            String request = Request.getNextRequest();
            if (Level.getAvailableLevels().containsKey(request)) {
                Level level = Level.getAvailableLevels().get(request);
                game = level.getLevelGame(Account.getActiveAccount());
                return true;
            } else if (request.equals("exit")) {
                return false;
            }
        }
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
                    return true;
                } else {
                    game = new Game(new CarryingFlag(), Account.getActiveAccount(), moodData.secondAccount);
                    return true;
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
