package model.cards;

import model.actions.Applier;
import model.actions.Dispeller;
import model.conditions.HasAttacked;
import model.conditions.HasDied;
import model.conditions.HasSpawned;
import model.effects.*;
import model.targets.*;
import model.triggers.*;

import java.util.ArrayList;

public class CardFactory {
    ArrayList<Warrior> allBuiltWarriors = new ArrayList<>();
    ArrayList<Spell> allBuiltSpells = new ArrayList<>();
    ArrayList<Hero> allBuiltHeroes = new ArrayList<>();
    ArrayList<Spell> allBuiltItems = new ArrayList<>();

    public void makeAllMinions() {
        {
            Warrior warrior = new Warrior(21, "Kamandar-E-Fars", 300, 2, 6, 4);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));


            warrior.description.descriptionOfCardSpecialAbility = "None";
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


            warrior.description.descriptionOfCardSpecialAbility = "Stun Enemy for next round";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(23, "Neyzedar-E-Fars", 500, 1, 5, 3);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(24, "Asbsavar-E-Fars", 200, 4, 10, 6);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(25, "Pahlevan-E-Fars", 600, 9, 24, 6);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getTriggers().add(new WoundDeepener(-1, Dispelablity.BAD));


            warrior.description.descriptionOfCardSpecialAbility = "Deal 5 more damage for every turn that has attacked one enemy";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(26, "Sepahsalar-E-Fars", 800, 7, 12, 4);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getEffects().add(new Combo(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "Deal 4 more damage for every Persian soldier that participate in attack (except itself)";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(27, "Kamandar-E-Toorani", 500, 1, 3, 4);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(28, "Ghollabsangdar-E-Toorani", 600, 1, 4, 2);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(29, "Neyzedar-E-Toorani", 600, 1, 4, 4);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));


            warrior.description.descriptionOfCardSpecialAbility = "None";
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


            warrior.description.descriptionOfCardSpecialAbility = "disarm enemy for one turn and poison enemy for 4 turns";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(211, "Gorzdar-E-Toorani", 450, 2, 3, 100);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(212, "Shahzade-E-Toorani", 800, 6, 6, 10);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getEffects().add(new Combo(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "Deal 4 more damage for every Turanian soldier that participate in attack (except itself)";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(213, "Div-E-Siah", 300, 9, 14, 10);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(214, "Ghool-E-Sangandaz", 300, 9, 14, 10);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(215, "Oghab", 200, 2, 0, 2);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));

            warrior.getEffects().add(new HP(-1, Dispelablity.GOOD, 10));


            warrior.description.descriptionOfCardSpecialAbility = "Has 10 power buff with increasiing health";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(216, "Div-E-Gorazsavar", 300, 6, 16, 8);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "None";
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


