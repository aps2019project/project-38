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
import javafx.scene.text.Text;
import model.Collection;
import model.Deck;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Spell;
import model.cards.Warrior;
import view.WindowChanger;
import view.fxmlControllers.cardHolder.SpellCardController;
import view.fxmlControllers.cardHolder.WarriorCardController;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static model.cards.Card.getCardByItsName;
import static view.Utility.scaleCard;

public class RemovingDeckCardsController implements Initializable {
    public static RemovingDeckCardsController removingDeckCardsController;
    public VBox minionsLeftVBox;
    public VBox minionsMiddleVBox;
    public VBox minionsRightVBox;
    public TextField minionsSearchTextField;
    private static HashMap<Warrior, AnchorPane> minions = new HashMap<>(), allMinions = new HashMap<>();
    public VBox heroesLeftVBox;
    public VBox heroesMiddleVBox;
    public VBox heroesRightVBox;
    public TextField heroesSearchTextField;
    private static HashMap<Hero, AnchorPane> heroes = new HashMap<>(), allHeroes = new HashMap<>();
    public VBox spellsLeftVBox;
    public VBox spellsMiddleVBox;
    public VBox spellsRightVBox;
    public TextField spellsSearchTextField;
    private static HashMap<Spell, AnchorPane> spells = new HashMap<>(), allSpells = new HashMap<>();
    public VBox itemsLeftVBox;
    public VBox itemsMiddleVBox;
    public VBox itemsRightVBox;
    public TextField itemsSearchTextField;
    private static HashMap<Spell, AnchorPane> items = new HashMap<>(), allItems = new HashMap<>();
    public HashMap<String, Integer> selectedCardsNameToNumberHashMap = new HashMap<>();
    public ImageView backButton;
    public ImageView goldCircleOfSetAsMainDeckButton;
    public ImageView setAsMainDeckButton;
    public Text setAsMainDeckText;
    private Deck deck;

    public void back(MouseEvent mouseEvent) {
        ChoosingDeckCardsController.choosingDeckCardsController.calculateEveryThing(deck);
        WindowChanger.instance.setMainParent(LoadedScenes.choosingDeckCards);
    }

