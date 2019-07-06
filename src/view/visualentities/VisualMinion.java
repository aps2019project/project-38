package view.visualentities;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class VisualMinion {
    String name;
    public AtomicBoolean isSelected = new AtomicBoolean(false);
    public AtomicBoolean isRunning = new AtomicBoolean(false);
    public ArrayBlockingQueue<SpriteType> actionQueue = new ArrayBlockingQueue<>(10);
    public ImageView view;
    public SpriteAnimation animation;
    public SpriteType lastState = SpriteType.breathing;

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
            isSelected.set(true);
        });
        view.setOnMouseExited(event -> {
            isSelected.set(false);
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                SpriteType st;
                if (isRunning.get()) {
                    if (lastState != SpriteType.run && lastState != SpriteType.death) {
                        run();
                    }
                } else if (isSelected.get()) {
                    if (lastState != SpriteType.idle && lastState != SpriteType.cast && lastState != SpriteType.death && lastState != SpriteType.attack) {
                        idle();
                    }
                } else if (!actionQueue.isEmpty()) {
                    if (!((lastState == SpriteType.cast || lastState == SpriteType.attack) && animation.getStatus() == Animation.Status.RUNNING) && (st = actionQueue.poll()) != null) {
                        switch (st) {
                            case cast:
                                cast();
                                break;
                            case death:
                                death();
                                stop();
                                break;
                            case attack:
                                attack();

                                break;
                        }
                    }
                } else {
                    if (lastState != SpriteType.breathing && !((lastState == SpriteType.cast || lastState == SpriteType.attack) && animation.getStatus() == Animation.Status.RUNNING) && lastState != SpriteType.death) {
                        breathing();
                    }
                }
            }
        }.start();
    }

    private void attack() {
        Platform.runLater(() -> {
            animation.stop();
            animation = new SpriteAnimation(view, SpriteType.attack, name, 0);
            lastState = SpriteType.attack;
        });
    }


    private void run() {
        Platform.runLater(() -> {
            animation.stop();
            animation = new SpriteAnimation(view, SpriteType.run, name, -1);
            lastState = SpriteType.run;
        });
    }

    private void death() {
        Platform.runLater(() -> {
            animation.stop();
            animation = new SpriteAnimation(view, SpriteType.death, name, 0);
            lastState = SpriteType.death;
        });
    }

//    private void hit() {
//        animation.stop();
//        SpriteAnimation sp = new SpriteAnimation(view, SpriteType.hit, name, 0);
//        animation = sp;
//
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (sp.equals(animation)) {
//                    breathing();
//                }
//            }
//        }, sp.realDuration);
//    }

    private void cast() {
        Platform.runLater(() -> {
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
                                    animation = new SpriteAnimation(view, SpriteType.castend, name, 0);
                                }
                            }
                        }, sp2.realDuration);
                    }
                }
            }, sp1.realDuration);
            lastState = SpriteType.cast;
        });
    }

    private void breathing() {
        Platform.runLater(() -> {
            animation.stop();
            animation = new SpriteAnimation(view, SpriteType.breathing, name, -1);
            lastState = SpriteType.breathing;
        });
    }

    private void idle() {
        Platform.runLater(() -> {
            animation.stop();
            animation = new SpriteAnimation(view, SpriteType.idle, name, -1);
            lastState = SpriteType.idle;
        });
    }
}