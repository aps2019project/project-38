package view.fxmlControllers;

import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.cards.Card;
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

    public void selectCard(MouseEvent mouseEvent) {
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

    public void setFields(Warrior warrior) {
        apText.setText(String.valueOf(warrior.getAp()));
        hpText.setText(String.valueOf(warrior.getHp()));
        VisualMinion vm = new VisualMinion(warrior.getName());
        gifPane.getChildren().add(vm.view);
        vm.view.setFitWidth(gifPane.getMinWidth());
        vm.view.setFitHeight(gifPane.getMinHeight());
        priceText.setText(String.valueOf(warrior.getPrice()));
        manaText.setText(String.valueOf(warrior.getRequiredMana()));
        nameText.setText(warrior.getName());
        typeText.setText("Minion");
        descriptionText.setText(warrior.description.descriptionOfCardSpecialAbility);//todo + milli ranged hybrid
    }
}
