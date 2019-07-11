package model.cards;

import model.Cell;
import model.effects.AP;
import model.effects.HP;
import model.effects.Melee;
import model.effects.Ranged;

import java.io.*;

public class Warrior extends Card {
    transient private Cell cell;
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

    public String getWarriorType() {
        if(this.getEffects().stream().anyMatch(effect -> effect instanceof Melee)) {
            if (this.getEffects().stream().anyMatch(effect -> effect instanceof Ranged)) {
                return "Hybrid";
            }
            else {
                return "Melee";
            }
        }
        return "Ranged";
    }

    @Override
    public void apply(Cell cell) {
        cell.setWarrior(this);
        this.cell=cell;
        cell.getBoard().getGame().getActivePlayer().getWarriors().add(this);

        ArenaController.ac.put(cell.getRow(),cell.getColumn(),this.name);
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