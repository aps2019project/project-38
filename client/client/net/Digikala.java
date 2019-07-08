package client.net;

import model.cards.HeroPower;
import model.cards.Warrior;


public class Digikala {
    public static Box<HeroPower> hp = new Box<>();
    public static HeroPower getHeroPowerOfPlayer(int playerIndex){
        Encoder.sendPackage(Message.HeroPowerOfPlayer,playerIndex);
        synchronized (hp) {
            try {
                hp.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return hp.obj;
    }

    public static Box<Warrior> warriorBox = new Box<>();
    public static Warrior getWarriorOfACell(int row,int col){
        Encoder.sendPackage(Message.WarriorOfACell,row,col);
        synchronized (warriorBox){
            try {
                warriorBox.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return warriorBox.obj;
    }

    public static Box<Integer> avatarIndex = new Box<>();
    public static int getIndexofAvatar(int playerIndex){
        Encoder.sendPackage(Message.IndexofAvatar,playerIndex);
        synchronized (avatarIndex) {
            try {
                avatarIndex.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return avatarIndex.obj;
        }
    }

}
