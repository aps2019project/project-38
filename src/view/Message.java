package view;

import model.*;
import model.cards.*;
import model.gamemodes.CarryingFlag;
import model.gamemodes.CollectingFlag;
import model.player.AIPlayer;
import model.player.HumanPlayer;
import model.player.Player;
import model.triggers.BurningCell;
import model.triggers.Flag;
import model.triggers.HolyBuff;
import model.triggers.Poisoned;

import java.util.HashMap;
import java.util.Map;

public interface Message {

    // general messages

    static void invalidInput() {
        System.out.println("Invalid input");
    }

    static void noCardInThisRepository() {
        System.out.println("There is no card in this repository :(");
    }

    static void printSomeThing(String someThing) {
        System.out.println(someThing);
    }

    static void INTER() {
        System.out.println();
    }

    // mainMenu messages

    static void showMainMenuHelp() {
        System.out.println("___--Main Menu--___");
        System.out.println("    1- Collection");
        System.out.println("    2- Shop");
        System.out.println("    3- Battle");
        System.out.println("    0- Exit");
    }

    // in shop
    static void showShopHelp() {
        System.out.println("___--Shop Menu--___");
        System.out.println("    1- Show Cards In Shop");
        System.out.println("    2- Show Cards In Collection");
        System.out.println("    3- Search In Shop");
        System.out.println("    4- Search In Collection");
        System.out.println("    5- Buy");
        System.out.println("    6- Sell");
        System.out.println("    0- Exit");
    }

    static void showInfoOfHeroPlusPrice(Hero hero, int numberOfHeroes, String kindOfAttackArea, String buyOrSell) {
        System.out.printf("     %d ) Name : %s - AP : %d - HP : %d - class : %s - SpecialPower : %s - %sCost : %d\n",
                numberOfHeroes, hero.getName(), hero.getAp(), hero.getHp(), kindOfAttackArea, hero.description.descriptionOfCardSpecialAbility, buyOrSell, hero.getPrice());
    }

    static void showInfoOfHeroMinusPrice(Hero hero, int numberOfHeroes, String kindOfAttackArea) {
        System.out.printf("     %d ) Name : %s - AP : %d - HP : %d - class : %s - SpecialPower : %s\n",
                numberOfHeroes, hero.getName(), hero.getAp(), hero.getHp(), kindOfAttackArea, hero.description.descriptionOfCardSpecialAbility);
    }

    static void showInfoOfItemPlusPrice(Spell spell, int numberOfItems, String buyOrSell) {
        System.out.printf("     %d ) Name : %s - Description : %s - %sCost : %d\n",
                numberOfItems, spell.getName(), spell.description.descriptionOfCardSpecialAbility, buyOrSell, spell.getPrice());
    }

    static void showInfoOfItemMinusPrice(Spell spell, int numberOfItems) {
        System.out.printf("     %d ) Name : %s - Description : %s\n",
                numberOfItems, spell.getName(), spell.description.descriptionOfCardSpecialAbility);
    }

    static void showInfoOfCardPlusPrice(Card card, int numberOfCards, String typeOfCard, String buyOrSell) {
        System.out.printf("     %d ) Type : %s - Name : %s - MP : %d - Description : %s - %sCost : %d\n",
                numberOfCards, typeOfCard, card.getName(), card.getRequiredMana(), card.description.descriptionOfCardSpecialAbility, buyOrSell, card.getPrice());
    }

    static void showInfoOfCardMinusPrice(Card card, int numberOfCards, String typeOfCard) {
        System.out.printf("     %d ) Type : %s - Name : %s - MP : %d " +
                        "- Description : %s\n",
                numberOfCards, typeOfCard, card.getName(), card.getRequiredMana(), card.description.descriptionOfCardSpecialAbility);
    }

    static void InterCardName() {
        System.out.println("Please Inter CardName:");
    }

    static void printCardID(int cardID) {
        System.out.printf("%d\n", cardID);
    }

    static void thereIsNoCardWithThisNameInShop() {
        System.out.println("There is no card with this name in shop cards :(");
    }

