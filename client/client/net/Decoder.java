package client.net;

import model.cards.HeroPower;
import model.cards.Warrior;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;
import java.util.Optional;

public class Decoder {
    public static void decode(Message m) {
        switch (m) {
            case HeroPowerOfPlayer:
                Digikala.hp.obj = (HeroPower) readObject();
                synchronized (Digikala.hp) {
                    Digikala.hp.notify();
                }
                break;
            case WarriorOfACell:
                Digikala.warriorBox.obj = (Warrior)readObject();
                synchronized (Digikala.warriorBox){
                    Digikala.warriorBox.notify();
                }
                break;

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
