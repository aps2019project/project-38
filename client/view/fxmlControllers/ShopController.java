package view.fxmlControllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Account;
import model.cards.*;
import view.WindowChanger;
import view.fxmlControllers.cardHolder.SpellCardController;
import view.fxmlControllers.cardHolder.WarriorCardController;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import static view.Utility.scaleCard;

public class ShopController implements Initializable {
    public static ShopController shopController;
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
    public ImageView goldCircleOfCollectionButton;
    public ImageView collectionButton;
    public Text collectionText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minionsSearchTextField.setOnKeyTyped(this::recalculateMinions);
        heroesSearchTextField.setOnKeyTyped(this::recalculateHeroes);
        spellsSearchTextField.setOnKeyTyped(this::recalculateSpells);
        itemsSearchTextField.setOnKeyTyped(this::recalculateItems);
    }

    public void goToCollection() {
        CollectionOfShopController.collectionOfShopController.calculateEverything();
        WindowChanger.instance.setMainParent(LoadedScenes.collectionOfShop);
    }

    public static void buy(String cardName) {
        Card card = Card.getCardByItsName(cardName);
        Account account = Account.activeAccount;
        if (Card.checkIsItem(card)) {
            int numberOfItems = 0;
            for (int ID : account.getCollection().getCardIDs()) {
                Card card1 = Card.getAllCards().get(ID);
                if (Card.checkIsItem(card1)) numberOfItems++;
            }
            if (numberOfItems >= 3) {
                AlertController.setAndShow("You have 3 items. You couldn't buy any other item");
                return;
            }
        }
        account.setDerrick(account.getDerrick() - card.price);
        account.getCollection().getCardIDs().add(card.ID);
        if (model.Collection.getCollection().getHowManyCard().containsKey(cardName)) {
            int keyValue = model.Collection.getCollection().getHowManyCard().get(cardName);
            model.Collection.getCollection().getHowManyCard().put(card.name, keyValue + 1);
        } else {
            model.Collection.getCollection().getHowManyCard().put(card.name, 1);
        }
        AlertController.setAndShow("You bought the card successfully");
        //todo server
    }

    public void back() {
        WindowChanger.instance.setMainParent(LoadedScenes.mainMenu);
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

    //---

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

    private void initializeAllMinions() {
        for (Iterator<Map.Entry<Warrior, AnchorPane>> iterator = allMinions.entrySet().iterator(); iterator.hasNext();) {
            Warrior minion = iterator.next().getKey();
            if (!CardFactory.getAllBuiltMinionsHashMapForShop().containsKey(minion) || CardFactory.getAllBuiltMinionsHashMapForShop().get(minion) <= 0) {//todo simpler
                iterator.remove();
            }
        }
        for (Map.Entry<Warrior, Integer> minion : CardFactory.getAllBuiltMinionsHashMapForShop().entrySet()) {
            if (!allMinions.containsKey(minion.getKey()) && minion.getValue() > 0) {
                loadMinion(minion.getKey());
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
        warriorCardController.setFields(minion, "for buy");
        allMinions.put(minion, anchorPane);
    }

    //---

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

    private void initializeAllHeroes() {
        for (Iterator<Map.Entry<Hero, AnchorPane>> iterator = allHeroes.entrySet().iterator(); iterator.hasNext();) {
            Hero hero = iterator.next().getKey();
            if (!CardFactory.getAllBuiltHeroesHashMapForShop().containsKey(hero) || CardFactory.getAllBuiltHeroesHashMapForShop().get(hero) <= 0) {//todo simpler
                iterator.remove();
            }
        }
        for (Map.Entry<Hero, Integer> hero : CardFactory.getAllBuiltHeroesHashMapForShop().entrySet()) {
            if (!allHeroes.containsKey(hero.getKey()) && hero.getValue() > 0) {
                loadHero(hero.getKey());
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
        warriorCardController.setFields(hero, "for buy");
        allHeroes.put(hero, anchorPane);
    }

    //---

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

    private void initializeAllSpells() {
        for (Iterator<Map.Entry<Spell, AnchorPane>> iterator = allSpells.entrySet().iterator(); iterator.hasNext();) {
            Spell spell = iterator.next().getKey();
            if (!CardFactory.getAllBuiltSpellsHashMapForShop().containsKey(spell) || CardFactory.getAllBuiltSpellsHashMapForShop().get(spell) <= 0) {//todo simpler
                iterator.remove();
            }
        }
        for (Map.Entry<Spell, Integer> spell : CardFactory.getAllBuiltSpellsHashMapForShop().entrySet()) {
            if (!allSpells.containsKey(spell.getKey()) && spell.getValue() > 0) {
                loadSpell(spell.getKey());
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
        spellCardController.setFields(spell, "for buy");
        allSpells.put(spell, anchorPane);
    }

    //---

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

    private void initializeAllItems() {
        for (Iterator<Map.Entry<Spell, AnchorPane>> iterator = allItems.entrySet().iterator(); iterator.hasNext();) {
            Spell item = iterator.next().getKey();
            if (!CardFactory.getAllBuiltItemsHashMapForShop().containsKey(item) || CardFactory.getAllBuiltItemsHashMapForShop().get(item) <= 0) {//todo simpler
                iterator.remove();
            }
        }
        for (Map.Entry<Spell, Integer> item : CardFactory.getAllBuiltItemsHashMapForShop().entrySet()) {
            if (!allItems.containsKey(item.getKey()) && item.getValue() > 0) {
                loadItem(item.getKey());
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
        spellCardController.setFields(item, "for buy");
        allItems.put(item, anchorPane);
    }

    //----------------------------

    public void shineCollectionBottom() {
        collectionButton.setEffect(new Glow(0.5));
        goldCircleOfCollectionButton.setOpacity(1);
        collectionText.setOpacity(1);
    }

    public void resetCollectionBottom() {
        collectionButton.setEffect(null);
        goldCircleOfCollectionButton.setOpacity(0.6);
        collectionText.setOpacity(0.6);
    }

    public void shineBackBottom() {
        backButton.setEffect(new Glow(0.5));
    }

    public void resetBackBottom() {
        backButton.setEffect(null);
    }

}

//todo if server updated, we should update here too.