package model.cards;

import model.Cell;
import model.QualityHaver;
import model.actions.AutoAction;
import model.player.Player;
import model.targets.SpellTarget;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Spell extends Card {
    private boolean isItem;
    private HashMap<AutoAction, SpellTarget> actions = new HashMap<>();

    public Spell(Integer ID, String name, int requiredMana, Integer price, boolean isItem) {
        super(ID, name, price, requiredMana);
        this.isItem = isItem;
    }

    public HashMap<AutoAction, SpellTarget> getActions() {
        return actions;
    }

    public static boolean checkIsItem(Card card) {
        if (card instanceof Spell) {
            Spell spell = (Spell) card;
            return spell.isItem;
        }
        return false;
    }

    public boolean isItem() {
        return isItem;
    }

    @Override
    public boolean apply(Cell cell) {
        Player user = cell.getBoard().getGame().getActivePlayer();
        boolean didSth = false;
        for (Map.Entry<AutoAction, SpellTarget> entry : actions.entrySet()) {
            for (QualityHaver target : entry.getValue().getTarget(user, cell)) {
                didSth = true;
                entry.getKey().execute(this, target);
            }
        }
        return didSth;
    }

    @Override
    public Spell deepCopy(){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Spell) ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            System.err.println("could deep copy in spell:");
            e.printStackTrace();
        }
        return this;
    }
}
