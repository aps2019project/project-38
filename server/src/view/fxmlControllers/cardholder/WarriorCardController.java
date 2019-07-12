package view.fxmlControllers.cardholder;

import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import model.Shop;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Warrior;
import view.fxmlControllers.AlertController;
import view.visualentities.VisualMinion;

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
        descriptionText.setText(warrior.description.descriptionOfCardSpecialAbility);
        this.type = type;
    }
}
