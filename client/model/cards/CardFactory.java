package model.cards;

import client.net.Digikala;

import java.util.ArrayList;
import java.util.HashMap;

public interface CardFactory {
    static HashMap<Warrior, Integer> getMinionToNumberHashMapForShop() {
        //todo server
    }

    static HashMap<Hero, Integer> getHeroesToNumberHashMapForShop() {
        //todo server
    }

    static HashMap<Spell, Integer> getSpellToNumberHashMapForShop() {
        //todo server
    }

    static HashMap<Spell, Integer> getItemToNumberHashMapForShop() {
        //todo server
    }

    static ArrayList<Warrior> getAllBuiltMinions() {
        return Digikala.getAllBuiltMinions();
    }

    static ArrayList<Hero> getAllBuiltHeroes() {
        return Digikala.getAllBuiltHeroes();
    }

    static ArrayList<Spell> getAllBuiltSpells() {
        return Digikala.getAllBuiltSpells();
    }

    static ArrayList<Spell> getAllBuiltItems() {
        return Digikala.getAllBuiltItems();
    }
}
