package client.net;

import view.fxmlControllers.GlobalChatController;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Decoder {
    public static void decode(Message m) {
        switch (m) {
            case HeroPowerOfPlayer:
                fillBoxAndNotify(Digikala.hp);
                break;
            case WarriorOfACell:
                fillBoxAndNotify(Digikala.warriorBox);
                break;
            case IndexofAvatar:
                fillBoxAndNotify(Digikala.avatarIndex);
                break;
            case PlayerUsername:
                fillBoxAndNotify(Digikala.playerUsername);
                break;
            case SpecificCell:
                fillBoxAndNotify(Digikala.specificCell);
                break;
            case NextCard:
                fillBoxAndNotify(Digikala.nextCard);
                break;
            case HandCard:
                fillBoxAndNotify(Digikala.handCard);
                break;
            case ActivePlayerIndex:
                fillBoxAndNotify(Digikala.activePlayerIndex);
                break;
            case updateMessages: {
                GlobalChatController.updateMessages();
                break;
            }
        }
    }

    public static <T> void fillBoxAndNotify(Box<T> box) {
        box.obj = (T) readObject();
        synchronized (box.waitStone) {
            box.waitStone.notify();
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
}
