package model.cards;

import client.net.Digikala;

import java.util.ArrayList;
import java.util.HashMap;

public interface CardFactory {
    static HashMap<Warrior, Integer> getAllBuiltMinionsHashMapForShop() {
        return Digikala.getAllBuiltMinionsHashMapForShop();
    }

    static HashMap<Hero, Integer> getAllBuiltHeroesHashMapForShop() {
        return Digikala.getAllBuiltHeroesHashMapForShop();
    }

    static HashMap<Spell, Integer> getAllBuiltSpellsHashMapForShop() {
        return Digikala.getAllBuiltSpellsHashMapForShop();
    }

    static HashMap<Spell, Integer> getAllBuiltItemsHashMapForShop() {
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
