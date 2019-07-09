package view.fxmlControllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.cards.Card;
import model.cards.Spell;
import model.cards.Warrior;
import view.WindowChanger;
import view.fxmlControllers.cardHolder.SpellCardController;
import view.fxmlControllers.cardHolder.WarriorCardController;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static view.Utility.scaleCard;

public class AuctionsController {
    public static AuctionsController auctionsController;
    public VBox myAuctionsLeftVBox;
    public VBox myAuctionsMiddleVBox;
    public VBox myAuctionsRightVBox;
    public VBox otherAuctionsLeftVBox;
    public VBox otherAuctionsMiddleVBox;
    public VBox otherAuctionsRightVBox;
    public ImageView backButton;
    private HashMap<Card, CardControllerForAuction> mineCardToCardControllerHashMap = new HashMap<>();
    private HashMap<Card, CardControllerForAuction> othersCardToCardControllerHashMap = new HashMap<>();

    public void back(MouseEvent mouseEvent) {
        CollectionOfShopController.collectionOfShopController.calculateEverything();
        WindowChanger.instance.setMainParent(LoadedScenes.collectionOfShop);
    }

    public void shineBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(new Glow(1));
    }

    public void resetBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(null);
    }

    public void calculateEveryThing() {
        Platform.runLater(() -> {
            myAuctionsLeftVBox.getChildren().clear();
            myAuctionsMiddleVBox.getChildren().clear();
            myAuctionsRightVBox.getChildren().clear();
            otherAuctionsLeftVBox.getChildren().clear();
            otherAuctionsMiddleVBox.getChildren().clear();
            otherAuctionsRightVBox.getChildren().clear();
            int counter = 0;
            for (Map.Entry<Card, CardControllerForAuction> entry : mineCardToCardControllerHashMap.entrySet()) {
                if (counter % 3 == 0) myAuctionsLeftVBox.getChildren().add(entry.getValue().cardAnchorPane);
                else if (counter % 3 == 1) myAuctionsMiddleVBox.getChildren().add(entry.getValue().cardAnchorPane);
                else myAuctionsRightVBox.getChildren().add(entry.getValue().cardAnchorPane);
                counter++;
            }
            counter = 0;
            for (Map.Entry<Card, CardControllerForAuction> entry : othersCardToCardControllerHashMap.entrySet()) {
                if (counter % 3 == 0) otherAuctionsLeftVBox.getChildren().add(entry.getValue().cardAnchorPane);
                else if (counter % 3 == 1) otherAuctionsMiddleVBox.getChildren().add(entry.getValue().cardAnchorPane);
                else otherAuctionsRightVBox.getChildren().add(entry.getValue().cardAnchorPane);
                counter++;
            }
        });
    }

    public void loadCard(Card card, boolean itsMine) {
        if (card instanceof Spell) {
            Spell spell = (Spell) card;
            AnchorPane anchorPane = null;
            SpellCardController spellCardController = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("spellCart.fxml"));
                anchorPane = scaleCard(fxmlLoader.load());
                spellCardController = fxmlLoader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
            spellCardController.setFields(spell, "for auction");
            spellCardController.cardAnchorPane = anchorPane;
            if (itsMine) mineCardToCardControllerHashMap.put(spell, spellCardController);
            else othersCardToCardControllerHashMap.put(spell, spellCardController);
        }
        else {
            Warrior warrior = (Warrior) card;
            AnchorPane anchorPane = null;
            WarriorCardController warriorCardController = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("warriorCart.fxml"));
                anchorPane = scaleCard(fxmlLoader.load());
                warriorCardController = fxmlLoader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
            warriorCardController.setFields(warrior, "for auction");
            warriorCardController.cardAnchorPane = anchorPane;
            if (itsMine) mineCardToCardControllerHashMap.put(warrior, warriorCardController);
            else othersCardToCardControllerHashMap.put(warrior, warriorCardController);
        }
        if (WindowChanger.instance.equalsToMainParent(LoadedScenes.auctions)) calculateEveryThing();
    }

    public void removeCard(Card card) {
        mineCardToCardControllerHashMap.remove(card);
        othersCardToCardControllerHashMap.remove(card);
        if (WindowChanger.instance.equalsToMainParent(LoadedScenes.auctions)) calculateEveryThing();
    }

    public CardControllerForAuction getCardControllerByCard(Card card) {
        if (mineCardToCardControllerHashMap.containsKey(card)) {
            return mineCardToCardControllerHashMap.get(card);
        }
        else {
            return othersCardToCardControllerHashMap.get(card);
        }
    }
}