            warrior.description.descriptionOfCardSpecialAbility = "Deals 2 damage to every minion in 8 adjacent spaces on death";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(218, "Mar-E-Sammi", 300, 4, 5, 6);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 4));
            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getTriggers().add(new Poisoned(4, Dispelablity.BAD));
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Applier(), new AttackedGetter());


            warrior.description.descriptionOfCardSpecialAbility = "Poison enemy for 3 turns";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(219, "Ejhdeha-E-Atashandaz", 250, 5, 9, 5);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 4));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(220, "Shir-E-Darrande", 600, 2, 1, 8);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getEffects().add(new AntiHolyBuff(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "Holy buff doesn't affect its attack";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(221, "Mar-E-Ghoolpeykar", 500, 8, 14, 7);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));
            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasSpawned());
            trigger.getTriggers().add(new HolyBuff(-1, Dispelablity.BAD, -1));
            trigger.getActions().put(new Applier(), new WithinDistanceGetter(false, 2, false));


            warrior.description.descriptionOfCardSpecialAbility = "Minions in 2 spaces distance take 1 more damage when attacked (permanently)";
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


            warrior.description.descriptionOfCardSpecialAbility = "When attack a minion the minion loses 6 health in next turn and 4 health in turn after that";
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


            warrior.description.descriptionOfCardSpecialAbility = "When attack a minion the minion loses 8 health in next round";
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


            warrior.description.descriptionOfCardSpecialAbility = "When attack a minion the minion loses 6 health in next turn";
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


            warrior.description.descriptionOfCardSpecialAbility = "Gives a Power buff with +2 AP and a Weakness buff with -1 HP to itself and every friendly minion in 8 adjacent spaces";
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


            warrior.description.descriptionOfCardSpecialAbility = "Gives a Power buff with +2 AP and a Holy buff to every friendly minion in 8 adjacent spaces";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(227, "Jen", 500, 5, 10, 4);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 4));
            Aura aura = new Aura(-1, Dispelablity.UNDISPELLABLE, new AllWarriorsGetter(true, false));
            aura.getEffects().add(new AP(1, Dispelablity.GOOD, 1));
            warrior.getTriggers().add(aura);


            warrior.description.descriptionOfCardSpecialAbility = "Gives all friendly minions a passive Power buff with +1 AP";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(228, "Goraz-E-Vahshi", 500, 6, 10, 14);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getTriggers().add(new EffTriggImmune(-1, Dispelablity.UNDISPELLABLE, Disarm.class));


            warrior.description.descriptionOfCardSpecialAbility = "can't be disarmed";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(229, "Piran", 400, 8, 20, 12);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getTriggers().add(new EffTriggImmune(-1, Dispelablity.UNDISPELLABLE, Poisoned.class));


            warrior.description.descriptionOfCardSpecialAbility = "can't be poisoned";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(230, "Giv", 450, 4, 5, 7);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));

            warrior.getTriggers().add(new SpellImmune(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "doesn't take negative affects from cards";
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


            warrior.description.descriptionOfCardSpecialAbility = "Deals 16 damage to a random enemy minion";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(232, "Ashkboos", 400, 7, 14, 8);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getTriggers().add(new LowerAttackCanceller(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "Doesn't take damage from minions with less AP";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(233, "Iraj", 500, 4, 6, 20);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(234, "Ghool-E-Bozorg", 600, 9, 30, 8);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 2));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(235, "Ghool-E-Dosar", 550, 4, 10, 4);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Dispeller(), new AttackedGetter());
            warrior.getTriggers().add(trigger);


            warrior.description.descriptionOfCardSpecialAbility = "Disables every positive affects of attacked minion";
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


            warrior.description.descriptionOfCardSpecialAbility = "Stun enemy minions in 8 adjacent spaces for one turn";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(237, "Foolad-Zereh", 650, 3, 1, 1);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            Aura aura = new Aura(-1, Dispelablity.UNDISPELLABLE, new TriggerOwnerGetter());
            aura.getTriggers().add(new HolyBuff(1, Dispelablity.GOOD, 12));
            warrior.getTriggers().add(aura);


            warrior.description.descriptionOfCardSpecialAbility = "Turn itself to a random enemy minion";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(238, "Siavash", 350, 4, 8, 5);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasDied());
            trigger.getActions().put(new Applier(), new HeroGetter(false));
            trigger.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -6));


            warrior.description.descriptionOfCardSpecialAbility = "Deals 6 damage to enemy Hero on death";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(239, "ShahGhool", 600, 5, 10, 4);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getEffects().add(new Combo(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "for every giant that participates in attack (except itself) disarms enemy for one turn";
            allBuiltWarriors.add(warrior);
        }
        {
            Warrior warrior = new Warrior(240, "Arzhang-E-Div", 600, 3, 6, 6);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getEffects().add(new Combo(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "Gives a Weakness buff with -1 AP to enemy and for every demon (except itself and white demon) adds -3 to Weakness buff";
            allBuiltWarriors.add(warrior);
        }
    }

    public void makeAllSpells() {
        {
            Spell spell = new Spell(11, "TotalDisarm", 1000, 0, false);

            spell.description.targetType = "one enemy";
            spell.description.descriptionOfCardSpecialAbility = "Disarm to the end of game";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(12, "AreaDispel", 1500, 2, false);

            spell.description.targetType = "square 2*2";
            spell.description.descriptionOfCardSpecialAbility = "it delete enemy's positive buffs and our negative buffs";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(13, "Empower", 250, 1, false);

            spell.description.targetType = "one friend";
            spell.description.descriptionOfCardSpecialAbility = "increase hit power of one person 2 units";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(14, "Fireball", 400, 1, false);

            spell.description.targetType = "one enemy";
            spell.description.descriptionOfCardSpecialAbility = "hit 4 unit to one enemy";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(15, "GodStrength", 450, 2, false);

            spell.description.targetType = "hero friend";
            spell.description.descriptionOfCardSpecialAbility = "increase hit power of one hero 4 units";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(16, "HellFire", 600, 3, false);

            spell.description.targetType = "square 2*2";
            spell.description.descriptionOfCardSpecialAbility = "make fiery effect in 2 cells for 2 turns";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(17, "LightingBolt", 1250, 2, false);

            spell.description.targetType = "hero enemy";
            spell.description.descriptionOfCardSpecialAbility = "hit 8 units to the hero";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(18, "PoisonLake", 900, 5, false);

            spell.description.targetType = "square 3*3";
            spell.description.descriptionOfCardSpecialAbility = "make poisoned 8 cells for one turn";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(19, "Madness", 650, 0, false);

            spell.description.targetType = "one friend";
            spell.description.descriptionOfCardSpecialAbility = "increase hit power of one person 4 units for 3 turns but it disarm";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(110, "AllDisarm", 2000, 9, false);

            spell.description.targetType = "all enemies";
            spell.description.descriptionOfCardSpecialAbility = "disarm for one turn";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(111, "AllPoison", 1500, 8, false);

            spell.description.targetType = "all enemies";
            spell.description.descriptionOfCardSpecialAbility = "all heroes poisoned for 4 turns";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(112, "Dispel", 2100, 0, false);

            spell.description.targetType = "one friend or enemy";
            spell.description.descriptionOfCardSpecialAbility = "it delete enemy's positive buffs and our negative buffs";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(113, "HealthWithProfit", 2250, 0, false);

            spell.description.targetType = "one friend";
            spell.description.descriptionOfCardSpecialAbility = "Gives a weakness buff -6 HP but also gives 2 holy buffes for 3 turns";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(114, "GhazaBokhorJoonBegiri", 2500, 2, false);

            spell.description.targetType = "one friend";
            spell.description.descriptionOfCardSpecialAbility = "Gives power buff +6 AP";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(115, "AllPower", 2000, 4, false);

            spell.description.targetType = "all friends";
            spell.description.descriptionOfCardSpecialAbility = "Gives power buff +6 AP";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(116, "AllAttack", 1500, 4, false);

            spell.description.targetType = "all enemies in one column";
            spell.description.descriptionOfCardSpecialAbility = "hit all enemies 6 units";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(117, "Weakening", 1000, 1, false);

            spell.description.targetType = "one minion enemy";
            spell.description.descriptionOfCardSpecialAbility = "Gives weakness buff -4 AP";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(118, "Sacrifice", 1600, 3, false);

            spell.description.targetType = "one minion friend";
            spell.description.descriptionOfCardSpecialAbility = "Gives weakness buff -6 HP and power buff +8 AP";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(119, "KingsGaurd", 1750, 3, false);

            spell.description.targetType = "random enemy minion around hero";
            spell.description.descriptionOfCardSpecialAbility = "killes enemy";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(120, "Shock", 1200, 1, false);


            spell.description.targetType = "one enemy";
            spell.description.descriptionOfCardSpecialAbility = "stun for 2 turns";
            allBuiltSpells.add(spell);
        }
    }

    public void makeAllHeroes() {
        {
            Hero hero = new Hero(31, "Div_E_Sefid", 8000, 50, 4, -1);

            hero.description.descriptionOfCardSpecialAbility = "Apply power buff with 4 point additional attack damage on himself";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(32, "Simorgh", 9000, 50, 4, -1);

            hero.description.descriptionOfCardSpecialAbility = "Make 8 cell around firable and apply holy buff on himself for 2 round";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(33, "Ezhdeha-E-Haftsar", 8000, 50, 4, -1);

            hero.description.descriptionOfCardSpecialAbility = "Disarm one person";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(34, "Rakhsh", 8000, 50, 4, -1);

            hero.description.descriptionOfCardSpecialAbility = "Stun one enemy for 1 round";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(35, "Zahhak", 10000, 50, 2, -1);

            hero.description.descriptionOfCardSpecialAbility = "Add 2 snakes with 1/1 randomly around himself";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(36, "Kaveh", 8000, 50, 4, -1);

            hero.description.descriptionOfCardSpecialAbility = "Make one cell saint for 3 turns";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(37, "Arash", 10000, 30, 2, -1);

            hero.description.descriptionOfCardSpecialAbility = "Add 4 point to all minions in hero's row";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(38, "Afsane", 11000, 40, 3, -1);

            hero.description.descriptionOfCardSpecialAbility = "Dispel one enemy";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(39, "Esfandiyar", 12000, 35, 3, -1);

            hero.description.descriptionOfCardSpecialAbility = "Have 3 passive holy buff";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(310, "Rostam", 8000, 55, 7, -1);

            hero.description.descriptionOfCardSpecialAbility = "None";
            allBuiltHeroes.add(hero);
        }
    }

    public void makeAllItems() {
        {
            Spell item = new Spell(41, "Taj-E-Daanayi", -1, 300, true);

            item.description.descriptionOfCardSpecialAbility = "increasing mana from roand 3 onwards";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(42, "Namoos-E-Separ", -1, 4000, true);

            item.description.descriptionOfCardSpecialAbility = "activate holy buff in our hero in passive kind";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(43, "Kaman-E-Daamol", -1, 30000, true);

            item.description.descriptionOfCardSpecialAbility = "just for ranged and hybrid : increasing hero range 2 units";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(44, "NooshDaroo", -1, -1, true);

            item.description.descriptionOfCardSpecialAbility = "increasing HP 6 units";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(45, "Tir-E-DoShakh", -1, -1, true);

            item.description.descriptionOfCardSpecialAbility = "Hitting power of one ranged or increase hybrid 2 units";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(46, "Par-E-Simorgh", -1, 3500, true);

            item.description.descriptionOfCardSpecialAbility = "When for the first time our heroes HP reach less than 15, make its HP double";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(47, "Eksir", -1, -1, true);

            item.description.descriptionOfCardSpecialAbility = "Increase each of health and hitting power 3 units";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(48, "Maajoon-E-Mana", -1, -1, true);

            item.description.descriptionOfCardSpecialAbility = "Increase mana 3 units";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(49, "Maajoon-E-RooyinTanoo", -1, -1, true);

            item.description.descriptionOfCardSpecialAbility = "In two turn activate 10 holy buff inside ours";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(410, "Nefrin-E-Marg", -1, -1, true);

            item.description.descriptionOfCardSpecialAbility = "For one minion : at death time 8 hits to random nearest person";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(411, "RandomDamage", -1, -1, true);

            item.description.descriptionOfCardSpecialAbility = "Two hits to three random persons";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(412, "TerrorHood", -1, 5000, true);

            item.description.descriptionOfCardSpecialAbility = "Apply a WeaknessBuff with two decrement in AP of a random enemy";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(413, "BladesOfAgility", -1, -1, true);

            item.description.descriptionOfCardSpecialAbility = "Increase hit power six units";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(414, "KingWisdom", -1, 9000, true);

            item.description.descriptionOfCardSpecialAbility = "Take one mana in each turn and killed enemy hero after 15 turn";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(415, "AssassinationDagger", -1, 15000, true);

            item.description.descriptionOfCardSpecialAbility = "Just for ranged and hybrid: increase hit power when our hero hit enemy hero";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(416, "PoisonousDagger", -1, 7000, true);

            item.description.descriptionOfCardSpecialAbility = "Each of ours poison one enemy when hit";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(417, "ShockHammer", -1, 15000, true);

            item.description.descriptionOfCardSpecialAbility = "Just in one turn stun enemy when hit";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(418, "SoulEater", -1, 25000, true);

            item.description.descriptionOfCardSpecialAbility = "Just for melee: increase its HP 2 units when hit";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(419, "Ghosl-E-Taamid", -1, 20000, true);

            item.description.descriptionOfCardSpecialAbility = "When put each minion has holy buff until toe turn";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(420, "Shamshir-E-Chini", -1, -1, true);

            item.description.descriptionOfCardSpecialAbility = "Until warrior doesn't hit, for 5 times it hits 5 times more";
            allBuiltItems.add(item);
        }
    }
}
