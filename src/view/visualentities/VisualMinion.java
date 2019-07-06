package view.visualentities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Collection;
import model.cards.Card;
import model.cards.CardFactory;
import model.cards.Warrior;
import view.fxmlControllers.ArenaController;
import view.images.LoadedImages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class VisualMinion {
    String name;
    public ImageView view;
    public SpriteAnimation animation;

    public double getWidth() {
        return animation.getWidth();
    }

    public double getHeight() {
        return animation.getHeight();
    }

    public VisualMinion(String name) {
        this.name = name;

        ImageView temp;
        if (Card.getAllCards().values().stream().filter(Objects::nonNull).anyMatch(card -> String.valueOf(card.getID()).startsWith("5"))) {
            temp = new ImageView(LoadedImages.sprites.get("cw"));
        } else {
            temp = new ImageView(LoadedImages.sprites.get(name));
        }
        temp.setVisible(false);
        view = temp;
        animation = new SpriteAnimation(view, SpriteType.breathing, name, -1);

        view.setOnMouseEntered(event -> {
            idle();
        });
        view.setOnMouseExited(event -> {
            breathing();
        });
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