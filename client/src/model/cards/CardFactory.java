package model.cards;

import client.net.Digikala;

import java.util.ArrayList;
import java.util.HashMap;

public interface CardFactory {
    static HashMap<Warrior, Integer> getMinionToNumberHashMapForShop() {
        return Digikala.getAllBuiltMinionsHashMapForShop();
    }

    static HashMap<Hero, Integer> getHeroesToNumberHashMapForShop() {
        return Digikala.getAllBuiltHeroesHashMapForShop();
    }

    static HashMap<Spell, Integer> getSpellToNumberHashMapForShop() {
        return Digikala.getAllBuiltSpellsHashMapForShop();
    }

    static HashMap<Spell, Integer> getItemToNumberHashMapForShop() {
        return Digikala.getAllBuiltItemsHashMapForShop();
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
