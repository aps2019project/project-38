package client.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Account;
import model.Collection;
import model.Level;
import model.cards.*;
import view.Utility;
import view.fxmlControllers.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
            case setActivePlayer: {
                int index = (int) readObject();
                ArenaController.ac.setActivePlayer(index);
                break;
            }
            case buildPlayerHand: {
                Gson gson = new Gson();
                int playerIndex = (int) readObject();

                HashMap<Integer, Card> handMap = new HashMap<>();
                int mapSize = (int) readObject();
                for (int i = 0; i < mapSize; i++) {
                    int index = (int) readObject();
                    if (readMessage().equals(Message.itsSpell)) {
                        Spell spell = gson.fromJson((String) readObject(), Spell.class);
                        handMap.put(index, spell);
                    } else {
                        Warrior warrior = gson.fromJson((String) readObject(), Warrior.class);
                        handMap.put(index, warrior);
                    }
                }

                ArenaController.ac.buildPlayerHand(handMap, playerIndex);
                break;
            }
            case showCollectedCollectibleItems: {
                ArenaController.ac.showCollectedCollectibleItems((String) readObject(), (int) readObject());
                break;
            }
            case kill: {
                ArenaController.ac.kill((int) readObject(), (int) readObject());
                break;
            }
            case showPopup: {
                Utility.showMessage((String) readObject());
                break;
            }
            ///////////////
            case getCollection: {
                fillBoxAndNotifyJ(Digikala.collectionBox, Collection.class);
                break;
            }
            case getDerrick: {
                fillBoxAndNotify(Digikala.derrick);
                break;
            }
            case getAllBuiltMinions: {
                fillBoxAndNotifyJ(Digikala.allBuiltMinions, new TypeToken<ArrayList<Warrior>>() {
                }.getType());
                break;
            }
            case getAllBuiltHeroes: {
                fillBoxAndNotifyJ(Digikala.allBuiltHeroes, new TypeToken<ArrayList<Hero>>() {
                }.getType());
                break;
            }
            case getAllBuiltSpells: {
                fillBoxAndNotifyJ(Digikala.allBuiltSpells, new TypeToken<ArrayList<Spell>>() {
                }.getType());
                break;
            }
            case getAllBuiltItems: {
                fillBoxAndNotifyJ(Digikala.allBuiltItems, new TypeToken<ArrayList<Spell>>() {
                }.getType());
                break;
            }
            case getAllCards: {
                Gson gson = new Gson();
                HashMap<Integer, Card> allCards = new HashMap<>();

                int size = (int) readObject();
                for (int i = 0; i < size; i++) {
                    int id = (int) readObject();
                    if (readMessage().equals(Message.itsWarrior)) {
                        Warrior warrior = gson.fromJson((String) readObject(), Warrior.class);
                        allCards.put(id, warrior);
                    } else {
                        Spell spell = gson.fromJson((String) readObject(), Spell.class);
                        allCards.put(id, spell);
                    }
                }

                Digikala.allCards.obj = allCards;
                synchronized (Digikala.allCards.waitStone) {
                    Digikala.allCards.waitStone.notify();
                }
                break;
            }
            case getAllBuiltMinionsHashMapForShop: {
                fillHashmapOfCardToInt(Digikala.allBuiltMinionsHashMapForShop, new TypeToken<HashMap<Warrior, Integer>>() {
                }.getType());
                break;
            }
            case getAllBuiltHeroesHashMapForShop: {
                fillHashmapOfCardToInt(Digikala.allBuiltHeroesHashMapForShop, new TypeToken<HashMap<Hero, Integer>>() {
                }.getType());
                break;
            }
            case getAllBuiltSpellsHashMapForShop: {
                fillHashmapOfCardToInt(Digikala.allBuiltSpellsHashMapForShop, new TypeToken<HashMap<Spell, Integer>>() {
                }.getType());
                break;
            }
            case getAllBuiltItemsHashMapForShop: {
                fillHashmapOfCardToInt(Digikala.allBuiltItemsHashMapForShop, new TypeToken<HashMap<Spell, Integer>>() {
                }.getType());
                break;
            }
            //---------------
            ///////ali:
            case AuctionResult: {
                String result = (String) readObject();
                AlertController.setAndShow(result);
                break;
            }
            case AuctionProposedPrice: {
                fillBoxAndNotify(Digikala.auctionProposedPriceIsAccepted);
                break;
            }
            case AuctionMaxProposedPriceUpdated: {
                int auctionIndex = (int) readObject();
                String username = (String) readObject();
                int newMaxProposedPrice = (int) readObject();
                AuctionController.setMaxProposedPrice(auctionIndex, newMaxProposedPrice, username);
                break;
            }
            case StartNewAuction: {
                String username = (String) readObject();
                String cardName = (String) readObject();
                int auctionIndex = (int) readObject();
                AuctionsController.auctionsController.loadCard(Card.getCardByItsName(cardName),
                        Account.getActiveAccount().username.equals(username), auctionIndex);
                break;
            }
            case CreateDeck: {
                fillBoxAndNotify(Collection.createDeckResult);
                break;
            }
            case DeleteDeck: {
                fillBoxAndNotify(Collection.deleteDeckResult);
                break;
            }
            case AddCardToDeck: {
                fillBoxAndNotify(Collection.addCardToDeckResult);
                break;
            }
            case RemoveCardFromDeck: {
                fillBoxAndNotify(Collection.removeCardFromDeckResult);
                break;
            }
            case SelectMainDeck: {
                fillBoxAndNotify(Collection.selectMainDeckResult);
                break;
            }
            case RenameDeck: {
                fillBoxAndNotify(Collection.renameDeckResult);
                break;
            }
            case LevelsDescription:{
                fillBoxAndNotifyJ(Level.levelsDescription, new TypeToken<ArrayList<String>>(){}.getType());
                break;
            }
            case StartGame:{
                GameStartWaitingRoomController.gameStartWaitingRoomController.exitToArena();
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
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(ClientSession.dis);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static <T> void fillBoxAndNotify(Box<T> box) {
        box.obj = (T) readObject();
        synchronized (box.waitStone) {
            box.waitStone.notify();
        }
    }

    private static <T> void fillBoxAndNotifyJ(Box<T> box, Class aClass) {
        Gson gson = new Gson();
        box.obj = (T) gson.fromJson((String) readObject(), aClass);
        synchronized (box.waitStone) {
            box.waitStone.notify();
        }
    }

    private static <T> void fillBoxAndNotifyJ(Box<T> box, Type aType) {
        Gson gson = new Gson();
        String s = (String) readObject();
        box.obj = (T) gson.fromJson(s, aType);
        synchronized (box.waitStone) {
            box.waitStone.notify();
        }
    }

    private static <T> void fillHashmapOfCardToInt(Box<T> box, Type aType) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        box.obj = (T) gson.fromJson((String) readObject(), aType);
        synchronized (box.waitStone) {
            box.waitStone.notify();
        }
    }
}
