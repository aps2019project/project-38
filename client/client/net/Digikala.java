package client.net;

import model.Collection;
import model.cards.*;

import java.util.ArrayList;
import java.util.HashMap;


public class Digikala {
    public static void wait(Box box){
        synchronized (box) {
            try {
                box.waitStone.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Box<HeroPower> hp = new Box<>();
    public static HeroPower getHeroPowerOfPlayer(int playerIndex){
        Encoder.sendPackage(Message.HeroPowerOfPlayer,playerIndex);
        wait(hp);
        return hp.obj;
    }

    public static Box<Warrior> warriorBox = new Box<>();
    public static Warrior getWarriorOfACell(int row,int col){
        Encoder.sendPackage(Message.WarriorOfACell,row,col);
        wait(warriorBox);
        return warriorBox.obj;
    }

    public static Box<Integer> avatarIndex = new Box<>();
    public static int getIndexOfAvatar(int playerIndex){
        Encoder.sendPackage(Message.IndexofAvatar,playerIndex);
        wait(avatarIndex);
        return avatarIndex.obj;
    }

    public static Box<String> playerUsername = new Box<>();
    public static String getPlayerUsername(int playerIndex){
        Encoder.sendPackage(Message.PlayerUsername,playerIndex);
        wait(playerUsername);
        return playerUsername.obj;
    }

    public static Box<Integer> activePlayerIndex = new Box<>();
    public static Integer getActivePlayerIndex(){
        Encoder.sendPackage(Message.ActivePlayerIndex);
        wait(activePlayerIndex);
        return activePlayerIndex.obj;
    }

    public static Box<Boolean> isMyWarrior = new Box<>();
    public static boolean getIsMyWarrior(int row,int col){
        Encoder.sendPackage(Message.isMyWarrior,row,col);
        wait(isMyWarrior);
        return isMyWarrior.obj;
    }

    public static Box<Boolean> isThereWarrior = new Box<>();
    public static boolean getIsThereWarrior(int row,int col){
        Encoder.sendPackage(Message.isThereWarrior,row,col);
        wait(isThereWarrior);
        return isThereWarrior.obj;
    }

    public static Box<Boolean> auctionProposedPrice = new Box<>();
    public static boolean getIsProposedPriceAccepted(int auctionIndex, String username, int proposedPrice){
        Encoder.sendPackage(Message.AuctionProposedPrice, auctionIndex, username, proposedPrice);
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
    public static Integer getDerrick(){
        Encoder.sendMessage(Message.getDerrick);
        wait(derrick);
        return derrick.obj;
    }

    public static Box<ArrayList<Warrior>> allBuiltMinions = new Box<>();
    public static ArrayList<Warrior> getAllBuiltMinions(){
        Encoder.sendMessage(Message.getAllBuiltMinions);
        wait(allBuiltMinions);
        return allBuiltMinions.obj;
    }

    public static Box<ArrayList<Hero>> allBuiltHeroes = new Box<>();
    public static ArrayList<Hero> getAllBuiltHeroes(){
        Encoder.sendMessage(Message.getAllBuiltHeroes);
        wait(allBuiltHeroes);
        return allBuiltHeroes.obj;
    }

    public static Box<ArrayList<Spell>> allBuiltSpells = new Box<>();
    public static ArrayList<Spell> getAllBuiltSpells(){
        Encoder.sendMessage(Message.getAllBuiltSpells);
        wait(allBuiltSpells);
        return allBuiltSpells.obj;
    }

    public static Box<ArrayList<Spell>> allBuiltItems = new Box<>();
    public static ArrayList<Spell> getAllBuiltItems(){
        Encoder.sendMessage(Message.getAllBuiltItems);
        wait(allBuiltItems);
        return allBuiltItems.obj;
    }
}