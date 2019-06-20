package view.images;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class LoadedImages {
    public static Image backGroundOfRegisterMenu = null;
    public static Image[] avatars = new Image[15];
    public static Image blueMana = null;
    public static Image grayMana = null;
    public static Image activeCard = null;
    public static Image activePlayer = null;
    public static Image inactivePlayer = null;
    public static Image blueCircle = null;

    {
        Random random = new Random();
        try {
            backGroundOfRegisterMenu = new Image(new FileInputStream("src/view/images/registerMenu/" + random.nextInt(5) + ".jpg"));
            //for arena:
            blueMana = new Image(new FileInputStream("src/view/images/arena/blueMana.png"));
            grayMana = new Image(new FileInputStream("src/view/images/arena/grayMana.png"));
            activeCard = new Image(new FileInputStream("src/view/images/arena/activeCard.png"));
            activePlayer = new Image(new FileInputStream("src/view/images/arena/activePlayer.png"));
            inactivePlayer = new Image(new FileInputStream("src/view/images/arena/inactivePlayer.png"));
            blueCircle = new Image(new FileInputStream("src/view/images/arena/blueCircle.png"));
            for (int i = 0; i < 15; i++) {
                avatars[i] = new Image(new FileInputStream("src/view/images/accounts/circular/" + i + ".png"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}