    static void thereIsNoCardWithThisNameInCollection() {
        System.out.println("There is no card with this name in collection cards :(");
    }

    static void thereIsNoCardWithThisNameAtAll() {
        System.out.println("There is no card with this name at all :(");
    }

    static void haveNotThisCardInYourCollection() {
        System.out.println("You haven't this card in your collection. You can't sell it :(");
    }

    static void haveNotEnoughMoney() {
        System.out.println("You haven't enough money :(");
    }

    static void buyWasSuccessful() {
        System.out.println("You bought the cart successfully :)");
    }

    static void sellWasSuccessful() {
        System.out.println("You sell the card successfully :)");
    }

    static void haveAlready3Items() {
        System.out.println("You have 3 items. You couldn't buy any other item :(");
    }

    static void haveXNumberOfCardIDInYourCollection(int numberOfFoundIDs) {
        System.out.printf("You have %d number of this card in your collection :)\n", numberOfFoundIDs);
    }

    static void existACardWithThisNameInShop() {
        System.out.println("There is a card with this name in shop :)");
    }

    // in account:
    static void showAccountHelp() {
        System.out.println("____--Welcome to the DUELYST--____ ");
        System.out.println("Account Menu:");
        System.out.println("    1- Create Account");
        System.out.println("    2- Login");
        System.out.println("    3- Show LeaderBoard");
        System.out.println("    0- Exit");
    }

    static void showLeaderBoard(int i, String username, int numberOfWins) {
        System.out.printf("%d- UserName : %s - Wins : %d\n", i, username, numberOfWins);
    }

    static void interUsername() {
        System.out.println("Please inter username:");
    }

    static void interPassword() {
        System.out.println("Please inter password:");
    }

    static void interPasswordAgain() {
        System.out.println("Please inter password again:");
    }

    static void noIdenticalPassword() {
        System.out.println("Your passwords aren't identical :(");
    }

    static void accountCreatedSuccessfully() {
        System.out.println("Account created successfully :)");
    }

    static void leaderBoardIsEmpty() {
        System.out.println("LeaderBoard is empty :(");
    }

    static void thereIsAnAccountWithThisName() {
        System.out.println("There is already an account with this name :(");
    }

    static void thereIsNoAccountWithThisName() {
        System.out.println("There is no account with this name :(");
    }

    static void incorrectPassword() {
        System.out.println("Your password is incorrect :(");
    }

    // in collection
    static void showCollectionHelp() {
        System.out.println("___--Collection Menu--___");
        System.out.println("    1- Show Info Of Cards Of Collection");
        System.out.println("    2- Search In Collection");
        System.out.println("    3- Create Deck");
        System.out.println("    4- Delete Deck");
        System.out.println("    5- Add Card To Deck");
        System.out.println("    6- Remove Card From Deck");
        System.out.println("    7- Check Validation Of A Deck");
        System.out.println("    8- Select A Deck As Main");
        System.out.println("    9- Show Info Of A Specific Deck");
        System.out.println("    10- Show Info Of All Decks");
//        System.out.println("    11- Save :||");
        System.out.println("    0- Exit");
    }

    static void thereIsAlreadyADeckWhitThisName() {
        System.out.println("There is already a deck with this name");
    }

    static void thereIsNoDeckWithThisName() {
        System.out.println("There is no deck with this name :(");
    }

    static void thereIsACardWithThisNameInThisDeck() {
        System.out.println("There is already a card whit this name in this deck");
    }

    static void have20CardsInThisDeck() {
        System.out.println("You have 20 cards in your deck. You couldn't put any other card");
    }

    static void thereIsAHeroInThisDeck() {
        System.out.println("there is already a hero in this deck. You can't add any other");
    }

    static void thereIsNoCardWithThisNameInThisDeck() {
        System.out.println("There is no card with this name in this deck :(");
    }

    static void deckCreated() {
        System.out.println("Deck created :)");
    }

    static void deckDeleted() {
        System.out.println("Deck deleted :)");
    }

    static void cardAddedToDeckSuccessfully() {
        System.out.println("Card added to deck successfully :)");
    }

