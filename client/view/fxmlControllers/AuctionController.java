package view.fxmlControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AuctionController implements Initializable {
    public TextField priceTextField;
    public ImageView sendProposedPriceButton;
    public Text sendProposedPriceText;
    public AnchorPane cardPlace;
    private int maxProposedPrice;

    public void sendProposedPrice(MouseEvent mouseEvent) {
        String priceString = priceTextField.getText();
        if (!priceString.equals("")) {
            int price = Integer.parseInt(priceString);
            if (price > maxProposedPrice) {

            } else {
                AlertController.setAndShow("Yor proposed price is too small");
            }
        } else {
          AlertController.setAndShow("Enter your proposed price");
        }
    }

    public void shineSendProposedPriceButton(MouseEvent mouseEvent) {
    }

    public void resetSendProposedPriceButton(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priceTextField.setOnKeyTyped(keyEvent -> {
            priceTextField.setText(priceTextField.getText().replaceAll("[^\\d]", ""));
            priceTextField.selectEnd();
            priceTextField.deselect();
        });
    }
}
