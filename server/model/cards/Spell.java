package model.cards;

import model.Cell;
import model.QualityHaver;
import model.actions.AutoAction;
import model.exceptions.NotEnoughConditions;
import model.player.Player;
import model.targets.SpellTarget;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Spell extends Card {
    private boolean isItem;
    transient private HashMap<AutoAction, SpellTarget> actions = new HashMap<>();

    public Spell(Integer ID, String name, int requiredMana, Integer price, boolean isItem) {
        super(ID, name, price, requiredMana);
        this.isItem = isItem;
    }

    public static boolean checkIsItem(Card card) {
        if (card instanceof Spell) {
            Spell spell = (Spell) card;
            return spell.isItem;
        }
        return false;
    }

    @Override
    public void apply(Cell cell) throws NotEnoughConditions {
        Player user = cell.getBoard().getGame().getActivePlayer();
        boolean didSth = false;
        for (Map.Entry<AutoAction, SpellTarget> entry : actions.entrySet()) {
            for (QualityHaver target : entry.getValue().getTarget(user, cell)) {
                didSth = entry.getKey().execute(this, target);
            }
        }
        if (!didSth) {
            throw new NotEnoughConditions("No valid target");
        }
    }

    @Override
    public Spell deepCopy() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Spell) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("could not deep copy in spell:");
            e.printStackTrace();
        }
        return this;
    }

    public HashMap<AutoAction, SpellTarget> getActions() {
        return actions;
    }

    public boolean isItem() {
        return isItem;
    }
}
