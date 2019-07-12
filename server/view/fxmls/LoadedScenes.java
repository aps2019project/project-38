package view.fxmls;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import view.Utility;
import view.fxmlControllers.CollectionOfShopController;
import view.fxmlControllers.ShopController;

import java.io.IOException;

import static view.Utility.tScale;

public class LoadedScenes {
    private FXMLLoader fxmlLoader;
    public static Parent customCard;
    public static  Parent shop;
    public static Parent collectionOfShop;

    {
        try {
            customCard = tScale(FXMLLoader.load(getClass().getResource("moreCustomCard.fxml")));
            fxmlLoader = new FXMLLoader(getClass().getResource("shop.fxml"));
            shop = Utility.scale(fxmlLoader.load());
            ShopController.shopController = fxmlLoader.getController();
            fxmlLoader = new FXMLLoader(getClass().getResource("collectionOfShop.fxml"));
            collectionOfShop = Utility.scale(fxmlLoader.load());
            CollectionOfShopController.collectionOfShopController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