    public void shineBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(new Glow(1));
    }

    public void resetBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(null);
    }

    public void setThisDeckAsMainDeck(MouseEvent mouseEvent) {
        Collection.getCollection().selectMainDeck(deck.getName());
    }

    public void shineSetAsMainDeckButton(MouseEvent mouseEvent) {
        setAsMainDeckButton.setEffect(new Glow(1));
        goldCircleOfSetAsMainDeckButton.setOpacity(1);
        setAsMainDeckText.setOpacity(1);
    }

    public void resetSetAsMainDeckButton(MouseEvent mouseEvent) {
        setAsMainDeckButton.setEffect(new Glow(0));
        goldCircleOfSetAsMainDeckButton.setOpacity(0.6);
        setAsMainDeckText.setOpacity(0.6);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        minionsSearchTextField.setOnKeyTyped(this::recalculateMinions);
        heroesSearchTextField.setOnKeyTyped(this::recalculateHeroes);
        spellsSearchTextField.setOnKeyTyped(this::recalculateSpells);
        itemsSearchTextField.setOnKeyTyped(this::recalculateItems);
    }

    public void calculateEveryThing(Deck deck) {
        this.deck = deck;
        selectedCardsNameToNumberHashMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : Collection.getCollection().getHowManyCard().entrySet()) {
            int numberOfCardInDeck = (int) deck.getCardIDs().stream().filter(cardID -> cardID.equals(Card.getIDByName(entry.getKey()))).count();
            selectedCardsNameToNumberHashMap.put(entry.getKey(), numberOfCardInDeck);
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
        allMinions.entrySet().removeIf(entry -> !selectedCardsNameToNumberHashMap.containsKey(entry.getKey().name) ||
                selectedCardsNameToNumberHashMap.get(entry.getKey().name) <= 0);
        for (Map.Entry<String, Integer> entry : selectedCardsNameToNumberHashMap.entrySet()) {
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
        warriorCardController.setFields(minion, "inside deck " + deck.getName());
        allMinions.put(minion, anchorPane);
    }

    private synchronized void recalculateMinions(KeyEvent keyEvent) {
        String searchText = minionsSearchTextField.getText();
        minions.entrySet().removeIf(entry -> !allMinions.containsKey(entry.getKey()));
        minions.entrySet().removeIf(entry -> !entry.getKey().name.toLowerCase()
                .replaceAll("[ \t\\-_]+", "").matches
                        (".*" + searchText.toLowerCase().replaceAll("[ \t\\-_]+", "") + ".*"));
        for (Map.Entry<Warrior, AnchorPane> entry : allMinions.entrySet()) {
            if (!minions.containsKey(entry.getKey()) && entry.getKey().name.toLowerCase()
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
        allHeroes.entrySet().removeIf(entry -> !selectedCardsNameToNumberHashMap.containsKey(entry.getKey().name) ||
                selectedCardsNameToNumberHashMap.get(entry.getKey().name) <= 0);
        for (Map.Entry<String, Integer> entry : selectedCardsNameToNumberHashMap.entrySet()) {
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
        warriorCardController.setFields(hero, "inside deck " + deck.getName());
        allHeroes.put(hero, anchorPane);
    }

    private synchronized void recalculateHeroes(KeyEvent keyEvent) {
        String searchText = heroesSearchTextField.getText();
        heroes.entrySet().removeIf(entry -> !allHeroes.containsKey(entry.getKey()));
        heroes.entrySet().removeIf(entry -> !entry.getKey().name.toLowerCase()
                .replaceAll("[ \t\\-_]+", "").matches
                        (".*" + searchText.toLowerCase().replaceAll("[ \t\\-_]+", "") + ".*"));
        for (Map.Entry<Hero, AnchorPane> entry : allHeroes.entrySet()) {
            if (!heroes.containsKey(entry.getKey()) && entry.getKey().name.toLowerCase()
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
        allSpells.entrySet().removeIf(entry -> !selectedCardsNameToNumberHashMap.containsKey(entry.getKey().name) ||
                selectedCardsNameToNumberHashMap.get(entry.getKey().name) <= 0);
        for (Map.Entry<String, Integer> entry : selectedCardsNameToNumberHashMap.entrySet()) {
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
        spellCardController.setFields(spell, "inside deck " + deck.getName());
        allSpells.put(spell, anchorPane);
    }

    private synchronized void recalculateSpells(KeyEvent keyEvent) {
        String searchText = spellsSearchTextField.getText();
        spells.entrySet().removeIf(entry -> !allSpells.containsKey(entry.getKey()));
        spells.entrySet().removeIf(entry -> !entry.getKey().name.toLowerCase()
                .replaceAll("[ \t\\-_]+", "").matches
                        (".*" + searchText.toLowerCase().replaceAll("[ \t\\-_]+", "") + ".*"));
        for (Map.Entry<Spell, AnchorPane> entry : allSpells.entrySet()) {
            if (!spells.containsKey(entry.getKey()) && entry.getKey().name.toLowerCase()
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
        allItems.entrySet().removeIf(entry -> !selectedCardsNameToNumberHashMap.containsKey(entry.getKey().name) ||
                selectedCardsNameToNumberHashMap.get(entry.getKey().name) <= 0);
        for (Map.Entry<String, Integer> entry : selectedCardsNameToNumberHashMap.entrySet()) {
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
        spellCardController.setFields(item, "inside deck " + deck.getName());
        allItems.put(item, anchorPane);
    }

    private synchronized void recalculateItems(KeyEvent keyEvent) {
        String searchText = itemsSearchTextField.getText();
        items.entrySet().removeIf(entry -> !allItems.containsKey(entry.getKey()));
        items.entrySet().removeIf(entry -> !entry.getKey().name.toLowerCase()
                .replaceAll("[ \t\\-_]+", "").matches
                        (".*" + searchText.toLowerCase().replaceAll("[ \t\\-_]+", "") + ".*"));
        for (Map.Entry<Spell, AnchorPane> entry : allItems.entrySet()) {
            if (!items.containsKey(entry.getKey()) && entry.getKey().name.toLowerCase()
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
