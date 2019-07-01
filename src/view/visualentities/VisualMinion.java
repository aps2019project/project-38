package view.visualentities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Collection;
import model.cards.Card;
import model.cards.CardFactory;
import model.cards.Warrior;
import view.fxmlControllers.ArenaController;

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
        try {
            ImageView temp = new ImageView(new Image(new FileInputStream("src/view/images/sprites/" + name + ".png")));
            temp.setVisible(false);
            view = temp;
            animation = new SpriteAnimation(view, SpriteType.breathing, name, -1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        view.setOnMouseEntered(event -> {
            idle();
//            if (ArenaController.ac.game != null) {
//                Warrior theCard = (Warrior) ArenaController.ac.game.getActivePlayer().getHand().values().stream().filter(Objects::nonNull).filter(card -> card.getName().equals(name)).findAny().orElse(null);
//                if (theCard == null) {
//                    if (ArenaController.ac.game.getActivePlayer().getPlayerHero().getName().equals(name)) {
//                        theCard = ArenaController.ac.game.getActivePlayer().getPlayerHero();
//                    }
//                    if(theCard==null){
//                    }
//                }
//
//                if (theCard == null) {
//                    System.err.println("didn't find the vm");
//                    return;
//                }
//                ArenaController.ac.showInfoOfACard(name, theCard.description.getDescriptionOfCardSpecialAbility(), "warrior", theCard.getHp(), theCard.getAp());
//            }
        });
        view.setOnMouseExited(event -> {
            breathing();
//            if (ArenaController.ac.game != null) {
//                ArenaController.ac.endShowInfoOfACard();
//            }
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