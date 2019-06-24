package view.fxmlControllers;

import controller.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Account;
import model.cards.CardFactory;
import model.cards.Hero;
import model.cards.Spell;
import model.cards.Warrior;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CollectionOfShopController implements Initializable {
    public VBox minionsLeftVBox;
    public VBox minionsMiddleVBox;
    public VBox minionsRightVBox;
    public TextField minionsSearchTextField;
    private HashMap<Warrior, AnchorPane> minions = new HashMap<>();
    public VBox heroesLeftVBox;
    public VBox heroesMiddleVBox;
    public VBox heroesRightVBox;
    public TextField heroesSearchTextField;
    private HashMap<Hero, AnchorPane> heroes = new HashMap<>();
    public VBox spellsLeftVBox;
    public VBox spellsMiddleVBox;
    public VBox spellsRightVBox;
    public TextField spellsSearchTextField;
    private HashMap<Spell, AnchorPane> spells = new HashMap<>();
    public VBox itemsLeftVBox;
    public VBox itemsMiddleVBox;
    public VBox itemsRightVBox;
    public TextField itemsSearchTextField;
    private HashMap<Spell, AnchorPane> items = new HashMap<>();
    public ImageView backButton;

    public void moveScrollPane(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("Up")) {
            //todo
        } else if (keyEvent.getCode().getName().equals("Down")) {
            //todo
        }
    }

    public void back(MouseEvent mouseEvent) {
        Main.mainStage.setScene(LoadedScenes.shop);
        Main.mainStage.setFullScreen(true);
    }

    public void shineBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(new Glow());
    }

    public void resetBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        minionsSearchTextField.setOnKeyTyped(this::recalculateMinions);
