package model.cards;

import model.cards.warriors.Warrior;
import model.effects.Effect;

import java.util.ArrayList;

public class CardFactory {
    ArrayList<Warrior> allBuildedWarrioirs = new ArrayList<>();

    public void makeAllCards() {
        {
            Warrior warrior = new Warrior(21, "Kamandar-E-Fars", 300, 2, 6, 4, false);
//            warrior.getEffects().add(new );
        }
    }
}