    static void cardRemovedFromDeckSuccessfully() {
        System.out.println("Card removed from deck successfully :)");
    }

    static void deckIsValid() {
        System.out.println("This deck is valid :)");
    }

    static void deckIsNotValid() {
        System.out.println("This deck is not valid :(");
    }

    static void deckSelectedAsMain() {
        System.out.println("This deck selected as main successfully :)");
    }

    static void interDeckName() {
        System.out.println("Please inter deckName:");
    }

    static void interCardName() {
        System.out.println("Please inter cardName:");
    }

    static void showDeckName(int index, String deckName) {
        System.out.printf("(( %d )) %s :\n", index, deckName);
    }

    static void noDeckExist() {
        System.out.println("There is no deck :(");
    }

    static void thereIsAnItemInThisDeck() {
        System.out.println("There is an item in this deck");
    }



    //******************************************************

    interface GameWindow {
        static void betweenTwoPageLine() {
            System.out.println("*************************************************************************************");
        }

        static void betweenTwoLineLine() {
            System.out.println(".....................................................................................");
        }

        interface BeforeGame {
            static void invalidMainDeck() {
                System.out.println("mainDeck is invalid");
            }

            static void singleOrMultiMenu() {
                System.out.println("1. Single player");
                System.out.println("2. Multi player");
            }

            static void StoryOrCustomMenu() {
                System.out.println("1. Levels");
                System.out.println("2. Custom game");
            }

            static void modeAndDeckMenu() {
                System.out.println("Select mode and enemy deck\nfor example: " +
                        "Start game [deck name] [mode name] [number of flags]*");
                System.out.println("Decks:");
                for (Map.Entry<String, Deck> entry : Account.getActiveAccount().getCollection().getAllDecks().entrySet()) {
                    System.out.println(entry.getKey());
                }
                System.out.println("Modes:");
                System.out.println("KillingEnemyHero");
                System.out.println("CarryingFlag");
                System.out.println("CollectingFlag");
            }

            static void accountMenu(HashMap<String, Account> accounts) {
                System.out.println("Select a ready account\nfor example: Select user [user name]");
                for (Map.Entry<String, Account> entry : accounts.entrySet()) {
                    if (entry.getValue() != Account.getActiveAccount()) {
                        System.out.println(entry.getKey());
                    }
                }
            }

            static void showLevelsForStoryMode() {
                for (Map.Entry<String, Level> entry : Level.getAvailableLevels().entrySet()) {
                    System.out.printf("%s: Hero Name: %s Mode: %s Prize: %s\n", entry.getKey(),
                            entry.getValue().getDeck().getHero().getName(),
                            entry.getValue().getGameMode().getClass().getSimpleName(), entry.getValue().getPrize());
                }
            }

            static void modeMenu() {
                System.out.println("Choose mode\nfor example: Start multiplayer game [mode name] [number of flags]*");
                System.out.println("Modes:");
                System.out.println("KillingEnemyHero");
                System.out.println("CarryingFlag");
                System.out.println("CollectingFlag");
            }
        }

        interface InsideGame {
            static void showMainView(Game game) {
                showBoardAbove(game);
                showBoard(game);
                showBoardBottom(game);
            }

            static void showBoardAbove(Game game) {
                int activePlayerNumber = game.getActivePlayer() == game.getPlayers()[0] ? 0 : 1;
                String completeName = game.getActivePlayer() instanceof AIPlayer ? "[AI]" : String.format
                        ("[Human] [User Name: %s]", ((HumanPlayer) game.getActivePlayer()).getAccount().getUsername());
                betweenTwoPageLine();
                System.out.printf("Game Mode: %s ", game.getGameMode().getClass().getSimpleName());
                if (game.getGameMode() instanceof CarryingFlag) {
                    CarryingFlag mode = (CarryingFlag) game.getGameMode();
                    System.out.printf("[Player 0 Score: %d] [Player 1 Score: %d]\n", mode.getPlayersScore()[0], mode.getPlayersScore()[1]);
                }
                else if (game.getGameMode() instanceof CollectingFlag) {
                    CollectingFlag mode = (CollectingFlag) game.getGameMode();
                    System.out.printf("[Player 0 Flags: %d] [Player 1 Flags: %d]\n",
                            game.getGameMode().getNumberOFPlayerFlags(game.getPlayers()[0]),
                            game.getGameMode().getNumberOFPlayerFlags(game.getPlayers()[0]));
                }
                System.out.println(activePlayerNumber + ": " + completeName);
                System.out.println("Mana: " + game.getActivePlayer().mana);
            }

