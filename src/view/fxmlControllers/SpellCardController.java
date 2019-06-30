package view.fxmlControllers;

import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import model.Account;
import model.cards.Hero;
import model.cards.Spell;
import model.cards.Warrior;
import view.visualentities.VisualMinion;
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
    private boolean forSell;

    public void selectCard(MouseEvent mouseEvent) {
        if (forSell) {
            AlertController alertController = AlertController.setAndShowAndWaitToGetResult(
                    String.format("Do you want to sell %s? Price: %s",
                            nameText.getText(), priceText.getText()), true);
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
                        String.format("Do you want to buy %s? Price: %s",
                                nameText.getText(), priceText.getText()), true);
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

    public void setFields(Spell spell, boolean forSell) {
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
        descriptionText.setText(spell.description.descriptionOfCardSpecialAbility);//todo
        this.forSell = forSell;
    }
}
