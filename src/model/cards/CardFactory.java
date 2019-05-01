package model.cards;

import model.actions.triggeraction.Applier;
import model.cards.warriors.Warrior;
import model.conditions.HasAttacked;
import model.effects.*;
import model.targets.AttackedGetter;
import model.triggers.Disarm;
import model.triggers.Stun;
import model.triggers.Trigger;

import java.util.ArrayList;

public class CardFactory {
    ArrayList<Warrior> allBuildedWarrioirs = new ArrayList<>();

    public void makeAllCards() {
        {
            Warrior warrior = new Warrior(21, "Kamandar-E-Fars", 300, 2, 6, 4, false);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(22, "Shamshirzan-E-Fars", 400, 2, 6, 4, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Applier(), new AttackedGetter());
            trigger.getTriggers().add(new Stun(1, Dispelablity.BAD));
            warrior.getTriggers().add(trigger);
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(23, "Neyzedar-E-Fars", 500, 1, 5, 3, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(24, "Asbsavar-E-Fars", 200, 4, 10, 6, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(25, "Pahlevan-E-Fars", 600, 9, 24, 6, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(26, "Sepahsalar-E-Fars", 800, 7, 12, 4, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(27, "Kamandar-E-Toorani", 500, 1, 3, 4, false);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(28, "Ghollabsangdar-E-Toorani", 600, 1, 4, 2, false);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(29, "Neyzedar-E-Toorani", 600, 1, 4, 4, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(210, "Jasoos-E-Toorani", 700, 4, 6, 6, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Applier(), new AttackedGetter());
            trigger.getTriggers().add(new Disarm(1, Dispelablity.BAD));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(211, "Gorzdar-E-Toorani", 450, 2, 3, 100, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(212, "Shahzade-E-Toorani", 800, 6, 6, 10, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(213, "Div-E-Siah", 300, 9, 14, 10, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(214, "Ghool-E-Sangandaz", 300, 9, 14, 10, false);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(215, "Oghab", 200, 2, 0, 2, false);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(216, "Div-E-Gorazsavar", 300, 6, 16, 8, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(217, "Ghool-E-Takcheshm", 500, 7, 12, 11, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(218, "Mar-E-Sammi", 300, 4, 5, 6, false);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 4));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(219, "Ejhdeha-E-Atashandaz", 250, 5, 9, 5, false);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 4));
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(220, "Shir-E-Darrande", 600, 2, 1, 8, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(221, "Mar-E-Ghoolpeykar", 500, 8, 14, 7, false);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(222, "Goorg-E-Sefid", 400, 5, 8, 2, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(223, "Palang", 400, 4, 6, 2, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(224, "Gorg", 400, 3, 6, 1, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(225, "Jadoogar", 550, 4, 5, 4, false);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(226, "Jadoogar-E-Aazam", 550, 6, 6, 6, false);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(227, "Jen", 500, 5, 10, 4, false);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 4));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(228, "Goraz-E-Vahshi", 500, 6, 10, 14, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(229, "Piran", 400, 8, 20, 12, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(230, "Giv", 450, 4, 5, 7, false);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(231, "Bahman", 450, 8, 16, 9, false);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(232,"Ashkboos",400,7,14,8,false);
            warrior.getEffects().add(new Melee(-1,Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(233,"Iraj",500,4,6,20,false);
            warrior.getEffects().add(new Ranged(-1,Dispelablity.UNDISPELLABLE,3));
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(234,"Ghool-E-Bozorg",600,9,30,8,false);
            warrior.getEffects().add(new Melee(-1,Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1,Dispelablity.UNDISPELLABLE,2));
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(235,"Ghool-E-Dosar",550,4,10,4,false);
            warrior.getEffects().add(new Melee(-1,Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(236,"Nane-Sarma",500,3,3,4,false);
            warrior.getEffects().add(new Ranged(-1,Dispelablity.UNDISPELLABLE,5));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(237,"Foolad-Zereh",650,3,1,1,false);
            warrior.getEffects().add(new Melee(-1,Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(238,"Siavash",350,4,8,5,false);
            warrior.getEffects().add(new Melee(-1,Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(239,"ShahGhool",600,5,10,4,false);
            warrior.getEffects().add(new Melee(-1,Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
        {
            Warrior warrior = new Warrior(240,"Arzhang-E-Div",600,3,6,6,false);
            warrior.getEffects().add(new Melee(-1,Dispelablity.UNDISPELLABLE));
            //todo
            allBuildedWarrioirs.add(warrior);
        }
    }
}
