package model.cards;

import client.net.Digikala;

import java.util.ArrayList;
import java.util.HashMap;

public interface CardFactory {
    static HashMap<Warrior, Integer> getAllBuiltMinionsHashMapForShop() {
        //todo server
    }

    static HashMap<Hero, Integer> getAllBuiltHeroesHashMapForShop() {
        //todo server
    }

    static HashMap<Spell, Integer> getAllBuiltSpellsHashMapForShop() {
        //todo server
    }

    static HashMap<Spell, Integer> getAllBuiltItemsHashMapForShop() {
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
