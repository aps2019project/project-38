package view.fxmlControllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Collection;
import model.Deck;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Spell;
import model.cards.Warrior;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static model.cards.Card.getCardByItsName;
import static view.Utility.scaleCard;

public class ChoosingDeckCardsController {
    public static ChoosingDeckCardsController choosingDeckCardsController;
    public static AnchorPane choosingDeckCardsAnchorPane;
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
    public ImageView goldCircleOfDeckButton;
    public ImageView deckButton;
    public Text deckButtonText;
    HashMap<String, Integer> notSelectedCardsNameToNumberHashMap;
    private Deck deck;

    static {
        FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("ChoosingDeckCards.fxml"));
        try {
            choosingDeckCardsAnchorPane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        choosingDeckCardsController = fxmlLoader.getController();
        choosingDeckCardsController.initialize();
    }

    public void back(MouseEvent mouseEvent) {
        CollectionController.collectionController.calculateEveryThing();
        WindowChanger.instance.setNewScene(LoadedScenes.collection);
    }

    public void shineBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(new Glow(1));
    }

    public void resetBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(null);
    }

    public void goToDeck(MouseEvent mouseEvent) {
        RemovingDeckCardsController.removingDeckCardsController.calculateEveryThing(deck);
        WindowChanger.instance.setNewScene(LoadedScenes.removingDeckCards);
    }

    public void shineDeckBottom(MouseEvent mouseEvent) {
        deckButton.setEffect(new Glow(1));
        goldCircleOfDeckButton.setOpacity(1);
        deckButtonText.setOpacity(1);
    }

    public void resetDeckBottom(MouseEvent mouseEvent) {
        deckButton.setEffect(null);
        goldCircleOfDeckButton.setOpacity(0.6);
        deckButtonText.setOpacity(0.6);
    }

    public void initialize() {
        minionsSearchTextField.setOnKeyTyped(choosingDeckCardsController::recalculateMinions);
        heroesSearchTextField.setOnKeyTyped(choosingDeckCardsController::recalculateHeroes);
        spellsSearchTextField.setOnKeyTyped(choosingDeckCardsController::recalculateSpells);
        itemsSearchTextField.setOnKeyTyped(choosingDeckCardsController::recalculateItems);
    }

    public void calculateEveryThing(Deck deck) {
        this.deck = deck;
        notSelectedCardsNameToNumberHashMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : Collection.getCollection().getHowManyCard().entrySet()) {
            int numberOfCardInDeck = (int) deck.getCardIDs().stream().filter(cardID -> cardID.equals(entry.getValue())).count();
            notSelectedCardsNameToNumberHashMap.put(entry.getKey(), entry.getValue() - numberOfCardInDeck);
        }
        initializeAllMinions();
        recalculateMinions(null);
        initializeAllHeroes();
        recalculateHeroes(null);
        initializeAllSpells();
        recalculateSpells(null);
        initializeAllItems();
        recalculateItems(null);
    }

    private void initializeAllMinions() {
        allMinions.entrySet().removeIf(entry -> !notSelectedCardsNameToNumberHashMap.containsKey(entry.getKey().getName()) ||
                notSelectedCardsNameToNumberHashMap.get(entry.getKey().getName()) <= 0);
        for (Map.Entry<String, Integer> entry : notSelectedCardsNameToNumberHashMap.entrySet()) {
            if (entry.getValue() > 0) {
                Card card = getCardByItsName(entry.getKey());
                if (card.getClass().equals(Warrior.class)) {
                    Warrior minion = (Warrior) card;
                    if (!allMinions.containsKey(minion)) {
                        loadMinion(minion);
                    }
                }
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
        warriorCardController.setFields(minion, "outside deck " + deck.getName());//todo
        allMinions.put(minion, anchorPane);
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

    private void initializeAllHeroes() {
        allHeroes.entrySet().removeIf(entry -> !notSelectedCardsNameToNumberHashMap.containsKey(entry.getKey().getName()) ||
                notSelectedCardsNameToNumberHashMap.get(entry.getKey().getName()) <= 0);
        for (Map.Entry<String, Integer> entry : notSelectedCardsNameToNumberHashMap.entrySet()) {
            if (entry.getValue() > 0) {
                Card card = getCardByItsName(entry.getKey());
                if (card instanceof Hero) {
                    Hero hero = (Hero) card;
                    if (!allHeroes.containsKey(hero)) {
                        loadHero(hero);
                    }
                }
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
        warriorCardController.setFields(hero, "outside deck " + deck.getName());
        allHeroes.put(hero, anchorPane);
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

    private void initializeAllSpells() {
        allSpells.entrySet().removeIf(entry -> !notSelectedCardsNameToNumberHashMap.containsKey(entry.getKey().getName()) ||
                notSelectedCardsNameToNumberHashMap.get(entry.getKey().getName()) <= 0);
        for (Map.Entry<String, Integer> entry : notSelectedCardsNameToNumberHashMap.entrySet()) {
            if (entry.getValue() > 0) {
                Card card = getCardByItsName(entry.getKey());
                if (card instanceof Spell && !Spell.checkIsItem(card)) {
                    Spell spell = (Spell) card;
                    if (!allSpells.containsKey(spell)) {
                        loadSpell(spell);
                    }
                }
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
        spellCardController.setFields(spell, "outside deck " + deck.getName());
        allSpells.put(spell, anchorPane);
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

    private void initializeAllItems() {
        allItems.entrySet().removeIf(entry -> !notSelectedCardsNameToNumberHashMap.containsKey(entry.getKey().getName()) ||
                notSelectedCardsNameToNumberHashMap.get(entry.getKey().getName()) <= 0);
        for (Map.Entry<String, Integer> entry : notSelectedCardsNameToNumberHashMap.entrySet()) {
            if (entry.getValue() > 0) {
                Card card = getCardByItsName(entry.getKey());
                if (Spell.checkIsItem(card)) {
                    Spell item = (Spell) card;
                    if (!allItems.containsKey(item)) {
                        loadItem(item);
                    }
                }
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
        spellCardController.setFields(item, "outside deck " + deck.getName());
        allItems.put(item, anchorPane);
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
}