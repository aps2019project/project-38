package client.net;

import com.google.gson.Gson;
import model.Cell;
import model.cards.HeroPower;
import model.cards.Spell;
import model.cards.Warrior;
import view.fxmlControllers.GlobalChatController;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Decoder {
    public static void decode(Message m) {
        switch (m) {
            case HeroPowerOfPlayer:
                fillBoxAndNotifyJ(Digikala.hp, HeroPower.class);
                break;
            case WarriorOfACell:
                fillBoxAndNotifyJ(Digikala.warriorBox, Warrior.class);
                break;
            case IndexofAvatar:
                fillBoxAndNotify(Digikala.avatarIndex);
                break;
            case PlayerUsername:
                fillBoxAndNotify(Digikala.playerUsername);
                break;
            case SpecificCell:
                fillBoxAndNotifyJ(Digikala.specificCell, Cell.class);
                break;
            case NextCard:
                if (readMessage().equals(Message.itsSpell)) {
                    fillBoxAndNotifyJ(Digikala.nextCard, Spell.class);
                } else {
                    fillBoxAndNotifyJ(Digikala.nextCard, Warrior.class);
                }
                break;
            case HandCard:
                if(readMessage().equals(Message.itsSpell)){
                    fillBoxAndNotifyJ(Digikala.handCard, Spell.class);
                }else {
                    fillBoxAndNotifyJ(Digikala.handCard, Warrior.class);
                }
                break;
            case ActivePlayerIndex:
                fillBoxAndNotify(Digikala.activePlayerIndex);
                break;
            case updateMessages: {
                GlobalChatController.updateMessages();
                break;
            }
            case isMyWarrior:
                fillBoxAndNotify(Digikala.isMyWarrior);
                break;
            case isThereWarrior:
                fillBoxAndNotify(Digikala.isThereWarrior);
                break;
            //------------------
            case put:{

                break;
            }
            case quitTheGame:{

                break;
            }
            case move:{

                break;
            }
            case attack:{

                break;
            }
            case useCard:{

                break;
            }
            case cast:{

                break;
            }
            case setCoolDown:{

                break;
            }
            case useCollectible:{

                break;
            }
        }
    }

    public static Message readMessage() {
        try {
            return Message.values()[ClientSession.dis.readInt()];
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object readObject() {
        try (ObjectInputStream ois = new ObjectInputStream(ClientSession.dis)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> void fillBoxAndNotify(Box<T> box) {
        box.obj = (T) readObject();
        synchronized (box.waitStone) {
            box.waitStone.notify();
        }
    }

    public static <T> void fillBoxAndNotifyJ(Box<T> box, Class aClass) {
        Gson gson = new Gson();
        try {
            box.obj = (T) gson.fromJson(ClientSession.dis.readUTF(), aClass);
            synchronized (box.waitStone) {
                box.waitStone.notify();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
