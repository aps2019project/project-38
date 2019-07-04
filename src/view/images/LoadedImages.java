package view.images;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoadedImages {
    public static HashMap<String, ArrayList<Image>> notStaticBeforeGameImages = new HashMap<>();

    public static Image[] avatars = new Image[15];
    public static Image blueMana = null;
    public static Image grayMana = null;
    public static Image blueCircle = null;
    public static Image grayCircle;

    {
        try {
            //for arena:
            blueMana = new Image(new FileInputStream("src/view/images/arena/blueMana.png"));
            grayMana = new Image(new FileInputStream("src/view/images/arena/grayMana.png"));
            blueCircle = new Image(new FileInputStream("src/view/images/arena/blueCircle.png"));
            grayCircle = new Image(new FileInputStream("src/view/images/arena/grayCircle.png"));
            for (int i = 0; i < 15; i++) {
                avatars[i] = new Image(new FileInputStream("src/view/images/accounts/circular/" + i + ".png"));
            }
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