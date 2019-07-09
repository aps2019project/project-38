package view.visualentities;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.images.LoadedImages;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpriteAnimation extends Transition {
    ImageView imageView;
    String fileName;
    int prefDuration;
    public SpriteType type;
    public int height, width;
    public int realDuration;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

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

    private void getFramesFromPlist() {
        String dataS = null;
        try {
            dataS = new String(Files.readAllBytes(LoadedImages.plists.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Matcher matcher;
        switch (type) {
            case spellBreathing:
                matcher = Pattern.compile("(?<key>>[0-9a-zA-Z]*_[0-9a-zA-Z]*_[0-9a-zA-Z]*_[0-9a-zA-Z]*\\.)|((?<x>\\d+),(?<y>\\d+)},\\{(?<width>\\d+),(?<height>\\d+))").matcher(dataS);
                break;
            default:
                matcher = Pattern.compile("(?<key>_" + type.name() + "_)|((?<x>\\d+),(?<y>\\d+)},\\{(?<width>\\d+),(?<height>\\d+))").matcher(dataS);
                break;
        }
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
            if (!imageView.isVisible()) {
                imageView.setVisible(true);
            }
            lastIndex = index;
        }
    }
}