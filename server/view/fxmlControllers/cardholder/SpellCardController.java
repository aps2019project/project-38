package view.fxmlControllers.cardholder;

import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import model.Shop;
import model.cards.Card;
import model.cards.Spell;
import view.fxmlControllers.AlertController;
import view.visualentities.VisualSpell;

public class SpellCardController {
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

    public void selectCard(MouseEvent mouseEvent) {
        Card card = Card.getCardByItsName(nameText.getText());
        switch (type) {
            case "for sell":{
                AlertController.setAndShowAndDo(
                        String.format
                                ("Do you want to increase number of %s in shop?" +
                                                " (You have %d number of it in shop and %d at all)",
                                        card.getName(), Shop.getShop().getNumberOfCardInShop(card),
                                        Shop.getShop().getNumberOfCardAtAll(card)),
                        () -> Shop.getShop().changeNumberOfCardInShop(card, 1));
                break;
            }
            case "for buy":{
                if (Shop.getShop().getNumberOfCardInShop(card) > 0) {
                    AlertController.setAndShowAndDo(
                            String.format
                                    ("Do you want to decrease number of %s in shop?" +
                                                    " (You have %d number of it in shop and %d at all)",
                                            card.getName(), Shop.getShop().getNumberOfCardInShop(card),
                                            Shop.getShop().getNumberOfCardAtAll(card)),
                            () -> Shop.getShop().changeNumberOfCardInShop(card, -1));
                } else {
                    AlertController.setAndShow(String.format("You haven't any of %s in shop" +
                                    " (You have %d number of it at all)",
                            card.getName(), Shop.getShop().getNumberOfCardAtAll(card)));
                }
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
        VisualSpell vs = new VisualSpell(spell.getName());
        double widthScale = gifPane.getPrefWidth() / vs.animation.width;
        double heightScale = gifPane.getPrefHeight() / vs.animation.height;
        Scale scale = new Scale(widthScale, heightScale, 0, 0);
        vs.view.getTransforms().add(scale);
        gifPane.getChildren().add(vs.view);
        priceText.setText(String.valueOf(spell.getPrice()));
        if (!Spell.checkIsItem(spell)) manaText.setText(String.valueOf(spell.getRequiredMana()));
        nameText.setText(spell.getName());
        typeText.setText(String.format("%s", Spell.checkIsItem(spell) ? "Item" : "Spell"));
        descriptionText.setText(spell.description.descriptionOfCardSpecialAbility);
        this.type = type;
    }
}