//        heroesSearchTextField.setOnKeyTyped(this::recalculateHeroes);
//        spellsSearchTextField.setOnKeyTyped(this::recalculateSpells);
//        itemsSearchTextField.setOnKeyTyped(this::recalculateItems);
        Platform.runLater(() -> {
//            recalculateMinions(null);//todo active account is null at first
//            recalculateHeroes(null);
//            recalculateSpells(null);
//            recalculateItems(null);
        });
    }

    private synchronized void recalculateMinions(KeyEvent ignored) {
        String searchText = minionsSearchTextField.getText();
        minions.entrySet().removeIf(entry -> !entry.getKey().getName().matches(searchText + ".*"));
        for (Warrior minion : CardFactory.getAllBuiltMinions()) {
            if (Account.getActiveAccount().getCollection().getCardIDs().contains(minion.getID()) &&
                    !minions.containsKey(minion) && minion.getName().matches(searchText + ".*")) {
                long numberOfTheMinion = Account.getActiveAccount().getCollection().getCardIDs()
                        .stream().filter(id -> id == minion.getID()).count();
                for (int i = 0; i < numberOfTheMinion; i++) {
                    addNewMinion(minion);
                }
            }
        }
        minionsLeftVBox.getChildren().removeAll();
        minionsRightVBox.getChildren().removeAll();
        for (Map.Entry<Warrior, AnchorPane> entry : minions.entrySet()) {
            if (minionsLeftVBox.getChildren().size() == minionsRightVBox.getChildren().size())
                minionsLeftVBox.getChildren().add(entry.getValue());
            else minionsRightVBox.getChildren().add(entry.getValue());
        }
    }

    private void addNewMinion(Warrior minion) {
        AnchorPane anchorPane = null;
        WarriorCardController warriorCardController = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("warriorCart.fxml"));
            anchorPane = fxmlLoader.load();
            warriorCardController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        warriorCardController.setFields(minion);
        minions.put(minion, anchorPane);
    }

    private synchronized void recalculateHeroes(KeyEvent ignored) {
        String searchText = heroesSearchTextField.getText();
        heroes.entrySet().removeIf(entry -> !entry.getKey().getName().matches(searchText + ".*"));
        for (Hero hero : CardFactory.getAllBuiltHeroes()) {
            if (Account.getActiveAccount().getCollection().getCardIDs().contains(hero.getID()) &&
                    !heroes.containsKey(hero) && hero.getName().matches(searchText + ".*")) {
                long numberOfTheHero = Account.getActiveAccount().getCollection().getCardIDs()
                        .stream().filter(id -> id == hero.getID()).count();
                for (int i = 0; i < numberOfTheHero; i++) {
                    addNewHero(hero);
                }
            }
        }
        heroesLeftVBox.getChildren().removeAll();
        heroesRightVBox.getChildren().removeAll();
        for (Map.Entry<Hero, AnchorPane> entry : heroes.entrySet()) {
            if (heroesLeftVBox.getChildren().size() == heroesRightVBox.getChildren().size())
                heroesLeftVBox.getChildren().add(entry.getValue());
            else heroesRightVBox.getChildren().add(entry.getValue());
        }
    }

    private void addNewHero(Hero hero) {
        AnchorPane anchorPane = null;
        WarriorCardController warriorCardController = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("warriorCart.fxml"));
            anchorPane = fxmlLoader.load();
            warriorCardController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        warriorCardController.setFields(hero);
        heroes.put(hero, anchorPane);
    }

    private synchronized void recalculateSpells(KeyEvent ignored) {
        String searchText = spellsSearchTextField.getText();
        spells.entrySet().removeIf(entry -> !entry.getKey().getName().matches(searchText + ".*"));
        for (Spell spell : CardFactory.getAllBuiltSpells()) {
            if (Account.getActiveAccount().getCollection().getCardIDs().contains(spell.getID()) &&
                    !spells.containsKey(spell) && spell.getName().matches(searchText + ".*")) {
                long numberOfTheSpell = Account.getActiveAccount().getCollection().getCardIDs()
                        .stream().filter(id -> id == spell.getID()).count();
                for (int i = 0; i < numberOfTheSpell; i++) {
                    addNewSpell(spell);
                }
            }
        }
        spellsLeftVBox.getChildren().removeAll();
        spellsRightVBox.getChildren().removeAll();
        for (Map.Entry<Spell, AnchorPane> entry : spells.entrySet()) {
            if (spellsLeftVBox.getChildren().size() == spellsRightVBox.getChildren().size())
                spellsLeftVBox.getChildren().add(entry.getValue());
            else spellsRightVBox.getChildren().add(entry.getValue());
        }
    }

    private void addNewSpell(Spell spell) {
        AnchorPane anchorPane = null;
        SpellCardController spellCardController = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("spellCart.fxml"));
            anchorPane = fxmlLoader.load();
            spellCardController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        spellCardController.setFields(spell);
        spells.put(spell, anchorPane);
    }

    private synchronized void recalculateItems(KeyEvent ignored) {
        String searchText = itemsSearchTextField.getText();
        items.entrySet().removeIf(entry -> !entry.getKey().getName().matches(searchText + ".*"));
        for (Spell item : CardFactory.getAllBuiltItems()) {
            if (Account.getActiveAccount().getCollection().getCardIDs().contains(item.getID()) &&
                    item.getPrice() != 0 && !items.containsKey(item) && item.getName().matches(searchText + ".*")) {
                long numberOfTheItem = Account.getActiveAccount().getCollection().getCardIDs()
                        .stream().filter(id -> id == item.getID()).count();
                for (int i = 0; i < numberOfTheItem; i++) {
                    addNewItem(item);
                }
            }
        }
        itemsLeftVBox.getChildren().removeAll();
        itemsRightVBox.getChildren().removeAll();
        for (Map.Entry<Spell, AnchorPane> entry : items.entrySet()) {
            if (itemsLeftVBox.getChildren().size() == itemsRightVBox.getChildren().size())
                itemsLeftVBox.getChildren().add(entry.getValue());
            else itemsRightVBox.getChildren().add(entry.getValue());
        }
    }

    private void addNewItem(Spell item) {
        AnchorPane anchorPane = null;
        SpellCardController spellCardController = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("spellCart.fxml"));
            anchorPane = fxmlLoader.load();
            spellCardController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        spellCardController.setFields(item);
        items.put(item, anchorPane);
    }
}
