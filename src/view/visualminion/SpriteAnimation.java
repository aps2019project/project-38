package view.visualminion;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpriteAnimation extends Transition {
    ImageView imageView;
    SpriteType type;
    String fileName;
    int prefDuration;
    public int height,width;
    public int realDuration;

    int lastIndex;
    ArrayList<SpriteFrame> frames = new ArrayList<>();

    public SpriteAnimation(ImageView imageView, SpriteType type, String fileName, int prefDuration) {
        this.imageView = imageView;
        this.type = type;
        this.fileName = fileName;
        this.prefDuration = prefDuration;

        getFramesFromPlist();
        setCycleDuration(Duration.millis(50 * frames.size()));
        switch (prefDuration) {
            case -1:
                setCycleCount(INDEFINITE);
                realDuration = -1;
                break;
            case 0:
                setCycleCount(1);
                realDuration = (int) getCycleDuration().toMillis();
                break;
            default:
                setCycleCount(prefDuration / (int) getCycleDuration().toMillis());
                realDuration = (int) (getCycleCount() * getCycleDuration().toMillis());
                break;
        }
        setInterpolator(Interpolator.LINEAR);
        this.play();
    }

    void getFramesFromPlist() {
        String dataS = null;
        try {
            dataS = new String(Files.readAllBytes(Paths.get("src/view/images/sprites/" + fileName + ".plist")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Matcher matcher = Pattern.compile("(?<key>_" + type.name() + "_)|((?<x>\\d+),(?<y>\\d+)},\\{(?<width>\\d+),(?<height>\\d+))").matcher(dataS);
        while (matcher.find()) {
            if (matcher.group("key") == null) {
                continue;
            }
            matcher.find();
            frames.add(new SpriteFrame(Integer.valueOf(matcher.group("x")), Integer.valueOf(matcher.group("y")), Integer.valueOf(matcher.group("width")), Integer.valueOf(matcher.group("height"))));
            height = Integer.valueOf(matcher.group("height"));
            width = Integer.valueOf(matcher.group("width"));
            matcher.find();
        }
    }

    @Override
    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * frames.size()), frames.size() - 1);
        if (index != lastIndex) {
            imageView.setViewport(new Rectangle2D(frames.get(index).x, frames.get(index).y, frames.get(index).width, frames.get(index).height));
            lastIndex = index;
        }
    }
}