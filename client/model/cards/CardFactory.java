package model.cards;

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
        //todo server
    }

    static ArrayList<Hero> getAllBuiltHeroes() {
        //todo server
    }

    static ArrayList<Spell> getAllBuiltSpells() {
        //todo server
    }

    static ArrayList<Spell> getAllBuiltItems() {
        //todo server
    }
}
