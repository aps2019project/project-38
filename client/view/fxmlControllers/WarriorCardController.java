package view.fxmlControllers;

import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import model.Account;
import model.Collection;
import model.Deck;
import model.cards.Hero;
import model.cards.Warrior;
import view.visualentities.VisualMinion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WarriorCardController {
    public ImageView blueLine;
    public ImageView cardImage;
    public Text apText;
    public Text hpText;
    public AnchorPane gifPane;
    public ImageView pricePlace;
    public Text priceText;
    public ImageView blueDot;
    public ImageView blueDiamond;
    public Text manaText;
    public Text nameText;
    public Text typeText;
    public Text descriptionText;
    private String type;
    private Deck deck;

    public void selectCard(MouseEvent mouseEvent) {
        if (type.equals("for sell")) {
            AlertController.setAndShowAndDo(
                    String.format("Do you want to sell %s? Price: %s (You have %d number of this card) Your Derrick: %d",
                            nameText.getText(), priceText.getText(),
                            Account.getActiveAccount().getCollection().getHowManyCard().get(nameText.getText()),
                            Account.getActiveAccount().derrick), () -> CollectionOfShopController.sell(nameText.getText()));
        }
        else if (type.equals("for buy")){
            if (Account.getActiveAccount().derrick >= Integer.parseInt(priceText.getText())) {
                AlertController.setAndShowAndDo(
                        String.format("Do you want to buy %s? Price: %s (You have %d number of this card) Your Derrick: %d)",
                                nameText.getText(), priceText.getText(),
                                Account.getActiveAccount().getCollection().getHowManyCard().get(nameText.getText()),
                                Account.getActiveAccount().derrick), () -> ShopController.buy(nameText.getText()));
            }
            else {
                AlertController.setAndShow("You have not enough Derrick");
            }
        }
        else if (type.equals("inside deck")) {
            int numberInside = RemovingDeckCardsController.removingDeckCardsController
                    .selectedCardsNameToNumberHashMap.get(nameText.getText()) == null ? 0 :
                    RemovingDeckCardsController.removingDeckCardsController
                            .selectedCardsNameToNumberHashMap.get(nameText.getText());
            int numberOutside = Collection.getCollection().getHowManyCard().get(nameText.getText()) - numberInside;
            AlertController.setAndShowAndDo(String.format
                    ("Do you want to remove %s from %s? (You have %d of this inside %d of this outside this deck)",
                            nameText.getText(), deck.getName(), numberInside, numberOutside), () -> {
                if (Collection.getCollection().removeCardFromDeck(nameText.getText(), deck.getName()))
                    RemovingDeckCardsController.removingDeckCardsController.calculateEveryThing(deck);
            });
        }
        else {
            int numberOutside = ChoosingDeckCardsController.choosingDeckCardsController
                    .notSelectedCardsNameToNumberHashMap.get(nameText.getText()) == null ? 0 :
                    ChoosingDeckCardsController.choosingDeckCardsController
                            .notSelectedCardsNameToNumberHashMap.get(nameText.getText());
            int numberInside = Collection.getCollection().getHowManyCard().get(nameText.getText()) - numberOutside;
            AlertController.setAndShowAndDo(String.format
                    ("Do you want to add %s to %s? (You have %d of this inside %d of this outside this deck)",
                            nameText.getText(), deck.getName(), numberInside, numberOutside), () -> {
                if (Collection.getCollection().addCardToDeck(nameText.getText(), deck.getName()))
                    ChoosingDeckCardsController.choosingDeckCardsController.calculateEveryThing(deck);
            });
        }
    }

    public void shineCard(MouseEvent mouseEvent) {
        blueLine.setOpacity(1);
        cardImage.setEffect(new Glow(0.3));
        blueDiamond.setEffect(new Glow(0.3));
        blueDot.setEffect(new Glow(0.3));
        pricePlace.setEffect(new Glow(0.3));
        gifPane.setEffect(new Glow(0.3));
    }

    public void resetCard(MouseEvent mouseEvent) {
        blueLine.setOpacity(0);
        cardImage.setEffect(null);
        blueDiamond.setEffect(null);
        blueDot.setEffect(null);
        pricePlace.setEffect(null);
        gifPane.setEffect(null);
    }

    public void setFields(Warrior warrior, String type) {
        apText.setText(String.valueOf(warrior.getAp()));
        hpText.setText(String.valueOf(warrior.getHp()));
        VisualMinion vm = new VisualMinion(warrior.getName());
        double widthScale = gifPane.getPrefWidth() / vm.animation.width;
        double heightScale = gifPane.getPrefHeight() / vm.animation.height;
        Scale scale = new Scale(widthScale, heightScale, 0, 0);
        vm.view.getTransforms().add(scale);
        gifPane.getChildren().add(vm.view);
        priceText.setText(String.valueOf(warrior.getPrice()));
        if (!(warrior instanceof Hero)) manaText.setText(String.valueOf(warrior.getRequiredMana()));
        nameText.setText(warrior.getName());
        typeText.setText(String.format("%s (%s)", warrior instanceof Hero ? "Hero" : "Minion", warrior.getWarriorType()));
        descriptionText.setText(warrior.description.descriptionOfCardSpecialAbility);//todo
        Matcher matcher = Pattern.compile("(?<type>(inside|outside) deck) (?<deckName>.+)").matcher(type);
        if (matcher.matches()) {
            this.type = matcher.group("type");
            deck = Collection.getCollection().getAllDecks().get(matcher.group("deckName"));
        }
        else this.type = type;
    }
}
