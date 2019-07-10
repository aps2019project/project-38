package view.fxmlControllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import model.Account;
import model.Collection;
import model.Deck;
import model.cards.Spell;
import view.Utility;
import view.fxmls.LoadedScenes;
import view.visualEntities.VisualSpell;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpellCardController extends CardControllerForAuction {
    public ImageView blueLine;
    public ImageView cardImage;
    public AnchorPane gifPane;
    public Text nameText;
    public Text typeText;
    public ImageView purpleDot;
    public ImageView pricePlace;
    public Text priceText;
    public Text descriptionText;
    public ImageView blueDiamond;
    public Text manaText;
    private String type;
    private Deck deck;

    public void selectCard(MouseEvent mouseEvent) {
        switch (type) {
            case "for auction inside":
                break;
            case "for auction":
                enterAuction();
                break;
            case "for sell":
                AlertController.setAndShowAndDo(
                        String.format("Do you want to sell %s? Price: %s (You have %d number of this card) Your Derrick: %d",
                                nameText.getText(), priceText.getText(),
                                Account.activeAccount.getCollection().getHowManyCard().get(nameText.getText()),
                                Account.activeAccount.getDerrick()), () -> {
                            AlertController.setAndShowAndDoOrNot("Do you want to sell it in auction or selling normally",
                                    () -> CollectionOfShopController.sell(nameText.getText(), true),
                                    () -> CollectionOfShopController.sell(nameText.getText(), false));
                        });
                break;
            case "for buy":
                if (Account.activeAccount.getDerrick() >= Integer.parseInt(priceText.getText())) {
                    AlertController.setAndShowAndDo(
                            String.format("Do you want to buy %s? Price: %s (You have %d number of this card) Your Derrick: %d)",
                                    nameText.getText(), priceText.getText(),
                                    Account.activeAccount.getCollection().getHowManyCard().get(nameText.getText()),
                                    Account.activeAccount.getDerrick()), () -> ShopController.buy(nameText.getText()));
                } else {
                    AlertController.setAndShow("You have not enough Derrick");
                }
                break;
            case "inside deck": {
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
                break;
            }
            default: {
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
                break;
            }
        }
    }

    public void shineCard(MouseEvent mouseEvent) {
        blueLine.setOpacity(1);
        cardImage.setEffect(new Glow(0.3));
        blueDiamond.setEffect(new Glow(0.3));
        purpleDot.setEffect(new Glow(0.3));
        pricePlace.setEffect(new Glow(0.3));
        gifPane.setEffect(new Glow(0.3));
    }

    public void resetCard(MouseEvent mouseEvent) {
        blueLine.setOpacity(0);
        cardImage.setEffect(null);
        blueDiamond.setEffect(null);
        purpleDot.setEffect(null);
        pricePlace.setEffect(null);
        gifPane.setEffect(null);
    }

    public void setFields(Spell spell, String type) {
        VisualSpell vs = new VisualSpell(spell.name);
        double widthScale = gifPane.getPrefWidth() / vs.animation.width;
        double heightScale = gifPane.getPrefHeight() / vs.animation.height;
        Scale scale = new Scale(widthScale, heightScale, 0, 0);
        vs.view.getTransforms().add(scale);
        gifPane.getChildren().add(vs.view);
        if (type.matches("for auction(| inside)")) {
            priceText.setText("Base Price: " + spell.price / 2);
        }
        else priceText.setText(String.valueOf(spell.price));
        if (!Spell.checkIsItem(spell)) manaText.setText(String.valueOf(spell.requiredMana));
        nameText.setText(spell.name);
        typeText.setText(String.format("%s", Spell.checkIsItem(spell) ? "Item" : "Spell"));
        descriptionText.setText(spell.description.descriptionOfCardSpecialAbility);
        Matcher matcher = Pattern.compile("(?<type>(inside|outside) deck) (?<deckName>.+)").matcher(type);
        if (matcher.matches()) {
            this.type = matcher.group("type");
            deck = Collection.getCollection().getAllDecks().get(matcher.group("deckName"));
        }
        else if (type.equals("for auction")) {
            this.type = type;
            FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("Auction.fxml"));
            try {
                initializeForAuctionType((AnchorPane) Utility.scale(fxmlLoader.load()), fxmlLoader.getController());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else this.type = type;
    }

    @Override
    public void setMaxProposedPrice(int maxProposedPrice, String usernameOfAccountOfMaxProposedPrice) {
        Platform.runLater(() -> priceText.setText(usernameOfAccountOfMaxProposedPrice + ": " + maxProposedPrice));
    }
}
