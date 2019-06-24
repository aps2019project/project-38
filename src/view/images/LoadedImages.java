package view.images;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoadedImages {
    public static Image backGroundOfRegisterMenu = null;
    public static HashMap<String, ArrayList<Image>> notStaticBeforeGameImages = new HashMap<>();


    {
        Random random = new Random();
        try {
            backGroundOfRegisterMenu = new Image(new FileInputStream("src/view/images/registerMenu/" + (random.nextInt(5) + 1) + ".jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    {
        notStaticBeforeGameImages.put("background", new ArrayList<>());
        notStaticBeforeGameImages.put("face", new ArrayList<>());
        notStaticBeforeGameImages.put("shadow", new ArrayList<>());
        for (int i = 0; i < 6; i++) {
            for (Map.Entry<String, ArrayList<Image>> entry : notStaticBeforeGameImages.entrySet()) {
                try {
                    entry.getValue().add(new Image(new FileInputStream("src/view/images/gamepreview/notstatics/" + entry.getKey() + i + ".png")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}