package model.cards.heros;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Cell;
import model.cards.Card;
import model.cards.spells.Spell;
import model.cards.warriors.Warrior;
import model.effects.Disarm;
import model.effects.Dispelablity;
import model.gamestate.GameState;
import model.triggers.Trigger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Hero extends Warrior {
    private Card card;

    public Hero(int ID, String name, int requiredMana, int price, boolean isItem, int HP, int AP, Card card) {
        super(ID, name, requiredMana, price, isItem, HP, AP);
        this.card = card;
    }
}
