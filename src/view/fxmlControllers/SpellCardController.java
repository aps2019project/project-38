package view.fxmlControllers;

import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.cards.Hero;
import model.cards.Spell;
import model.cards.Warrior;
import view.visualentities.VisualMinion;

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

    public void selectCard(MouseEvent mouseEvent) {
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

    public void setFields(Spell spell) {
        VisualMinion vm = new VisualMinion(spell.getName());
        gifPane.getChildren().add(vm.view);
        vm.view.setFitWidth(gifPane.getMinWidth());
        vm.view.setFitHeight(gifPane.getMinHeight());
        priceText.setText(String.valueOf(spell.getPrice()));
        manaText.setText(String.valueOf(spell.getRequiredMana()));
        nameText.setText(spell.getName());
        typeText.setText(String.format("%s", spell.isItem() ? "Item" : "Spell"));
        descriptionText.setText(spell.description.descriptionOfCardSpecialAbility);//todo
    }
}
