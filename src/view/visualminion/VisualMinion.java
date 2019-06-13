package view.visualminion;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class VisualMinion {
    String name;
    public ImageView view;
    public SpriteAnimation animation;

    public VisualMinion(String name) {
        this.name = name;
        try {
            view = new ImageView(new Image(new FileInputStream("src/view/images/sprites/" + name + ".png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        animation = new SpriteAnimation(view, SpriteType.breathing, name, -1);

        view.setOnMouseEntered(event -> idle());
        view.setOnMouseExited(event -> breathing());
    }

    public void attack() {
        animation.stop();
        SpriteAnimation sp = new SpriteAnimation(view, SpriteType.attack, name, 0);
        animation = sp;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (sp.equals(animation)) {
                    breathing();
                }
            }
        }, sp.realDuration);
    }

    public void run() {
        animation.stop();
        animation = new SpriteAnimation(view, SpriteType.run, name, -1);
    }

    public void death() {
        animation.stop();
        SpriteAnimation sp = new SpriteAnimation(view, SpriteType.death, name, 0);
        animation = sp;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (sp.equals(animation)) {
                    breathing();
                }
            }
        }, sp.realDuration);
    }

    public void hit() {
        animation.stop();
        SpriteAnimation sp = new SpriteAnimation(view, SpriteType.hit, name, 0);
        animation = sp;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (sp.equals(animation)) {
                    breathing();
                }
            }
        }, sp.realDuration);
    }

    public void cast() {
        animation.stop();
        SpriteAnimation sp1 = new SpriteAnimation(view, SpriteType.caststart, name, 0);
        animation = sp1;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (sp1.equals(animation)) {
                    SpriteAnimation sp2 = new SpriteAnimation(view, SpriteType.cast, name, 0);
                    animation = sp2;

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (sp2.equals(animation)) {
                                SpriteAnimation sp3 = new SpriteAnimation(view, SpriteType.castend, name, 0);
                                animation = sp3;

                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        if (sp3.equals(animation)) {
                                            breathing();
                                        }
                                    }
                                }, sp3.realDuration);
                            }
                        }
                    }, sp2.realDuration);
                }
            }
        }, sp1.realDuration);
    }

    public void breathing() {
        animation.stop();
        animation = new SpriteAnimation(view, SpriteType.breathing, name, -1);
    }

    public void idle() {
        animation.stop();
        animation = new SpriteAnimation(view, SpriteType.idle, name, -1);
    }
}