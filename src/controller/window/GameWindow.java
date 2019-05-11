package controller.window;

import controller.window.normalwindow.ChoosingWindow;
import controller.window.normalwindow.OperatingWindow;
import model.*;
import model.cards.Card;
import model.cards.Spell;
import model.gamemodes.CarryingFlag;
import model.gamemodes.CollectingFlag;
import model.gamemodes.GameMode;
import model.gamemodes.KillingEnemyHero;
import model.player.AIPlayer;
import model.player.HumanPlayer;
import model.player.Player;
import view.Message;
import view.Request;
import view.windowgraphics.WindowGraphic;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameWindow extends Window {
    private Game game;

    private void initializeAndRunBeforeGameWindows() {
        ChoosingWindow battle = new ChoosingWindow
                (null, "Battle", "Single Or Multi");
        ChoosingWindow single = new ChoosingWindow
                (battle, "Single Player", "Story Or Custom");
        ChoosingWindow multi = new ChoosingWindow
                (battle, "Select Other Account", "Multi Player", "Select Other Account");
        battle.getSubWindows().add(single);
        battle.getSubWindows().add(multi);
        ChoosingWindow story = new ChoosingWindow
                (single, "Story", "#final#");
        ChoosingWindow custom = new ChoosingWindow
                (single, "Choosing Enemy Deck", "Custom", "Choosing Enemy Deck");
        single.getSubWindows().add(story);
        single.getSubWindows().add(custom);
        initializeStoryWindow(story);
        initializeCustomWindow(custom);
        initializeMultiWindow(multi);
        battle.openWindow();
    }

    private void initializeStoryWindow(ChoosingWindow story) {
        GameWindow thisGameWindow = this;
        for (Map.Entry<String, Level> entry : Level.getAvailableLevels().entrySet()) {
            story.getSubWindows().add(new OperatingWindow(story,
                    String.format("[Level Name: %s] [Hero Name: %s] [Mode: %s] [Prize: %d]",
                            entry.getKey(), entry.getValue().getDeck().getHero().getName(),
                            entry.getValue().getGameMode().getClass().getSimpleName(), entry.getValue().getPrize())) {
                @Override
                public void main() {
                    game = entry.getValue().getLevelGame(Account.getActiveAccount());
                    this.closeWindow();
                    thisGameWindow.openWindow();
                }
            });
        }
    }

    private void initializeCustomWindow(ChoosingWindow custom) {
        for (Map.Entry<String, Deck> entry : Account.getActiveAccount().getCollection().getAllDecks().entrySet()) {
            ChoosingWindow mode = new ChoosingWindow(custom,
                    "Choosing Mode",
                    String.format("[Deck Name: %s] [Hero: %s]",
                            entry.getValue().getName(), entry.getValue().getHero().getName()),
                    "Choosing Mode");
            custom.getSubWindows().add(mode);
            initializeKillingEnemyHeroAndCarryingFlagWindows(mode, entry);
            initializeCollectingFlagWindow(mode, entry);
        }
    }

    private <T> void initializeKillingEnemyHeroAndCarryingFlagWindows(ChoosingWindow mode, Map.Entry<String, T> entry) {
        GameWindow thisGameWindow = this;
        ArrayList<String> titles = new ArrayList<>();
        titles.add("Killing Enemy Hero");
        titles.add("Carrying Flag");
        for (String title : titles) {
            mode.getSubWindows().add(new OperatingWindow(mode, title) {
                @Override
                public void main() {
                    GameMode gameMode;
                    if (title.equals("Killing Enemy Hero")) {
                        gameMode = new KillingEnemyHero();
                    } else {
                        gameMode = new CarryingFlag();
                    }
                    if (entry.getValue() instanceof Account) {
                        game = new Game(gameMode, Account.getActiveAccount(), (Account) entry.getValue());
                    } else {
                        game = new Game(gameMode, Account.getActiveAccount(), (Deck) entry.getValue());
                    }
                    this.closeWindow();
                    thisGameWindow.openWindow();
                }
            });
        }
    }

    private <T> void initializeCollectingFlagWindow(ChoosingWindow mode, Map.Entry<String, T> entry) {
        GameWindow thisGameWindow = this;
        ChoosingWindow numberOfFlags = new ChoosingWindow
                (mode, "Choose Number Of Flags", "Collecting Flag", "#final#") {
            @Override
            public void main() {
                while (true) {
                    WindowGraphic.getRandomWindowGraphics().showWindowBody(this);
                    String request = Request.getNextRequest();
                    Pattern pattern = Pattern.compile("(\\d+)[\t ]*");
                    Matcher matcher = pattern.matcher(request);
                    if (matcher.matches()) {
                        if (handleRequest(Integer.parseInt(matcher.group(1)))) {
                            break;
                        }
                    } else {
                        Message.GameWindow.FailMessage.invalidCommand();
                    }
                }
            }

            private boolean handleRequest(int request) {
                if (request == 0) {
                    this.closeWindow();
                    this.getSuperWindow().openWindow();
                    return true;
                } else {
                    this.closeWindow();
                    if (entry.getValue() instanceof Account) {
                        game = new Game(new CollectingFlag(request), Account.getActiveAccount(), (Account) entry.getValue());
                    } else {
                        game = new Game(new CollectingFlag(request), Account.getActiveAccount(), (Deck) entry.getValue());
                    }
                    thisGameWindow.openWindow();
                    this.closeWindow();
                    return true;
                }
            }
        };
        mode.getSubWindows().add(numberOfFlags);
    }

    private void initializeMultiWindow(ChoosingWindow multi) {
        for (Map.Entry<String, Account> entry : Account.getUsernameToAccountObject().entrySet()) {
            if (entry.getValue() != Account.getActiveAccount() && entry.getValue().getCollection().getMainDeck() != null) {
                ChoosingWindow mode = new ChoosingWindow
                        (multi, "Choosing Mode", String.format("[User Name: %s]", entry.getKey()), "Choosing Mode");
                multi.getSubWindows().add(mode);
                initializeKillingEnemyHeroAndCarryingFlagWindows(mode, entry);
                initializeCollectingFlagWindow(mode, entry);
            }
        }
    }

    public GameWindow() {
        initializeAndRunBeforeGameWindows();
    }

    @Override
    public void main() {
        while (game.getGameMode().winner == null) {
            Message.GameWindow.InsideGame.showMainView(game);
            if (game.getActivePlayer() instanceof HumanPlayer) {
                getPlayerAction();
            } else {
                ((AIPlayer) game.getActivePlayer()).doSomething();
            }
        }
        endGame();
    }

    private void endGame() {
        Message.GameWindow.InsideGame.showMainView(game);
        Player winner = game.getGameMode().winner;
        Player loser = game.getOtherPlayer(winner);
        updatePlayerMatchHistory(game, winner, loser, true);
        updatePlayerMatchHistory(game, loser, winner, false);
        while (true) {
            Message.GameWindow.AfterGame.showWinner(winner);
            String request = Request.getNextRequest();
            if (request.equals("exit")) {
                break;
            }
        }
        this.closeWindow();
    }

    private static void updatePlayerMatchHistory(Game game, Player player, Player enemy, boolean isWinner) {
        if (player instanceof HumanPlayer) {
            HumanPlayer humanPlayer = (HumanPlayer) player;
            if (isWinner) {
                humanPlayer.getAccount().derrick += game.prize;
            }
            String opponentName = enemy instanceof AIPlayer ? "AI" : ((HumanPlayer) enemy).getAccount().getUsername();
            humanPlayer.getAccount().putGameInHistory(opponentName, isWinner);
        }
    }

    private void getPlayerAction() {
        String request = Request.getNextRequest();
        if (request.matches("Help")) {
            help();
        } else if (request.matches("Select \\d \\d")) {
            selectWarrior(request);
        } else if (request.matches("Select \\d")) {
            selectCard(request);
        } else if (request.equals("Select SSP")) {
            selectSpecialPower();
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
        } else if (request.equals("Enter graveyard")) {
            graveyardMenu();
        } else if (request.equals("exit")) {
            exit();
        } else {
            Message.GameWindow.FailMessage.invalidCommand();
        }
    }

    private void selectSpecialPower() {
        if (game.getActivePlayer().getPlayerHero().getPower() != null) {
            game.getSelectedThings().selectSpecialPower(game);
        } else {
            Message.GameWindow.FailMessage.emptyCard();
        }
    }

    private void exit() {
        game.getGameMode().winner = game.getOtherPlayer(game.getActivePlayer());
    }

    private void useCollectibleItem(String request) {
        Cell cell = getCellByMessage(request);
        if (cell == null) {
            return;
        }
        if (game.getSelectedThings().collectibleItem != null) {
            if (game.useCollectible(game.getSelectedThings().collectibleItem, cell)) {
                Message.GameWindow.InsideGame.DoneMessages.usingItem();
            } else {
                Message.GameWindow.FailMessage.notEnoughNecessaryCondition();
            }
        } else {
            Message.GameWindow.FailMessage.noSelectedCollectibleItem();
        }
        game.getSelectedThings().deselectAll();
    }

    private void showCollectibleItemInfo() {
        Spell item = game.getSelectedThings().collectibleItem;
        if (item != null) {
            while (true) {
                Message.GameWindow.InsideGame.showCardDescription(item);
                String request = Request.getNextRequest();
                if (request.equals("exit")) {
                    return;
                }
            }

        } else {
            Message.GameWindow.FailMessage.noSelectedCollectibleItem();
        }
    }

    private void showCardInfo(String request) {
        Pattern pattern = Pattern.compile("(\\d{2,3})");
        Matcher matcher = pattern.matcher(request);
        matcher.find();
        int cardID = Integer.parseInt(matcher.group(1));
        if (Card.getAllCards().containsKey(cardID)) {
            while (true) {
                Message.GameWindow.betweenTwoPageLine();
                Message.GameWindow.InsideGame.showCardDescription(Card.getAllCards().get(cardID));

                request = Request.getNextRequest();
                if (request.equals("exit")) {
                    return;
                }
            }
        } else {
            Message.GameWindow.FailMessage.noCardMatched();
        }
    }

    private void useSpecialPower(String request) {
        Cell cell = getCellByMessage(request);
        if (cell == null) {
            return;
        }
        if (game.useSpecialPower(cell)) {
            Message.GameWindow.InsideGame.DoneMessages.useSpecialPower();
        } else {
            Message.GameWindow.FailMessage.notEnoughNecessaryCondition();
        }
        game.getSelectedThings().deselectAll();
    }

    private void replaceCard() {
        if (game.getSelectedThings().cardHandIndex != null) {
            if (game.replaceCard(game.getSelectedThings().cardHandIndex)) {
                Message.GameWindow.InsideGame.DoneMessages.replaceCard();
            } else {
                Message.GameWindow.FailMessage.notEnoughNecessaryCondition();
            }
        } else {
            Message.GameWindow.FailMessage.noSelectedCard();
        }
        game.getSelectedThings().deselectAll();
    }

    private void insertCardinGame(String request) {
        Cell cell = getCellByMessage(request);
        if (cell == null) {
            return;
        }
        if (game.getSelectedThings().cardHandIndex != null) {
            if (game.useCard(game.getSelectedThings().cardHandIndex, cell)) {
                Message.GameWindow.InsideGame.DoneMessages.useCard();
            } else {
                Message.GameWindow.FailMessage.notEnoughNecessaryCondition();
            }
        } else {
            Message.GameWindow.FailMessage.noSelectedCard();
        }
        game.getSelectedThings().deselectAll();
    }

    private void comboAttack(String request) {
        Cell cell = getCellByMessage(request);
        if (cell == null) {
            return;
        }
        if (game.getSelectedThings().getWarriorsCell().size() > 1) {
            if (cell.getWarrior() != null && game.getActivePlayer() != game.getWarriorsPlayer(cell.getWarrior())) {
                if (game.comboAttack(game.getSelectedThings().getWarriorsCell(), cell)) {
                    Message.GameWindow.InsideGame.DoneMessages.comboAttack();
                } else {
                    Message.GameWindow.FailMessage.notEnoughNecessaryCondition();
                }
            } else {
                Message.GameWindow.FailMessage.thereIsNoEnemyWarriorInThisCell();
            }
        } else {
            Message.GameWindow.FailMessage.noSelectedWarriorsGroup();
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
                if (game.move(game.getSelectedThings().getWarriorsCell().get(0), cell)) {
                    Message.GameWindow.InsideGame.DoneMessages.move();
                } else {
                    Message.GameWindow.FailMessage.notEnoughNecessaryCondition();
                }
            } else {
                Message.GameWindow.FailMessage.targetCellIsField();
            }
        } else {
            Message.GameWindow.FailMessage.noSelectedWarrior();
        }
        game.getSelectedThings().deselectAll();
    }

    private void attack(String request) {
        Cell cell = getCellByMessage(request);
        if (cell == null) {
            return;
        }
        if (game.getSelectedThings().getWarriorsCell().size() == 1) {
            if (cell.getWarrior() != null && game.getActivePlayer() != game.getWarriorsPlayer(cell.getWarrior())) {
                if (game.attack(game.getSelectedThings().getWarriorsCell().get(0), cell)) {
                    Message.GameWindow.InsideGame.DoneMessages.attack();
                } else {
                    Message.GameWindow.FailMessage.notEnoughNecessaryCondition();
                }
            } else {
                Message.GameWindow.FailMessage.thereIsNoEnemyWarriorInThisCell();
            }
        } else {
            Message.GameWindow.FailMessage.noSelectedWarrior();
        }
        game.getSelectedThings().deselectAll();
    }

    private void selectCard(String request) {
        int index = Integer.parseInt(request.replace("Select ", ""));
        if (index < Constant.GameConstants.handSize) {
            if (game.getActivePlayer().getHand().get(index) != null) {
                game.getSelectedThings().selectCard(game, index);
                Message.GameWindow.InsideGame.DoneMessages.selectCart();
            } else {
                Message.GameWindow.FailMessage.emptyCard();
            }
        } else {
            Message.GameWindow.FailMessage.wrongIndex();
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
        Message.GameWindow.FailMessage.wrongIndex();
        return null;
    }

    private void graveyardMenu() {
        while (true) {
            Message.GameWindow.InsideGame.graveyardWindow(game);
            String request = Request.getNextRequest();
            if (request.equals("Exit")) {
                return;
            } else {
                Message.GameWindow.FailMessage.invalidCommand();
            }
        }
    }

    private void collectibleItemsMenu() {
        while (true) {
            Message.GameWindow.betweenTwoPageLine();
            Message.GameWindow.InsideGame.collectiblesWindow(game);
            String request = Request.getNextRequest();
            if (request.equals("exit")) {
                return;
            } else if (request.matches("Select \\d+")) {
                int index = Integer.parseInt(request.replace("Select ", ""));
                if (index < game.getCollectibleItems().size()) {
                    game.getSelectedThings().selectColletableItem(game.getCollectibleItems().get(index));
                    Message.GameWindow.InsideGame.DoneMessages.selectItem();
                } else {
                    Message.GameWindow.FailMessage.wrongIndex();
                }
            } else {
                Message.GameWindow.FailMessage.invalidCommand();
            }
        }
    }

    private void help() {
        while (true) {
            Message.GameWindow.betweenTwoPageLine();
            Message.GameWindow.InsideGame.help();
            String input = Request.getNextRequest();
            if (input.matches("exit")) {
                break;
            } else {
                Message.GameWindow.FailMessage.invalidCommand();
            }
        }
    }

    private void selectWarrior(String request) {
        Cell cell = getCellByMessage(request);
        if (cell == null) {
            return;
        }
        if (!game.getSelectedThings().getWarriorsCell().contains(cell) && cell.getWarrior() != null &&
                game.getActivePlayer() == game.getWarriorsPlayer(cell.getWarrior())) {
            game.getSelectedThings().seletWarrior(game.getBoard().getCell(cell.getRow(), cell.getColumn()));
            Message.GameWindow.InsideGame.DoneMessages.selectWarrior();
        } else {
            Message.GameWindow.FailMessage.youHaveNoOwnWarriorInThisCell();
        }
    }
}