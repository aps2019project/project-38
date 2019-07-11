package client.net;

import model.Cell;
import model.cards.Card;
import model.cards.HeroPower;
import model.cards.Warrior;

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
    public static int getIndexofAvatar(int playerIndex){
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

    public static Box<Cell> specificCell = new Box<>();
    public static Cell getSpecificCell(int row, int col){
        Encoder.sendPackage(Message.SpecificCell,row,col);
        wait(specificCell);
        return specificCell.obj;
    }

    public static Box<Card> nextCard = new Box<>();
    public static Card getNextCard(){//of me
        Encoder.sendPackage(Message.NextCard);
        wait(nextCard);
        return nextCard.obj;
    }

    public static Box<Card> handCard = new Box<>();
    public static Card getHandCard(int index){//of me
        Encoder.sendPackage(Message.HandCard,index);
        wait(handCard);
        return handCard.obj;
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

    public static Box<HashMap<Integer, Card>> idToCardAllCards = new Box<>();
    public static HashMap<Integer, Card> getAllCards() {
        Encoder.sendMessage(Message.IDToCardAllCards);
        wait(idToCardAllCards);
        return idToCardAllCards.obj;
    }
}
