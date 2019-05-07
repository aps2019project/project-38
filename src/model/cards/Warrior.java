package model.cards;

import model.Cell;
import model.effects.AP;
import model.effects.HP;
import model.player.Player;

import java.io.*;

public class Warrior extends Card {
    private Cell cell;
    private int hp;
    private int ap;

    public Warrior(Integer ID, String name, Integer price, int requiredMana, int hp, int ap) {
        super(ID, name, price, requiredMana);
        this.hp = hp;
        this.ap = ap;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getHp() {
        return hp+ effects.stream().filter(effect -> effect instanceof HP).mapToInt(effect -> ((HP)effect).getHp()).sum();
    }

    public int getAp() {
        return ap + effects.stream().filter(effect -> effect instanceof AP).mapToInt(effect -> ((AP)effect).getAp()).sum();
    }

    @Override
    public boolean apply(Cell cell) {
        cell.setWarrior(this);
        this.cell=cell;
        cell.getBoard().getGame().getActivePlayer().getWarriors().add(this);
        return true;
    }

    @Override
    public Warrior deepCopy(){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            Warrior copied = (Warrior) ois.readObject();
            return copied;
        }catch (IOException | ClassNotFoundException e){
            System.err.println("could not deep copy in Warrior: "+this.name);
            e.printStackTrace();
        }
        return this;
    }
}