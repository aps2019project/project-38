package client.net;

import com.google.gson.Gson;
import model.cards.Card;
import model.cards.HeroPower;
import model.cards.Spell;
import model.cards.Warrior;
import view.Utility;
import view.fxmlControllers.ArenaController;
import view.fxmlControllers.GlobalChatController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.util.HashMap;

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
//            case NextCard:
//                if (readMessage().equals(Message.itsSpell)) {
//                    fillBoxAndNotifyJ(Digikala.nextCard, Spell.class);
//                } else {
//                    fillBoxAndNotifyJ(Digikala.nextCard, Warrior.class);
//                }
//                break;
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
            //-----------------
            case put: {
                int hero_row = (int) readObject();
                int hero_col = (int) readObject();
                String name = (String) readObject();
                ArenaController.ac.put(hero_row, hero_col, name);
                break;
            }
            case quitTheGame: {
                String winnerName = (String) readObject();
                ArenaController.ac.endGame(winnerName);
                break;
            }
            case move: {
                int original_row = (int) readObject();
                int original_col = (int) readObject();
                int target_row = (int) readObject();
                int target_col = (int) readObject();
                ArenaController.ac.move(original_row, original_col, target_row, target_col);
                break;
            }
            case attack: {
                int attackerCell_row = (int) readObject();
                int attackerCell_col = (int) readObject();
                int defenderCell_row = (int) readObject();
                int defenderCell_col = (int) readObject();
                ArenaController.ac.attack(attackerCell_row, attackerCell_col, defenderCell_row, defenderCell_col);
                break;
            }
            case useCard: {
                int handMapKey = (int) readObject();
                ArenaController.ac.useCard(handMapKey);
                break;
            }
            case cast: {
                int heroCell_row = (int) readObject();
                int heroCell_col = (int) readObject();
                ArenaController.ac.cast(heroCell_row, heroCell_col);
                break;
            }
            case setCoolDown: {
                int remainedTurnToCoolDown = (int) readObject();
                int playerNumber = (int) readObject();
                ArenaController.ac.setHeroSpecialPowerCoolDown(remainedTurnToCoolDown, playerNumber);
                break;
            }
            case useCollectible: {
                int indexOf = (int) readObject();
                int playerNumber = (int) readObject();
                ArenaController.ac.useCollectibleItem(indexOf, playerNumber);
                break;
            }
            case mana: {
                int manaNumber = (int) readObject();
                int playerNumber = (int) readObject();
                ArenaController.ac.setActiveMana(manaNumber, playerNumber);
                break;
            }
            case graveYard: {
                String name = (String) readObject();
                String type = (String) readObject();
                int playerNumber = (int) readObject();
                ArenaController.ac.transferToGraveYard(name, type, playerNumber);
            }
            case setActivePlayer:{
                int index = (int) readObject();
                ArenaController.ac.setActivePlayer(index);
                break;
            }
            case buildPlayerHand:{
                Gson gson = new Gson();
                int playerIndex = (int) readObject();
                HashMap<Integer, Card> handMap = new HashMap<>();
                int mapSize = (int) readObject();
                for (int i = 0; i < mapSize; i++) {
                    int index = (int)readObject();
                    if(readMessage().equals(Message.itsSpell)){
                        Spell spell = gson.fromJson((String)readObject(),Spell.class);
                        handMap.put(index,spell);
                    }else {
                        Warrior warrior = gson.fromJson((String)readObject(),Warrior.class);
                        handMap.put(index,warrior);
                    }
                }
                ArenaController.ac.buildPlayerHand(handMap,playerIndex);
                break;
            }
            case showCollectedCollectibleItems:{
                ArenaController.ac.showCollectedCollectibleItems((String)readObject(),(int)readObject());
                break;
            }
            case kill:{
                ArenaController.ac.kill((int)readObject(),(int)readObject());
                break;
            }
            case showPopup:{
                Utility.showMessage((String)readObject());
                break;
            }
//            fillBoxAndNotifyJ(new Box<>(),new TypeToken<ArrayList<Card>>(){}.getType());
            //---------------
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
        box.obj = (T) gson.fromJson((String)readObject(), aClass);
        synchronized (box.waitStone) {
            box.waitStone.notify();
        }
    }

    public static <T> void fillBoxAndNotifyJ(Box<T> box, Type aType) {
        Gson gson = new Gson();
        box.obj = (T) gson.fromJson((String)readObject(), aType);
        synchronized (box.waitStone) {
            box.waitStone.notify();
        }
    }
}
