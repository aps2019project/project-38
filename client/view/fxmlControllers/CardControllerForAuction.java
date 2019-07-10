package view.fxmlControllers;

import javafx.scene.layout.AnchorPane;
import view.WindowChanger;

public abstract class CardControllerForAuction {
    private AnchorPane anchorPaneOfAuction;
    private AuctionController auctionController;
    public AnchorPane cardAnchorPane;

    public abstract void setMaxProposedPrice(int proposedPrice, String accountOfProposedPrice);

    void initializeForAuctionType(AnchorPane anchorPaneOfAuction, AuctionController auctionController) {
        this.anchorPaneOfAuction = anchorPaneOfAuction;
        this.auctionController = auctionController;
    }

    public void enterAuction() {
        WindowChanger.instance.setMainParent(anchorPaneOfAuction);
    }

    public AnchorPane getAnchorPaneOfAuction() {
        return anchorPaneOfAuction;
    }

    public AuctionController getAuctionController() {
        return auctionController;
    }
}
