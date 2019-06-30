package view.fxmlControllers;

import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import model.Account;
import model.cards.Hero;
import model.cards.Warrior;
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
    private boolean forSell;

    public void selectCard(MouseEvent mouseEvent) {
        if (forSell) {
            AlertController alertController = AlertController.setAndShowAndWaitToGetResult(
                    String.format("Do you want to sell %s? Price: %s (You have %d number of this card) Your Derrick: %d",
                            nameText.getText(), priceText.getText(),
                            Account.getActiveAccount().getCollection().getHowManyCard().get(nameText.getText()),
                            Account.getActiveAccount().derrick), true);
            new Thread(() -> {
                try {
                    synchronized (alertController)  {
                        alertController.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (alertController.result) {
                    CollectionOfShopController.sell(nameText.getText());
                }
            }).start();
        }
        else {
            if (Account.getActiveAccount().derrick >= Integer.parseInt(priceText.getText())) {
                AlertController alertController = AlertController.setAndShowAndWaitToGetResult(
                        String.format("Do you want to buy %s? Price: %s (You have %d number of this card) Your Derrick: %d)",
                                nameText.getText(), priceText.getText(),
                                Account.getActiveAccount().getCollection().getHowManyCard().get(nameText.getText()),
                                Account.getActiveAccount().derrick), true);
                new Thread(() -> {
                    try {
                        synchronized (alertController)  {
                            alertController.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (alertController.result) {
                        ShopController.buy(nameText.getText());
                    }
                }).start();
            }
            else {
                AlertController.setAndShowAndWaitToGetResult("You have not enough Derrick", false);
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

    public void setFields(Warrior warrior, boolean forSell) {
        apText.setText(String.valueOf(warrior.getAp()));
        hpText.setText(String.valueOf(warrior.getHp()));
        VisualMinion vm = new VisualMinion(warrior.getName());
        double widthScale = gifPane.getPrefWidth() / vm.animation.width;
        double heightScale = gifPane.getPrefHeight() / vm.animation.height;
        Scale scale = new Scale(widthScale, heightScale, 0, 0);
        vm.view.getTransforms().add(scale);
        gifPane.getChildren().add(vm.view);
        priceText.setText(String.valueOf(warrior.getPrice()));
        manaText.setText(String.valueOf(warrior.getRequiredMana()));
        nameText.setText(warrior.getName());
        typeText.setText(String.format("%s (%s)", warrior instanceof Hero ? "Hero" : "Minion", warrior.getWarriorType()));
        descriptionText.setText(warrior.description.descriptionOfCardSpecialAbility);//todo
        this.forSell = forSell;
    }
}
