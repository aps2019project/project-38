package model;

import model.cards.*;
import server.DBMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Shop {
    public static int baseNumberOfACardInShop = 4;
    private static Shop shop = new Shop();
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private  HashMap<Warrior, Integer> minionToNumberHashMap = new HashMap<>();
    private  HashMap<Hero, Integer> heroesToNumberHashMap = new HashMap<>();
    private  HashMap<Spell, Integer> spellToNumberHashMap = new HashMap<>();
    private  HashMap<Spell, Integer> itemToNumberHashMap = new HashMap<>();

    public void sell(Account destinationAccount, Account targetAccount, Card card, int price) {
        for (String deckName : destinationAccount.getCollection().getDecks()) {
            Deck deck = destinationAccount.getCollection().getAllDecks().get(deckName);
            if (deck.getCardIDs().stream().filter(cardID -> cardID.equals(card.getID())).count() ==
                    destinationAccount.getCollection().getHowManyCard().get(card.getName())) {
                deck.getCardIDs().remove((Integer) card.getID());
                deck.setHero(deck.getHero() == card ? null : deck.getHero());
                deck.setItem(deck.getItem() == card ? null : deck.getItem());
                deck.minions.remove(card);
                deck.spells.remove(card);
            }
        }
        destinationAccount.derrick += price;
        destinationAccount.getCollection().getCardIDs().remove((Integer) card.getID());
        int keyValue = destinationAccount.getCollection().getHowManyCard().get(card.getName());
        destinationAccount.getCollection().getHowManyCard().put(card.getName(), keyValue - 1);
        changeNumberOfCardInShop(card, 1);
        if (targetAccount != null) {
            buy(targetAccount, card, price);
        }
    }

    public void buy(Account account, Card card, int price) {
        account.derrick -= price;
        account.getCollection().getCardIDs().add(card.getID());
        if (account.getCollection().getHowManyCard().containsKey(card.getName())) {
            int keyValue = account.getCollection().getHowManyCard().get(card.getName());
            account.getCollection().getHowManyCard().put(card.getName(), keyValue + 1);
        } else {
            account.getCollection().getHowManyCard().put(card.getName(), 1);
        }
        changeNumberOfCardInShop(card, -1);
    }

    public void changeNumberOfCardInShop(Card card, int extraNumber) {
        if (card instanceof Hero) {
            Hero hero = (Hero) card;
            heroesToNumberHashMap.put(hero, heroesToNumberHashMap.get(hero) + extraNumber);
        } else if (card instanceof Warrior) {
            Warrior minion = (Warrior) card;
            minionToNumberHashMap.put(minion, minionToNumberHashMap.get(minion) + extraNumber);
        } else if (Spell.checkIsItem(card)) {
            Spell item = (Spell) card;
            itemToNumberHashMap.put(item, itemToNumberHashMap.get(item) + extraNumber);
        } else {
            Spell spell = (Spell) card;
            spellToNumberHashMap.put(spell, spellToNumberHashMap.get(spell) + extraNumber);
        }
    }

    public int getNumberOfCardAtAll(Card card) {
        return getNumberOfCardInShop(card) + Account.getUsernameToAccount().values().stream().filter
                (account -> account.getCollection().getCardIDs().contains(card.getID()))
                .mapToInt(account -> account.getCollection().getHowManyCard().get(card.getName())).sum();
    }

    public int getNumberOfCardInShop(Card card) {
        if (card instanceof Hero) {
            Hero hero = (Hero) card;
            return heroesToNumberHashMap.get(hero);
        } else if (card instanceof Warrior) {
            Warrior minion = (Warrior) card;
            return minionToNumberHashMap.get(minion);
        } else if (Spell.checkIsItem(card)) {
            Spell item = (Spell) card;
            return itemToNumberHashMap.get(item);
        } else {
            Spell spell = (Spell) card;
            return spellToNumberHashMap.get(spell);
        }
    }

    public void addNewCardToShop(Card card) {
        if (card instanceof Hero) {
            Hero hero = (Hero) card;
            heroesToNumberHashMap.put(hero, baseNumberOfACardInShop);
        } else if (card instanceof Warrior) {
            Warrior minion = (Warrior) card;
            minionToNumberHashMap.put(minion, baseNumberOfACardInShop);
        } else if (Spell.checkIsItem(card)) {
            Spell item = (Spell) card;
            itemToNumberHashMap.put(item, baseNumberOfACardInShop);
        } else {
            Spell spell = (Spell) card;
            spellToNumberHashMap.put(spell, baseNumberOfACardInShop);
        }
        cardIDs.add(card.getID());
    }

    public ArrayList<Integer> getCardIDs() {
        return cardIDs;
    }

    public static Shop getShop() {
        return shop;
    }

    public HashMap<Warrior, Integer> getMinionToNumberHashMapForShop() {
        return minionToNumberHashMap;
    }

    public HashMap<Hero, Integer> getHeroesToNumberHashMapForShop() {
        return heroesToNumberHashMap;
    }

    public HashMap<Spell, Integer> getSpellToNumberHashMapForShop() {
        return spellToNumberHashMap;
    }

    public HashMap<Spell, Integer> getItemToNumberHashMapForShop() {
        return itemToNumberHashMap;
    }

    public void saveShop() {
        DBMethods.initDB("Shop");
        for (Map.Entry<Warrior, Integer> entry : minionToNumberHashMap.entrySet()) {
            DBMethods.put("Shop", entry.getKey().getName(), entry.getValue().toString());
        }
        for (Map.Entry<Hero, Integer> entry : heroesToNumberHashMap.entrySet()) {
            DBMethods.put("Shop", entry.getKey().getName(), entry.getValue().toString());
        }
        for (Map.Entry<Spell, Integer> entry : spellToNumberHashMap.entrySet()) {
            DBMethods.put("Shop", entry.getKey().getName(), entry.getValue().toString());
        }
        for (Map.Entry<Spell, Integer> entry : itemToNumberHashMap.entrySet()) {
            DBMethods.put("Shop", entry.getKey().getName(), entry.getValue().toString());
        }
    }

    public void loadShop() {//todo ali ask hashem about these functions
        try {
            for (Warrior minion : CardFactory.getAllBuiltMinions()) {
                minionToNumberHashMap.put(minion, Integer.parseInt(DBMethods.get("Shop", minion.getName())));
                DBMethods.delete("Shop", minion.getName());
            }
            for (Hero hero : CardFactory.getAllBuiltHeroes()) {
                heroesToNumberHashMap.put(hero, Integer.parseInt(DBMethods.get("Shop", hero.getName())));
                DBMethods.delete("Shop", hero.getName());
            }
            for (Spell spell : CardFactory.getAllBuiltSpells()) {
                spellToNumberHashMap.put(spell, Integer.parseInt(DBMethods.get("Shop", spell.getName())));
                DBMethods.delete("Shop", spell.getName());
            }
            for (Spell item : CardFactory.getAllBuiltItems()) {
                itemToNumberHashMap.put(item, Integer.parseInt(DBMethods.get("Shop", item.getName())));
                DBMethods.delete("Shop", item.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("couldn't read shop from file");
            minionToNumberHashMap = new HashMap<>();
            for (Warrior minion : CardFactory.getAllBuiltMinions()) {
                minionToNumberHashMap.put(minion, baseNumberOfACardInShop);
            }
            heroesToNumberHashMap = new HashMap<>();
            for (Hero hero : CardFactory.getAllBuiltHeroes()) {
                heroesToNumberHashMap.put(hero, baseNumberOfACardInShop);
            }
            spellToNumberHashMap = new HashMap<>();
            for (Spell spell : CardFactory.getAllBuiltSpells()) {
                spellToNumberHashMap.put(spell, baseNumberOfACardInShop);
            }
            itemToNumberHashMap = new HashMap<>();
            for (Spell item : CardFactory.getAllBuiltItems()) {
                itemToNumberHashMap.put(item, baseNumberOfACardInShop);
            }
        }
    }
}
