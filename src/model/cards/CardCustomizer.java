package model.cards;

import model.Account;
import view.fxmlControllers.CustomCardController;

public class CardCustomizer {
    CustomCardController ccc;

    public CardCustomizer(CustomCardController ccc) {
        this.ccc = ccc;
    }

    public void build() {
        switch (ccc.getCardType()) {
            case "Hero":
                buildHero();
                break;
            case "Minion":
                buildMinion();
                break;
            case "Spell":
                buildSpell();
                break;
        }
    }

    void buildHero() {
        Hero hero = new Hero((int)(System.currentTimeMillis()%Integer.MAX_VALUE),ccc.getCardName(),ccc.getCardCost(),Integer.valueOf(ccc.getWarriorHP()),Integer.valueOf(ccc.getWarriorAP()),0);
    }

    void buildSpell() {

    }

    void buildMinion() {

    }
}