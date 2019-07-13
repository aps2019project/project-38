package view.images;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoadedImages {
    public static HashMap<String, ArrayList<Image>> notStaticBeforeGameImages = new HashMap<>();

    public static Image[] avatars = new Image[15];
    public static Image blueMana = null;
    public static Image grayMana = null;
    public static Image blueCircle = null;
    public static Image grayCircle;
    public static HashMap<String,Image> sprites = new HashMap<>();
    public static HashMap<String,Path> plists = new HashMap<>();

    {
        try {
            //for arena:
            blueMana = new Image(new FileInputStream("client/view/images/arena/blueMana.png"));
            grayMana = new Image(new FileInputStream("client/view/images/arena/grayMana.png"));
            blueCircle = new Image(new FileInputStream("client/view/images/arena/blueCircle.png"));
            grayCircle = new Image(new FileInputStream("client/view/images/arena/grayCircle.png"));
            for (int i = 0; i < 15; i++) {
                avatars[i] = new Image(new FileInputStream("client/view/images/accounts/circular/" + i + ".png"));
            }
            for (Path path : Files.newDirectoryStream(Paths.get("client/view/images/sprites"))) {
                if(path.toString().endsWith(".plist")){
                    plists.put(path.getFileName().toString().replace(".plist",""),path);
                }else {
                    sprites.put(path.getFileName().toString().replace(".png",""),new Image(path.toUri().toURL().toString()));
                }
            }
        } catch (IOException e) {
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
                    entry.getValue().add(new Image(new FileInputStream("client/view/images/gamepreview/notstatics/" + entry.getKey() + i + ".png")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}