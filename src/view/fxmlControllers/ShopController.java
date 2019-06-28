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
import model.cards.*;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShopController implements Initializable {
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
    public ImageView goldCircleOfCollectionButton;
    public ImageView collectionButton;
    public Text collectionText;

    public void moveScrollPane(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("Up")) {
            //todo
        } else if (keyEvent.getCode().getName().equals("Down")) {
            //todo
        }
    }

    public void back(MouseEvent mouseEvent) {
        WindowChanger.instance.setNewScene(LoadedScenes.mainMenu);
    }

    public void shineBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(new Glow(0.5));
    }

    public void resetBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(null);
    }

    public void goToCollection(MouseEvent mouseEvent) {
        WindowChanger.instance.setNewScene(LoadedScenes.collectionOfShop);
    }

    public void shineCollectionBottom(MouseEvent mouseEvent) {
        collectionButton.setEffect(new Glow(0.5));
        goldCircleOfCollectionButton.setOpacity(0.9);
        collectionText.setOpacity(1);
    }

    public void resetCollectionBottom(MouseEvent mouseEvent) {
        collectionButton.setEffect(null);
        goldCircleOfCollectionButton.setOpacity(0.6);
        collectionText.setOpacity(0.6);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minionsSearchTextField.setOnKeyTyped(this::recalculateMinions);
//        heroesSearchTextField.setOnKeyTyped(this::recalculateHeroes);
//        spellsSearchTextField.setOnKeyTyped(this::recalculateSpells);
//        itemsSearchTextField.setOnKeyTyped(this::recalculateItems);
        Platform.runLater(() -> {
            recalculateMinions(null);
//            recalculateHeroes(null);
//            recalculateSpells(null);
//            recalculateItems(null);
        });
    }

    private synchronized void recalculateMinions(KeyEvent ignored) {
        String searchText = minionsSearchTextField.getText();
        minions.entrySet().removeIf(entry -> !entry.getKey().getName().matches(searchText + ".*"));
        for (Warrior minion : CardFactory.getAllBuiltMinions()) {
            if (!minions.containsKey(minion) && minion.getName().matches(searchText + ".*")) addNewMinion(minion);
        }
        Platform.runLater(() -> {
            minionsLeftVBox.getChildren().removeAll(minionsLeftVBox.getChildren());
            minionsMiddleVBox.getChildren().removeAll(minionsLeftVBox.getChildren());
            minionsRightVBox.getChildren().removeAll(minionsLeftVBox.getChildren());
        });
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Map.Entry<Warrior, AnchorPane> entry : minions.entrySet()) {
            if (minionsLeftVBox.getChildren().size() <= minionsMiddleVBox.getChildren().size() &&
                    minionsLeftVBox.getChildren().size() <= minionsRightVBox.getChildren().size())
                Platform.runLater(() -> minionsLeftVBox.getChildren().add(entry.getValue()));
            else if (minionsMiddleVBox.getChildren().size() <= minionsLeftVBox.getChildren().size() &&
                    minionsMiddleVBox.getChildren().size() <= minionsRightVBox.getChildren().size())
                Platform.runLater(() -> minionsMiddleVBox.getChildren().add(entry.getValue()));
            else Platform.runLater(() -> minionsRightVBox.getChildren().add(entry.getValue()));
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            if (!heroes.containsKey(hero) && hero.getName().matches(searchText + ".*")) addNewHero(hero);
        }
        Platform.runLater(() -> {
            heroesLeftVBox.getChildren().removeAll();
            heroesRightVBox.getChildren().removeAll();
        });
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            if (!spells.containsKey(spell) && spell.getName().matches(searchText + ".*")) addNewSpell(spell);
        }
        Platform.runLater(() -> {
            spellsLeftVBox.getChildren().removeAll();
            spellsRightVBox.getChildren().removeAll();
        });
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            if (item.getPrice() != 0 && !items.containsKey(item) && item.getName().matches(searchText + ".*"))
                addNewItem(item);
        }
        Platform.runLater(() -> {
            itemsLeftVBox.getChildren().removeAll();
            itemsRightVBox.getChildren().removeAll();
        });
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