            static void showBoardBottom(Game game) {
                System.out.println("Hand:");
                for (Map.Entry<Integer, Card> entry : game.getActivePlayer().getHand().entrySet()) {
                    if (entry.getValue() == null) {
                        System.out.println(entry.getKey() + ": Empty");
                    }
                    else {
                        System.out.print(entry.getKey() + ". ");
                        CardView.showCard(entry.getValue());
                    }
                }
                betweenTwoLineLine();
                Card nextCard = game.getActivePlayer().getNextCard();
                System.out.printf("Next Turn Card: [Type: %s] %s\n",
                        nextCard instanceof Spell ? "Spell" : "Minion", CardView.cardDetail(nextCard));
                betweenTwoLineLine();
                HeroPower heroPower = game.getActivePlayer().getPlayerHero().getPower();
                CardView.ShowSpecialPower(heroPower);
                betweenTwoLineLine();
                showSelectedThings(game);
                betweenTwoLineLine();
            }

            static void showSelectedThings(Game game) {
                System.out.print("SelectedCards: ");
                if (game.getSelectedThings().getWarriorsCell().size() != 0) {
                    System.out.printf("{Warrior%s}\n", game.getSelectedThings().getWarriorsCell().size() > 1 ? "s" : "");
                    for (Cell cell : game.getSelectedThings().getWarriorsCell()) {
                        CardView.showCard(cell.getWarrior());
                    }
                }
                else if (game.getSelectedThings().collectibleItem != null) {
                    System.out.println("{Collectible Item}");
                    Spell item = game.getSelectedThings().collectibleItem;
                    CardView.showItem(item);
                }
                else if (game.getSelectedThings().cardHandIndex != null) {
                    System.out.println("{Card}");
                    Card card = game.getActivePlayer().getHand().get(game.getSelectedThings().cardHandIndex);
                    CardView.showCard(card);
                }
                else if (game.getSelectedThings().specialPowerIsSelected) {
                    System.out.println("{Special Power}");
                    HeroPower specialPower = game.getActivePlayer().getPlayerHero().getPower();
                    CardView.ShowSpecialPower(specialPower);
                }
                else {
                    System.out.print("Empty\n");
                }
            }

            static void showBoard(Game game) {
                System.out.print(" ");
                for (int i = 0; i < Constant.GameConstants.boardColumn * 5; i++) {
                    System.out.print(i % 5 == 2 ? i / 5 : " ");
                }
                System.out.println();
                horizontalBoardLine();
                for (int i = 0; i < Constant.GameConstants.boardRow; i++) {
                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < Constant.GameConstants.boardColumn; k++) {
                            if (k == 0) System.out.print(j == 1 ? i : " ");
                            switch (j) {
                                case 0:
                                    System.out.print("|");
                                    cellFirstLine(game.getBoard().getCell(i, k));
                                    break;
                                case 1:
                                    System.out.print("|");
                                    cellSecondLine(game.getBoard().getCell(i, k));
                                    break;
                                case 2:
                                    System.out.print("|");
                                    cellThirdLine(game.getBoard().getCell(i, k));
                                    break;
                            }
                        }
                        System.out.println("|");
                    }
                    horizontalBoardLine();
                }
            }

            static void horizontalBoardLine() {
                System.out.print(" +");
                for (int i = 0; i < Constant.GameConstants.boardColumn; i++) {
                    System.out.print("----+");
                }
                System.out.println();
            }

