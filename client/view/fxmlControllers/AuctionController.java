package view.fxmlControllers;

import client.net.Digikala;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import model.Account;
import model.cards.Card;
import model.cards.Spell;
import model.cards.Warrior;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class AuctionController implements Initializable {
    public TextField priceTextField;
    public ImageView sendProposedPriceButton;
    public Text sendProposedPriceText;
    public AnchorPane cardPlace;
    public Text timerText;
    public ImageView backButton;
    public ImageView priceTextFieldImage;
    private int auctionIndex;
    private Card card;
    private CardControllerForAuction cardControllerForAuction;
    private int maxProposedPrice;

    public void sendProposedPrice(MouseEvent mouseEvent) {
        String priceString = priceTextField.getText();
        if (!priceString.equals("")) {
            int price = Integer.parseInt(priceString);
            if (price > maxProposedPrice) {
                if (price <= Account.activeAccount.getDerrick()) {
                    if (Digikala.getIsProposedPriceAccepted(auctionIndex, price)) {
                        AlertController.setAndShow(String.format
                                ("Your proposed price (%d) for card with name %s accepted", price, card.getName()));
                    }
                    else {
                        AlertController.setAndShow(String.format
                                ("Your proposed price (%d) for card with name %s did't accept", price, card.getName()));
                    }
                }
                else {
                    AlertController.setAndShow("You have not enough derrick");
                }
            } else {
                AlertController.setAndShow("Your proposed price is too small");
            }
        } else {
          AlertController.setAndShow("Enter your proposed price");
        }
    }

    public void shineSendProposedPriceButton(MouseEvent mouseEvent) {
        sendProposedPriceButton.setEffect(new Bloom(0.4));
        sendProposedPriceText.setEffect(new  Bloom(0));
    }

    public void resetSendProposedPriceButton(MouseEvent mouseEvent) {
        sendProposedPriceButton.setEffect(null);
        sendProposedPriceText.setEffect(null);
    }

    public void back(MouseEvent mouseEvent) {
        AuctionsController.auctionsController.calculateEveryThing();
        WindowChanger.instance.setMainParent(LoadedScenes.auctions);
    }

    public void shineBackButton(MouseEvent mouseEvent) {
        backButton.setEffect(new Glow(1));
    }

    public void resetBackButton(MouseEvent mouseEvent) {
        backButton.setEffect(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priceTextField.setOnKeyTyped(keyEvent -> {
            priceTextField.setText(priceTextField.getText().replaceAll("[^\\d]", ""));
            priceTextField.selectEnd();
            priceTextField.deselect();
        });
    }

    public void initialize(int auctionIndex, Card card, boolean mine) {
        this.auctionIndex = auctionIndex;
        this.card = card;
        if (card instanceof Spell) {
            Spell spell = (Spell) card;
            AnchorPane anchorPane;
            SpellCardController spellCardController = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("spellCart.fxml"));
                anchorPane = fxmlLoader.load();
                double xScale = cardPlace.getPrefWidth() / anchorPane.getPrefWidth();
                double yScale = cardPlace.getPrefHeight() / anchorPane.getPrefHeight();
                Scale scale = new Scale(xScale, yScale, 0, 0);
                anchorPane.getTransforms().add(scale);
                cardPlace.getChildren().add(anchorPane);
                cardControllerForAuction = fxmlLoader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
            spellCardController.setFields(spell, "for auction inside");
        }
        else {
            Warrior warrior = (Warrior) card;
            AnchorPane anchorPane;
            WarriorCardController warriorCardController = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("warriorCart.fxml"));
                anchorPane = fxmlLoader.load();
                double xScale = cardPlace.getPrefWidth() / anchorPane.getPrefWidth();
                double yScale = cardPlace.getPrefHeight() / anchorPane.getPrefHeight();
                Scale scale = new Scale(xScale, yScale, 0, 0);
                anchorPane.getTransforms().add(scale);
                cardPlace.getChildren().add(anchorPane);
                cardControllerForAuction = fxmlLoader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
            warriorCardController.setFields(warrior, "for auction inside");
        }
        if (mine) {
            priceTextField.setOpacity(0);
            priceTextFieldImage.setOpacity(0);
            sendProposedPriceText.setOpacity(0);
            sendProposedPriceButton.setOpacity(0);
            priceTextField.setDisable(true);
            priceTextFieldImage.setDisable(true);
            sendProposedPriceText.setDisable(true);
            sendProposedPriceButton.setDisable(true);
        }
        start();
    }

    private void start() {
        long startTime = System.currentTimeMillis();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                int time = (int) ((System.currentTimeMillis() - startTime) / 1000);
                Platform.runLater(() -> timerText.setText(String.format("%d:%02d", time / 60, time % 60)));
                if (time >= 3 * 60) finish();
            }
        }, 500, 3 * 60 * 1000 + 1000);
    }

    private void finish() {
        AuctionsController.auctionsController.removeCard(auctionIndex);
        if (WindowChanger.instance.equalsToMainParent
                (AuctionsController.auctionsController.getCardControllerByAuctionIndex(auctionIndex)
                        .getAnchorPaneOfAuction())) {
            AuctionsController.auctionsController.calculateEveryThing();
            WindowChanger.instance.setMainParent(LoadedScenes.auctions);
        }
        System.gc();
    }

    private void setMaxProposedPrice(int maxProposedPrice, String usernameOfAccountOfMaxProposedPrice) {
        this.maxProposedPrice = maxProposedPrice;
        cardControllerForAuction.setMaxProposedPrice(maxProposedPrice, usernameOfAccountOfMaxProposedPrice);

    }

    public static void setMaxProposedPrice(int auctionIndex, int maxProposedPrice, String usernameOfAccountOfMaxProposedPrice) {
        AuctionsController.auctionsController.getCardControllerByAuctionIndex(auctionIndex).getAuctionController()
                .setMaxProposedPrice(maxProposedPrice, usernameOfAccountOfMaxProposedPrice);
        AuctionsController.auctionsController.getCardControllerByAuctionIndex(auctionIndex)
                .setMaxProposedPrice(maxProposedPrice, usernameOfAccountOfMaxProposedPrice);
    }
}