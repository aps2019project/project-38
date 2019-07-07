package view.fxmlControllers;

import javafx.application.Platform;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Deck;

public class DeckButtonController {
    public ImageView button;
    public Text buttonText;
    private Deck deck;

    public void doClickEvents(MouseEvent mouseEvent) {
        CollectionController.collectionController.selectDeck(deck);
    }

    public void shineButton(MouseEvent mouseEvent) {
        button.setEffect(new DropShadow());
        buttonText.setEffect(new DropShadow());
    }

    public void resetButton(MouseEvent mouseEvent) {
        button.setEffect(null);
        buttonText.setEffect(null);
    }

    public void setFields(Deck deck, String type) {
        this.deck = deck;
        Platform.runLater(() -> buttonText.setText(deck.getName() + ": " + type));
    }
}