            static void cellFirstLine(Cell cell) {
                Game game = cell.getBoard().getGame();
                if (cell.getWarrior() != null) {
                    int playerNumber = game.getWarriorsPlayer(cell.getWarrior()) == game.getPlayers()[0] ? 0 : 1;
                    System.out.print(playerNumber + String.format("%3d", cell.getWarrior().getID()));
                } else {
                    System.out.print("    ");
                }
            }

            static void cellSecondLine(Cell cell) {
                if (cell.getWarrior() != null) {
                    System.out.print(String.format("%2d%2d", cell.getWarrior().getAp(), cell.getWarrior().getHp()));
                } else {
                    System.out.print("    ");
                }
            }

            static void cellThirdLine(Cell cell) {
                //Flag --> F
                if (cell.getWarrior() != null) {
                    if (cell.getWarrior().getTriggers().stream().anyMatch(trigger -> trigger instanceof Flag)) {
                        System.out.print("F");
                    } else {
                        System.out.print(" ");
                    }
                } else {
                    if (cell.getTriggers().stream().anyMatch(trigger -> trigger instanceof Flag)) {
                        System.out.print("F");
                    } else {
                        System.out.print(" ");
                    }
                }
                //HolyBuff --> H
                if (cell.getTriggers().stream().anyMatch(trigger -> trigger.getTriggers().stream().anyMatch
                        (insideTrigger -> insideTrigger instanceof HolyBuff))) {
                    System.out.print("H");
                } else {
                    System.out.print(" ");
                }
                //Poison --> P
                if (cell.getTriggers().stream().anyMatch(trigger -> trigger.getTriggers().stream().anyMatch
                        (insideTrigger -> insideTrigger instanceof Poisoned))) {
                    System.out.print("P");
                } else {
                    System.out.print(" ");
                }
                //todo --> B
                if (cell.getTriggers().stream().anyMatch(trigger -> trigger instanceof BurningCell)) {
                    System.out.print("B");
                } else {
                    System.out.print(" ");
                }
            }

            static void help() {
                System.out.println("Select Warrior: Select [row] [column]");
                System.out.println("(you can select multi warriors for combo by using above command repeatedly)");
                System.out.println("Select Card From Hand: Select [hand index]");
                System.out.println("Select Special Power: Select SPP");
                System.out.println("Deselect Warriors: Deselect warriors");
                System.out.println("(if you selected just one warrior you can attack to an enemy warrior): Attack [row] [column]");
                System.out.println("(if you selected just one warrior you can move it): move [row] [column]");
//                System.out.println("(if you selected just one warrior you can see all its effects and triggers): Peek");
                System.out.println("(if you selected more than one warrior you can combo attack to an enemy warrior): Attack combo [row] [column]");
                System.out.println("(if you selected a card you can put it on board): Insert in [row] [column]");
                System.out.println("(if you selected a card you can replace it(once in each turn)): Replace");
                System.out.println("Use Special Power: Use special power [row] [column]");
                System.out.println("Show Card Info: Show card info [cardID]");
                System.out.println("(get out of above window): exit");
                System.out.println("End Turn: End turn");
                System.out.println("Show Collectible Items: Show collectibles");
                System.out.println("(get out of above window): exit");
                System.out.println("(if you are in collectible items window you can select collectible item): Select [item index]");
                System.out.println("(get out of above window): exit");
                System.out.println("(if you selected an collectible item in board window you can see item info): Show collectible item info");
                System.out.println("(if you selected an collectible item in board window you can use it): Use collectible item [row] [column]");
                System.out.println("Show Graveyard: Enter graveyard");
                System.out.println("(get out of above window): exit");
            }


            static void collectiblesWindow(Game game) {
                System.out.println("Collectible Items:");
                for (int i = 0; i < game.getCollectibleItems().size(); i++) {
                    Spell item = game.getCollectibleItems().get(i);
                    CardView.showItem(item);
                }
            }

            static void graveyardWindow(Game game) {
                System.out.println("Graveyard Cards: ");
                for (Card card : game.getActivePlayer().getUsedCards()) {
                    CardView.showCard(card);
                    //todo can Items be in grave yard?
                }
            }

