package view.images;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class LoadedImages {
    public static Image backGroundOfRegisterMenu = null;


    {
        Random random = new Random();
        try {
            backGroundOfRegisterMenu = new Image(new FileInputStream("src/view/images/registerMenu/" + (random.nextInt(5) + 1) + ".jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    {

    }
}