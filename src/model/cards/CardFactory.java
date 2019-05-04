package model.cards;

import model.actions.triggeractions.Applier;
import model.actions.triggeractions.Dispeller;
import model.conditions.HasAttacked;
import model.conditions.HasDied;
import model.conditions.HasSpawned;
import model.effects.*;
import model.targets.triggertargets.*;
import model.triggers.*;

import java.util.ArrayList;

public class CardFactory {
    ArrayList<Warrior> allBuiltWarriors = new ArrayList<>();

    public void makeAllMinions() {
        {
            Warrior warrior = new Warrior(21, "Kamandar-E-Fars", 300, 2, 6, 4);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));
            warrior.descriptionOfSpecialPower = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(22, "Shamshirzan-E-Fars", 400, 2, 6, 4);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Applier(), new AttackedGetter());
            trigger.getTriggers().add(new Stun(1, Dispelablity.BAD));
            warrior.getTriggers().add(trigger);

            warrior.descriptionOfSpecialPower = "Stun Enemy for next round";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(23, "Neyzedar-E-Fars", 500, 1, 5, 3);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));

            warrior.descriptionOfSpecialPower = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(24, "Asbsavar-E-Fars", 200, 4, 10, 6);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.descriptionOfSpecialPower = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(25, "Pahlevan-E-Fars", 600, 9, 24, 6);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getTriggers().add(new WoundDeepener(-1, Dispelablity.BAD));

            warrior.descriptionOfSpecialPower = "Deal 5 more damage for every turn that has attacked one enemy";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(26, "Sepahsalar-E-Fars", 800, 7, 12, 4);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getEffects().add(new Combo(-1, Dispelablity.UNDISPELLABLE));

            warrior.descriptionOfSpecialPower = "Deal 4 more damage for every Persian soldier that participate in attack (except itself)";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(27, "Kamandar-E-Toorani", 500, 1, 3, 4);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));

            warrior.descriptionOfSpecialPower = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(28, "Ghollabsangdar-E-Toorani", 600, 1, 4, 2);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));

            warrior.descriptionOfSpecialPower = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(29, "Neyzedar-E-Toorani", 600, 1, 4, 4);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));

            warrior.descriptionOfSpecialPower = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(210, "Jasoos-E-Toorani", 700, 4, 6, 6);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Applier(), new AttackedGetter());
            trigger.getTriggers().add(new Disarm(1, Dispelablity.BAD));
            trigger.getTriggers().add(new Poisoned(4, Dispelablity.BAD));

            warrior.descriptionOfSpecialPower = "disarm enemy for one turn and poison enemy for 4 turns";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(211, "Gorzdar-E-Toorani", 450, 2, 3, 100);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.descriptionOfSpecialPower = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(212, "Shahzade-E-Toorani", 800, 6, 6, 10);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getEffects().add(new Combo(-1, Dispelablity.UNDISPELLABLE));

            warrior.descriptionOfSpecialPower = "Deal 4 more damage for every Turanian soldier that participate in attack (except itself)";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(213, "Div-E-Siah", 300, 9, 14, 10);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));

            warrior.descriptionOfSpecialPower = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(214, "Ghool-E-Sangandaz", 300, 9, 14, 10);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));

            warrior.descriptionOfSpecialPower = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(215, "Oghab", 200, 2, 0, 2);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));

            warrior.getEffects().add(new HP(-1, Dispelablity.GOOD, 10));

            warrior.descriptionOfSpecialPower = "Has 10 power buff with increasiing health";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(216, "Div-E-Gorazsavar", 300, 6, 16, 8);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.descriptionOfSpecialPower = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(217, "Ghool-E-Takcheshm", 500, 7, 12, 11);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasDied());
            trigger.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -2));
            trigger.getActions().put(new Applier(), new NearbyGetter(false, false));

            warrior.descriptionOfSpecialPower = "Deals 2 damage to every minion in 8 adjacent spaces on death";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(218, "Mar-E-Sammi", 300, 4, 5, 6);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 4));

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getTriggers().add(new Poisoned(4, Dispelablity.BAD));
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Applier(), new AttackedGetter());

            warrior.descriptionOfSpecialPower = "Poison enemy for 3 turns";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(219, "Ejhdeha-E-Atashandaz", 250, 5, 9, 5);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 4));

            warrior.descriptionOfSpecialPower = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(220, "Shir-E-Darrande", 600, 2, 1, 8);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getEffects().add(new AntiHolyBuff(-1, Dispelablity.UNDISPELLABLE));

            warrior.descriptionOfSpecialPower = "Holy buff doesn't affect its attack";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(221, "Mar-E-Ghoolpeykar", 500, 8, 14, 7);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasSpawned());
            trigger.getTriggers().add(new HolyBuff(-1, Dispelablity.BAD, -1));
            trigger.getActions().put(new Applier(), new WithinDistanceGetter(false, 2, false));

            warrior.descriptionOfSpecialPower = "Minions in 2 spaces distance take 1 more damage when attacked (permanently)";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(222, "Goorg-E-Sefid", 400, 5, 8, 2);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Applier(), new AttackedGetter());
            TimedEffectBomb teb1 = new TimedEffectBomb(1, Dispelablity.BAD);
            teb1.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -6));
            TimedEffectBomb teb2 = new TimedEffectBomb(2, Dispelablity.BAD);
            teb2.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -4));
            trigger.getTriggers().add(teb1);
            trigger.getTriggers().add(teb2);

            warrior.descriptionOfSpecialPower = "When attack a minion the minion loses 6 health in next turn and 4 health in turn after that";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(223, "Palang", 400, 4, 6, 2);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Applier(), new AttackedGetter());
            TimedEffectBomb teb = new TimedEffectBomb(1, Dispelablity.BAD);
            teb.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -8));
            trigger.getTriggers().add(teb);
            warrior.getTriggers().add(trigger);

            warrior.descriptionOfSpecialPower = "When attack a minion the minion loses 8 health in next round";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(224, "Gorg", 400, 3, 6, 1);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Applier(), new AttackedGetter());
            TimedEffectBomb teb = new TimedEffectBomb(1, Dispelablity.BAD);
            teb.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -6));
            trigger.getTriggers().add(teb);
            warrior.getTriggers().add(trigger);

            warrior.descriptionOfSpecialPower = "When attack a minion the minion loses 6 health in next turn";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(225, "Jadoogar", 550, 4, 5, 4);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));

            Aura aura = new Aura(-1, Dispelablity.UNDISPELLABLE
                    , new NearbyGetter(true, false).and(new TriggerOwnerGetter()));
            aura.getEffects().add(new AP(1, Dispelablity.GOOD, 2));
            aura.getEffects().add(new HP(1, Dispelablity.BAD, -1));
            warrior.getTriggers().add(aura);

            warrior.descriptionOfSpecialPower = "Gives a Power buff with +2 AP and a Weakness buff with -1 HP to itself and every friendly minion in 8 adjacent spaces";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(226, "Jadoogar-E-Aazam", 550, 6, 6, 6);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));

            Aura aura = new Aura(-1, Dispelablity.UNDISPELLABLE, new NearbyGetter(true, false)
                    .and(new TriggerOwnerGetter()));
            aura.getEffects().add(new AP(1, Dispelablity.GOOD, 2));
            aura.getTriggers().add(new HolyBuff(1, Dispelablity.GOOD, 1));
            warrior.getTriggers().add(aura);

            warrior.descriptionOfSpecialPower = "Gives a Power buff with +2 AP and a Holy buff to every friendly minion in 8 adjacent spaces";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(227, "Jen", 500, 5, 10, 4);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 4));

            Aura aura = new Aura(-1, Dispelablity.UNDISPELLABLE, new AllWarriorsGetter(true, false));
            aura.getEffects().add(new AP(1, Dispelablity.GOOD, 1));
            warrior.getTriggers().add(aura);

            warrior.descriptionOfSpecialPower = "Gives all friendly minions a passive Power buff with +1 AP";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(228, "Goraz-E-Vahshi", 500, 6, 10, 14);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getTriggers().add(new EffTriggImmune(-1, Dispelablity.UNDISPELLABLE, Disarm.class));

            warrior.descriptionOfSpecialPower = "can't be disarmed";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(229, "Piran", 400, 8, 20, 12);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getTriggers().add(new EffTriggImmune(-1, Dispelablity.UNDISPELLABLE, Poisoned.class));

            warrior.descriptionOfSpecialPower = "can't be poisoned";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(230, "Giv", 450, 4, 5, 7);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));

            warrior.getTriggers().add(new SpellImmune(-1, Dispelablity.UNDISPELLABLE));

            warrior.descriptionOfSpecialPower = "doesn't take negative affects from cards";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(231, "Bahman", 450, 8, 16, 9);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasSpawned());
            trigger.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -16));
            trigger.getActions().put(new Applier(), new RandomGetter(new AllWarriorsGetter(false, false)));
            warrior.getTriggers().add(trigger);

            warrior.descriptionOfSpecialPower = "Deals 16 damage to a random enemy minion";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(232, "Ashkboos", 400, 7, 14, 8);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getTriggers().add(new LowerAttackCanceller(-1, Dispelablity.UNDISPELLABLE));

            warrior.descriptionOfSpecialPower = "Doesn't take damage from minions with less AP";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(233, "Iraj", 500, 4, 6, 20);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));

            warrior.descriptionOfSpecialPower = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(234, "Ghool-E-Bozorg", 600, 9, 30, 8);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 2));

            warrior.descriptionOfSpecialPower = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(235, "Ghool-E-Dosar", 550, 4, 10, 4);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Dispeller(), new AttackedGetter());
            warrior.getTriggers().add(trigger);

            warrior.descriptionOfSpecialPower = "Disables every positive affects of attacked minion";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(236, "Nane-Sarma", 500, 3, 3, 4);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasSpawned());
            trigger.getActions().put(new Applier(), new NearbyGetter(false, false));
            trigger.getTriggers().add(new Stun(1, Dispelablity.BAD));
            warrior.getTriggers().add(trigger);

            warrior.descriptionOfSpecialPower = "Stun enemy minions in 8 adjacent spaces for one turn";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(237, "Foolad-Zereh", 650, 3, 1, 1);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            Aura aura = new Aura(-1, Dispelablity.UNDISPELLABLE, new TriggerOwnerGetter());
            aura.getTriggers().add(new HolyBuff(1, Dispelablity.GOOD, 12));
            warrior.getTriggers().add(aura);

            warrior.descriptionOfSpecialPower = "Turn itself to a random enemy minion";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(238, "Siavash", 350, 4, 8, 5);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasDied());
            trigger.getActions().put(new Applier(), new HeroGetter(false));
            trigger.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -6));

            warrior.descriptionOfSpecialPower = "Deals 6 damage to enemy Hero on death";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(239, "ShahGhool", 600, 5, 10, 4);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getEffects().add(new Combo(-1, Dispelablity.UNDISPELLABLE));

            warrior.descriptionOfSpecialPower = "for every giant that participates in attack (except itself) disarms enemy for one turn";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(240, "Arzhang-E-Div", 600, 3, 6, 6);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getEffects().add(new Combo(-1, Dispelablity.UNDISPELLABLE));

            warrior.descriptionOfSpecialPower = "Gives a Weakness buff with -1 AP to enemy and for every demon (except itself and white demon) adds -3 to Weakness buff";
            allBuiltWarriors.add(warrior);
        }
    }

    public void makeAllSpells() {

    }
}
