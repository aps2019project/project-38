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
    private HashMap<Integer, CardControllerForAuction> mineAuctionIndexToCardControllerHashMap = new HashMap<>();
    private HashMap<Integer, CardControllerForAuction> othersAuctionIndexToCardControllerHashMap = new HashMap<>();

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
            for (Map.Entry<Integer, CardControllerForAuction> entry : mineAuctionIndexToCardControllerHashMap.entrySet()) {
                if (counter % 3 == 0) myAuctionsLeftVBox.getChildren().add(entry.getValue().cardAnchorPane);
                else if (counter % 3 == 1) myAuctionsMiddleVBox.getChildren().add(entry.getValue().cardAnchorPane);
                else myAuctionsRightVBox.getChildren().add(entry.getValue().cardAnchorPane);
                counter++;
            }
            counter = 0;
            for (Map.Entry<Integer, CardControllerForAuction> entry : othersAuctionIndexToCardControllerHashMap.entrySet()) {
                if (counter % 3 == 0) otherAuctionsLeftVBox.getChildren().add(entry.getValue().cardAnchorPane);
                else if (counter % 3 == 1) otherAuctionsMiddleVBox.getChildren().add(entry.getValue().cardAnchorPane);
                else otherAuctionsRightVBox.getChildren().add(entry.getValue().cardAnchorPane);
                counter++;
            }
        });
    }

    public void loadCard(Card card, boolean itsMine, int auctionIndex) {
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
            spellCardController.setFields(spell, String.format("for auction %d %s", auctionIndex, String.valueOf(itsMine)));
            spellCardController.cardAnchorPane = anchorPane;
            if (itsMine) mineAuctionIndexToCardControllerHashMap.put(auctionIndex, spellCardController);
            else othersAuctionIndexToCardControllerHashMap.put(auctionIndex, spellCardController);
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
            warriorCardController.setFields(warrior, String.format("for auction %d %s", auctionIndex, String.valueOf(itsMine)));
            warriorCardController.cardAnchorPane = anchorPane;
            if (itsMine) mineAuctionIndexToCardControllerHashMap.put(auctionIndex, warriorCardController);
            else othersAuctionIndexToCardControllerHashMap.put(auctionIndex, warriorCardController);
        }
        if (WindowChanger.instance.equalsToMainParent(LoadedScenes.auctions)) calculateEveryThing();
    }

    public void removeCard(int auctionIndex) {
        mineAuctionIndexToCardControllerHashMap.remove(auctionIndex);
        othersAuctionIndexToCardControllerHashMap.remove(auctionIndex);
        if (WindowChanger.instance.equalsToMainParent(LoadedScenes.auctions)) calculateEveryThing();
    }

    public CardControllerForAuction getCardControllerByAuctionIndex(int auctionIndex) {
        if (mineAuctionIndexToCardControllerHashMap.containsKey(auctionIndex)) {
            return mineAuctionIndexToCardControllerHashMap.get(auctionIndex);
        }
        else {
            return othersAuctionIndexToCardControllerHashMap.get(auctionIndex);
        }
    }
}