            static void showCardDescription(Card card) {
                CardView.showCard(card);
                CardView.showCardDescriptionAndTarget(card);
            }

            interface CardView {
                static void showCardDescriptionAndTarget(Card card) {
                    if (card.description != null) {
                        System.out.println("Description Of Card Ability: " +
                                card.description.descriptionOfCardSpecialAbility);
                        if (card instanceof Spell) {
                            System.out.println("Target Type: " + card.description.targetType);
                        }
                    }
                }

                static void showCard(Card card) {
                    if (card instanceof Warrior) {
                        showWarrior((Warrior)card);
                    }else {
                        showSpell((Spell)card);
                    }
                }

                static void showSpell(Spell spell) {//private
                    System.out.printf("Spell: %s\n", cardDetail(spell));
                }

                static void showWarrior(Warrior warrior) {//private
                    System.out.printf("%s: %s [AP: %d] [HP: %d]\n",warrior instanceof Hero ? "Hero" : "Minion",
                            cardDetail(warrior), warrior.getAp(), warrior.getHp());
                }

                static void ShowSpecialPower(HeroPower heroPower) {
                    //todo we should keep a card in HeroPower instead of extending Spell
                    System.out.printf("Special Power: [Type: Spell] %s [Cool Down Remaining: %d]\n",
                            cardDetail(heroPower), heroPower.coolDownRemaining);
                }

                static void showItem(Card card) {
                    System.out.printf("Item: [Type: %s]%s\n",
                            card instanceof Spell ? "Spell" : "Minion", cardDetail(card));
                }

                static String cardDetail(Card card) { //private
                    return String.format("[Name: %s] [ID: %d] [Required Mana: %d]",
                            card.getName(), card.getID(), card.getRequiredMana());
                }
            }

            interface DoneMessages {
                static void usingItem(){
                    System.out.println("using item is done");
                }

                static void useSpecialPower() {
                    System.out.println("using special power is done");
                }

                static void replaceCard() {
                    System.out.println("replace card is done");
                }

                static void useCard() {
                    System.out.println("use card is done");
                }

                static void comboAttack() {
                    System.out.println("combo attack is done");
                }

                static void move() {
                    System.out.println("move is done");
                }

                static void attack() {
                    System.out.println("attack done");
                }

                static void selectCart() {
                    System.out.println("select card done");
                }

                static void selectItem() {
                    System.out.println("select item done");
                }

                static void selectWarrior() {
                    System.out.println("select warrior done");
                }
            }
        }

        interface AfterGame {
            static void showWinner(Player winner) {
                betweenTwoPageLine();
                System.out.printf("Winner: %s Player %s\n", winner.getClass().getSimpleName(), winner instanceof HumanPlayer
                        ? ((HumanPlayer)winner).getAccount().getUsername() : "");
            }
        }

        interface FailMessage {
            static void notEnoughNecessaryCondition() {
                System.out.println("not enough necessary condition");
            }

            static void wrongIndex() {
                System.out.println("wrong index");
            }

            static void youHaveNoOwnWarriorInThisCell() {
                System.out.println("you have no own warrior in this cell");
            }

            static void thereIsNoEnemyWarriorInThisCell() {
                System.out.println("there is no enemy warrior in this cell");
            }

            static void invalidCommand() {
                System.out.println("invalid command");
            }

            static void noSelectedCollectibleItem() {
                System.out.println("you have no selected collectible item");
            }

            static void noCardMatched() {
                System.out.println("no card matched");
            }

            static void noSelectedCard() {
                System.out.println("ou have no selected card");
            }

            static void noSelectedWarriorsGroup() {
                System.out.println("you should select a group of warriors for combo attack");
            }

            static void targetCellIsField() {
                System.out.println("target cell is filled");
            }

            static void noSelectedWarrior() {
                System.out.println("you have no selected warrior");
            }

            static void emptyCard() {
                System.out.println("you cant select empty cart");
            }
        }
    }

    static void notEnoughCardNumber() {
        System.out.println("You can't add this card to your deck. You haven't enough number of it in your collection");
    }
}