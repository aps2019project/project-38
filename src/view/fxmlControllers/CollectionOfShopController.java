package view.fxmlControllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Account;
import model.Collection;
import model.Deck;
import model.cards.*;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import static view.Utility.scaleCard;

public class CollectionOfShopController implements Initializable {
    public static CollectionOfShopController collectionOfShopController;
    public VBox minionsLeftVBox;
    public VBox minionsMiddleVBox;
    public VBox minionsRightVBox;
    public TextField minionsSearchTextField;
    private HashMap<Warrior, AnchorPane> minions = new HashMap<>(), allMinions = new HashMap<>();
    public VBox heroesLeftVBox;
    public VBox heroesMiddleVBox;
    public VBox heroesRightVBox;
    public TextField heroesSearchTextField;
    private HashMap<Hero, AnchorPane> heroes = new HashMap<>(), allHeroes = new HashMap<>();
    public VBox spellsLeftVBox;
    public VBox spellsMiddleVBox;
    public VBox spellsRightVBox;
    public TextField spellsSearchTextField;
    private HashMap<Spell, AnchorPane> spells = new HashMap<>(), allSpells = new HashMap<>();
    public VBox itemsLeftVBox;
    public VBox itemsMiddleVBox;
    public VBox itemsRightVBox;
    public TextField itemsSearchTextField;
    private HashMap<Spell, AnchorPane> items = new HashMap<>(), allItems = new HashMap<>();
    public ImageView backButton;

    public void back(MouseEvent mouseEvent) {
        WindowChanger.instance.setNewScene(LoadedScenes.shop);

    }

