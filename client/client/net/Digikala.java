package client.net;

import model.Collection;
import model.cards.*;

import java.util.ArrayList;
import java.util.HashMap;


public class Digikala {

    public static void wait(Box box) {
        synchronized (box.waitStone) {
            try {
                box.waitStone.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Box<HeroPower> hp = new Box<>();

    public static HeroPower getHeroPowerOfPlayer(int playerIndex) {
        Encoder.sendPackage(Message.HeroPowerOfPlayer, playerIndex);
        wait(hp);
        return hp.obj;
    }

    public static Box<Warrior> warriorBox = new Box<>();

    public static Warrior getWarriorOfACell(int row, int col) {
        Encoder.sendPackage(Message.WarriorOfACell, row, col);
        wait(warriorBox);
        return warriorBox.obj;
    }

    public static Box<Integer> avatarIndex = new Box<>();

    public static int getIndexOfAvatar(int playerIndex) {
        Encoder.sendPackage(Message.IndexofAvatar, playerIndex);
        wait(avatarIndex);
        return avatarIndex.obj;
    }

    public static Box<String> playerUsername = new Box<>();

    public static String getPlayerUsername(int playerIndex) {
        Encoder.sendPackage(Message.PlayerUsername, playerIndex);
        wait(playerUsername);
        return playerUsername.obj;
    }

    public static Box<Integer> activePlayerIndex = new Box<>();

    public static Integer getActivePlayerIndex() {
        Encoder.sendPackage(Message.ActivePlayerIndex);
        wait(activePlayerIndex);
        return activePlayerIndex.obj;
    }

    public static Box<Boolean> isMyWarrior = new Box<>();

    public static boolean getIsMyWarrior(int row, int col) {
        Encoder.sendPackage(Message.isMyWarrior, row, col);
        wait(isMyWarrior);
        return isMyWarrior.obj;
    }

    public static Box<Boolean> isThereWarrior = new Box<>();

    public static boolean getIsThereWarrior(int row, int col) {
        Encoder.sendPackage(Message.isThereWarrior, row, col);
        wait(isThereWarrior);
        return isThereWarrior.obj;
    }

    public static Box<Boolean> auctionProposedPriceIsAccepted = new Box<>();

    public static boolean getIsProposedPriceAccepted(int auctionIndex, int proposedPrice) {
        Encoder.sendPackage(Message.AuctionProposedPrice, auctionIndex, proposedPrice);
        wait(isThereWarrior);
        return isThereWarrior.obj;
    }

    public static Box<Collection> collectionBox = new Box<>();

    public static Collection getCollection() {
        Encoder.sendMessage(Message.getCollection);
        wait(collectionBox);
        return collectionBox.obj;
    }

    public static Box<Integer> derrick = new Box<>();

    public static Integer getDerrick() {
        Encoder.sendMessage(Message.getDerrick);
        wait(derrick);
        return derrick.obj;
    }

    public static Box<ArrayList<Warrior>> allBuiltMinions = new Box<>();

    public static ArrayList<Warrior> getAllBuiltMinions() {
        if (allBuiltMinions.obj == null) {
            Encoder.sendMessage(Message.getAllBuiltMinions);
            wait(allBuiltMinions);
        }
        return allBuiltMinions.obj;
    }

    public static Box<ArrayList<Hero>> allBuiltHeroes = new Box<>();

    public static ArrayList<Hero> getAllBuiltHeroes() {
        if (allBuiltHeroes.obj == null) {
            Encoder.sendMessage(Message.getAllBuiltHeroes);
            wait(allBuiltHeroes);
        }
        return allBuiltHeroes.obj;
    }

    public static Box<ArrayList<Spell>> allBuiltSpells = new Box<>();

    public static ArrayList<Spell> getAllBuiltSpells() {
        if (allBuiltSpells.obj == null) {
            Encoder.sendMessage(Message.getAllBuiltSpells);
            wait(allBuiltSpells);
        }
        return allBuiltSpells.obj;
    }

    public static Box<ArrayList<Spell>> allBuiltItems = new Box<>();

    public static ArrayList<Spell> getAllBuiltItems() {
        if (allBuiltItems.obj == null) {
            Encoder.sendMessage(Message.getAllBuiltItems);
            wait(allBuiltItems);
        }
        return allBuiltItems.obj;
    }

    public static Box<String> warriorType = new Box<>();

    public static String getWarriorType(int id) {
        Encoder.sendPackage(Message.getWarriorType, id);
        wait(warriorType);
        return warriorType.obj;
    }

    public static Box<Integer> IDByName = new Box<>();

    public static Integer getIDByName(String name) {
        Encoder.sendPackage(Message.getIDByName, name);
        wait(IDByName);
        return IDByName.obj;
    }

    public static Box<HashMap<Integer, Card>> allCards = new Box<>();

    public static HashMap<Integer, Card> getAllCards() {
        if (allCards.obj == null) {
            Encoder.sendPackage(Message.getAllCards);
            wait(allCards);
        }
        return allCards.obj;
    }

    public static Box<HashMap<Warrior, Integer>> allBuiltMinionsHashMapForShop = new Box<>();

    public static HashMap<Warrior, Integer> getAllBuiltMinionsHashMapForShop() {
        if (allBuiltMinionsHashMapForShop.obj == null) {
            Encoder.sendMessage(Message.getAllBuiltMinionsHashMapForShop);
            wait(allBuiltMinionsHashMapForShop);
        }
        return allBuiltMinionsHashMapForShop.obj;
    }

    public static Box<HashMap<Hero, Integer>> allBuiltHeroesHashMapForShop = new Box<>();

    public static HashMap<Hero, Integer> getAllBuiltHeroesHashMapForShop() {
        if (allBuiltHeroesHashMapForShop.obj == null) {
            Encoder.sendMessage(Message.getAllBuiltHeroesHashMapForShop);
            wait(allBuiltHeroesHashMapForShop);
        }
        return allBuiltHeroesHashMapForShop.obj;
    }

    public static Box<HashMap<Spell, Integer>> allBuiltSpellsHashMapForShop = new Box<>();

    public static HashMap<Spell, Integer> getAllBuiltSpellsHashMapForShop() {
        if (allBuiltSpellsHashMapForShop.obj == null) {
            Encoder.sendMessage(Message.getAllBuiltSpellsHashMapForShop);
            wait(allBuiltSpellsHashMapForShop);
        }
        return allBuiltSpellsHashMapForShop.obj;
    }

    public static Box<HashMap<Spell, Integer>> allBuiltItemsHashMapForShop = new Box<>();

    public static HashMap<Spell, Integer> getAllBuiltItemsHashMapForShop() {
        if (allBuiltItemsHashMapForShop.obj == null) {
            Encoder.sendMessage(Message.getAllBuiltItemsHashMapForShop);
            wait(allBuiltItemsHashMapForShop);
        }
        return allBuiltItemsHashMapForShop.obj;
    }
}