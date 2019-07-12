package view.fxmls;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

import static view.Utility.tScale;

public class LoadedScenes {
    public static Parent customCard;

    {
        try {
            customCard = tScale(FXMLLoader.load(getClass().getResource("moreCustomCard.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
