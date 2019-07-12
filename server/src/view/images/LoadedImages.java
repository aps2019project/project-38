package view.images;

import javafx.scene.image.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class LoadedImages {
    public static HashMap<String,Image> sprites = new HashMap<>();
    public static HashMap<String,Path> plists = new HashMap<>();

    static { //todo amir (static)
        try {
            for (Path path : Files.newDirectoryStream(Paths.get("src/view/images/sprites"))) {
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
}