    public void shineBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(new Glow());
    }

    public void resetBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minionsSearchTextField.setOnKeyTyped(this::recalculateMinions);
        heroesSearchTextField.setOnKeyTyped(this::recalculateHeroes);
        spellsSearchTextField.setOnKeyTyped(this::recalculateSpells);
        itemsSearchTextField.setOnKeyTyped(this::recalculateItems);
        calculateEverything();
    }

    public void calculateEverything() {
        initializeAllMinions();
        recalculateMinions(null);
        initializeAllHeroes();
        recalculateHeroes(null);
        initializeAllSpells();
        recalculateSpells(null);
        initializeAllItems();
        recalculateItems(null);
    }

    private synchronized void recalculateMinions(KeyEvent keyEvent) {
        String searchText = minionsSearchTextField.getText();
        minions.entrySet().removeIf(entry -> !allMinions.containsKey(entry.getKey()));
        minions.entrySet().removeIf(entry -> !entry.getKey().getName().toLowerCase()
                .replaceAll("[ \t\\-_]+", "").matches
                        (".*" + searchText.toLowerCase().replaceAll("[ \t\\-_]+", "") + ".*"));
        for (Map.Entry<Warrior, AnchorPane> entry : allMinions.entrySet()) {
            if (!minions.containsKey(entry.getKey()) && entry.getKey().getName().toLowerCase()
                    .replaceAll("[ \t\\-_]+", "").matches
                            (".*" + searchText.toLowerCase().replaceAll("[ \t\\-_]+", "") + ".*"))
                minions.put(entry.getKey(), entry.getValue());
        }
        Platform.runLater(() -> {
            minionsLeftVBox.getChildren().clear();
            minionsMiddleVBox.getChildren().clear();
            minionsRightVBox.getChildren().clear();
        });
        int counter = 0;
        for (Map.Entry<Warrior, AnchorPane> entry : minions.entrySet()) {
            if (counter % 3 == 0) {
                Platform.runLater(() -> minionsLeftVBox.getChildren().add(entry.getValue()));
            }
            else if (counter % 3 == 1) {
                Platform.runLater(() -> minionsMiddleVBox.getChildren().add(entry.getValue()));
            }
            else {
                Platform.runLater(() -> minionsRightVBox.getChildren().add(entry.getValue()));
            }
            counter++;
        }
    }

    private void initializeAllMinions() {
        for (Warrior minion : CardFactory.getAllBuiltMinions()) {
            if (Account.getActiveAccount().getCollection().getHowManyCard().containsKey(minion.getName())&&
                    Account.getActiveAccount().getCollection().getHowManyCard().get(minion.getName()) > 0 &&
                    !allMinions.containsKey(minion)) {
                loadMinion(minion);
            }
        }
        for (Iterator<Map.Entry<Warrior, AnchorPane>> iterator = allMinions.entrySet().iterator(); iterator.hasNext();) {
            Warrior minion = iterator.next().getKey();
            if (!Account.getActiveAccount().getCollection().getHowManyCard().containsKey(minion.getName()) ||
                    Account.getActiveAccount().getCollection().getHowManyCard().get(minion.getName()) <= 0) {
                iterator.remove();
            }
        }
    }

    private void loadMinion(Warrior minion) {
        AnchorPane anchorPane = null;
        WarriorCardController warriorCardController = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("warriorCart.fxml"));
            anchorPane = scaleCard(fxmlLoader.load());
            warriorCardController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        warriorCardController.setFields(minion, "for sell");
        allMinions.put(minion, anchorPane);
    }

    private synchronized void recalculateHeroes(KeyEvent keyEvent) {
        String searchText = heroesSearchTextField.getText();
        heroes.entrySet().removeIf(entry -> !allHeroes.containsKey(entry.getKey()));
        heroes.entrySet().removeIf(entry -> !entry.getKey().getName().toLowerCase()
                .replaceAll("[ \t\\-_]+", "").matches
                        (".*" + searchText.toLowerCase().replaceAll("[ \t\\-_]+", "") + ".*"));
        for (Map.Entry<Hero, AnchorPane> entry : allHeroes.entrySet()) {
            if (!heroes.containsKey(entry.getKey()) && entry.getKey().getName().toLowerCase()
                    .replaceAll("[ \t\\-_]+", "").matches
                            (".*" + searchText.toLowerCase().replaceAll("[ \t\\-_]+", "") + ".*"))
                heroes.put(entry.getKey(), entry.getValue());
        }
        Platform.runLater(() -> {
            heroesLeftVBox.getChildren().clear();
            heroesMiddleVBox.getChildren().clear();
            heroesRightVBox.getChildren().clear();
        });
        int counter = 0;
        for (Map.Entry<Hero, AnchorPane> entry : heroes.entrySet()) {
            if (counter % 3 == 0) {
                Platform.runLater(() -> heroesLeftVBox.getChildren().add(entry.getValue()));
            }
            else if (counter % 3 == 1) {
                Platform.runLater(() -> heroesMiddleVBox.getChildren().add(entry.getValue()));
            }
            else {
                Platform.runLater(() -> heroesRightVBox.getChildren().add(entry.getValue()));
            }
            counter++;
        }
    }

    private void initializeAllHeroes() {
        for (Hero hero : CardFactory.getAllBuiltHeroes()) {
            if (Account.getActiveAccount().getCollection().getHowManyCard().containsKey(hero.getName())&&
                    Account.getActiveAccount().getCollection().getHowManyCard().get(hero.getName()) > 0 &&
                    !allHeroes.containsKey(hero)) {
                loadHero(hero);
            }
        }
        for (Iterator<Map.Entry<Hero, AnchorPane>> iterator = allHeroes.entrySet().iterator(); iterator.hasNext();) {
            Hero hero = iterator.next().getKey();
            if (!Account.getActiveAccount().getCollection().getHowManyCard().containsKey(hero.getName()) ||
                    Account.getActiveAccount().getCollection().getHowManyCard().get(hero.getName()) <= 0) {
                iterator.remove();
            }
        }
    }

    private void loadHero(Hero hero) {
        AnchorPane anchorPane = null;
        WarriorCardController warriorCardController = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("warriorCart.fxml"));
            anchorPane = scaleCard(fxmlLoader.load());
            warriorCardController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        warriorCardController.setFields(hero, "for sell");
        allHeroes.put(hero, anchorPane);
    }

    private synchronized void recalculateSpells(KeyEvent keyEvent) {
        String searchText = spellsSearchTextField.getText();
        spells.entrySet().removeIf(entry -> !allSpells.containsKey(entry.getKey()));
        spells.entrySet().removeIf(entry -> !entry.getKey().getName().toLowerCase()
                .replaceAll("[ \t\\-_]+", "").matches
                        (".*" + searchText.toLowerCase().replaceAll("[ \t\\-_]+", "") + ".*"));
        for (Map.Entry<Spell, AnchorPane> entry : allSpells.entrySet()) {
            if (!spells.containsKey(entry.getKey()) && entry.getKey().getName().toLowerCase()
                    .replaceAll("[ \t\\-_]+", "").matches
                            (".*" + searchText.toLowerCase().replaceAll("[ \t\\-_]+", "") + ".*"))
                spells.put(entry.getKey(), entry.getValue());
        }
        Platform.runLater(() -> {
            spellsLeftVBox.getChildren().clear();
            spellsMiddleVBox.getChildren().clear();
            spellsRightVBox.getChildren().clear();
        });
        int counter = 0;
        for (Map.Entry<Spell, AnchorPane> entry : spells.entrySet()) {
            if (counter % 3 == 0) {
                Platform.runLater(() -> spellsLeftVBox.getChildren().add(entry.getValue()));
            }
            else if (counter % 3 == 1) {
                Platform.runLater(() -> spellsMiddleVBox.getChildren().add(entry.getValue()));
            }
            else {
                Platform.runLater(() -> spellsRightVBox.getChildren().add(entry.getValue()));
            }
            counter++;
        }
    }

    private void initializeAllSpells() {
        for (Spell spell : CardFactory.getAllBuiltSpells()) {
            if (Account.getActiveAccount().getCollection().getHowManyCard().containsKey(spell.getName())&&
                    Account.getActiveAccount().getCollection().getHowManyCard().get(spell.getName()) > 0 &&
                    !allSpells.containsKey(spell)) {
                loadSpell(spell);
            }
        }
        for (Iterator<Map.Entry<Spell, AnchorPane>> iterator = allSpells.entrySet().iterator(); iterator.hasNext();) {
            Spell spell = iterator.next().getKey();
            if (!Account.getActiveAccount().getCollection().getHowManyCard().containsKey(spell.getName()) ||
                    Account.getActiveAccount().getCollection().getHowManyCard().get(spell.getName()) <= 0) {
                iterator.remove();
            }
        }
    }

    private void loadSpell(Spell spell) {
        AnchorPane anchorPane = null;
        SpellCardController spellCardController = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("spellCart.fxml"));
            anchorPane = scaleCard(fxmlLoader.load());
            spellCardController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        spellCardController.setFields(spell, "for sell");
        allSpells.put(spell, anchorPane);
    }

    private synchronized void recalculateItems(KeyEvent keyEvent) {
        String searchText = itemsSearchTextField.getText();
        items.entrySet().removeIf(entry -> !allItems.containsKey(entry.getKey()));
        items.entrySet().removeIf(entry -> !entry.getKey().getName().toLowerCase()
                .replaceAll("[ \t\\-_]+", "").matches
                        (".*" + searchText.toLowerCase().replaceAll("[ \t\\-_]+", "") + ".*"));
        for (Map.Entry<Spell, AnchorPane> entry : allItems.entrySet()) {
            if (!items.containsKey(entry.getKey()) && entry.getKey().getName().toLowerCase()
                    .replaceAll("[ \t\\-_]+", "").matches
                            (".*" + searchText.toLowerCase().replaceAll("[ \t\\-_]+", "") + ".*"))
                items.put(entry.getKey(), entry.getValue());
        }
        Platform.runLater(() -> {
            itemsLeftVBox.getChildren().clear();
            itemsMiddleVBox.getChildren().clear();
            itemsRightVBox.getChildren().clear();
        });
        int counter = 0;
        for (Map.Entry<Spell, AnchorPane> entry : items.entrySet()) {
            if (counter % 3 == 0) {
                Platform.runLater(() -> itemsLeftVBox.getChildren().add(entry.getValue()));
            }
            else if (counter % 3 == 1) {
                Platform.runLater(() -> itemsMiddleVBox.getChildren().add(entry.getValue()));
            }
            else {
                Platform.runLater(() -> itemsRightVBox.getChildren().add(entry.getValue()));
            }
            counter++;
        }
    }

    private void initializeAllItems() {
        for (Spell item : CardFactory.getAllBuiltItems()) {
            if (Account.getActiveAccount().getCollection().getHowManyCard().containsKey(item.getName())&&
                    Account.getActiveAccount().getCollection().getHowManyCard().get(item.getName()) > 0 &&
                    !allItems.containsKey(item)) {
                loadItem(item);
            }
        }
        for (Iterator<Map.Entry<Spell, AnchorPane>> iterator = allItems.entrySet().iterator(); iterator.hasNext();) {
            Spell item = iterator.next().getKey();
            if (!Account.getActiveAccount().getCollection().getHowManyCard().containsKey(item.getName()) ||
                    Account.getActiveAccount().getCollection().getHowManyCard().get(item.getName()) <= 0) {
                iterator.remove();
            }
        }
    }

    private void loadItem(Spell item) {
        AnchorPane anchorPane = null;
        SpellCardController spellCardController = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("spellCart.fxml"));
            anchorPane = scaleCard(fxmlLoader.load());
            spellCardController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        spellCardController.setFields(item, "for sell");
        allItems.put(item, anchorPane);
    }

    public static void sell(String cardName) {
        Card card = Card.getCardByItsName(cardName);
        Account account = Account.getActiveAccount();
        if (account.getCollection().getMainDeck() != null &&
                account.getCollection().getMainDeck().getCardIDs()
                        .stream().filter(cardID -> cardID.equals(card.getID())).count() ==
                        Collection.getCollection().getHowManyCard().get(cardName)) {
            AlertController.setAndShowAndDo
                    ("If you selling this card, yur main deck will be invalid. Are you sure about selling it?",
                            () -> doAfterSellingJobs(card));
        }
        else {
            doAfterSellingJobs(card);
        }
    }

    private static void doAfterSellingJobs(Card card) {
        Account account = Account.getActiveAccount();
        for (String deckName : account.getCollection().getDecks()) {
            Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
            if (deck.getCardIDs().stream().filter(cardID -> cardID.equals(card.getID())).count() ==
                    Collection.getCollection().getHowManyCard().get(card.getName())) {
                deck.getCardIDs().remove((Integer) card.getID());
                deck.setHero(deck.getHero() == card ? null : deck.getHero());
                deck.setItem(deck.getItem() == card ? null : deck.getItem());
                deck.minions.remove(card);//todo dose it have any problem?
                deck.spells.remove(card);
            }
        }
        account.derrick = account.derrick + card.getPrice();
        account.getCollection().getCardIDs().remove((Integer) card.getID());
        int keyValue = model.Collection.getCollection().getHowManyCard().get(card.getName());
        Collection.getCollection().getHowManyCard().put(card.getName(), keyValue - 1);
        AlertController.setAndShow("You sell the card successfully");
        CollectionOfShopController.collectionOfShopController.calculateEverything();
    }
}
