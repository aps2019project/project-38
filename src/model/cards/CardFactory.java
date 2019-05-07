package model.cards;

import model.Cell;
import model.Constant;
import model.Game;
import model.QualityHaver;
import model.Shop;
import model.actions.Applier;
import model.actions.Dispeller;
import model.actions.Killer;
import model.conditions.*;
import model.effects.*;
import model.gamestate.AttackState;
import model.gamestate.DeathState;
import model.gamestate.GameState;
import model.gamestate.PutMinionState;
import model.player.Player;
import model.targets.*;
import model.triggers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PrimitiveIterator;

public class CardFactory {
    private static ArrayList<Card> allBuiltMinions = new ArrayList<>();
    private static ArrayList<Card> allBuiltSpells = new ArrayList<>();
    private static ArrayList<Card> allBuiltHeroes = new ArrayList<>();
    private static ArrayList<Card> allBuiltItems = new ArrayList<>();

    private static void makeAllMinions() {
        {
            Warrior warrior = new Warrior(21, "Kamandar-E-Fars", 300, 2, 6, 4);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltMinions.add(warrior);
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
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(23, "Neyzedar-E-Fars", 500, 1, 5, 3);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(24, "Asbsavar-E-Fars", 200, 4, 10, 6);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(25, "Pahlevan-E-Fars", 600, 9, 24, 6);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getTriggers().add(new WoundDeepener(-1, Dispelablity.BAD));


            warrior.description.descriptionOfCardSpecialAbility = "Deal 5 more damage for every turn that has attacked one enemy";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(26, "Sepahsalar-E-Fars", 800, 7, 12, 4);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getEffects().add(new Combo(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "Deal 4 more damage for every Persian soldier that participate in attack (except itself)";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(27, "Kamandar-E-Toorani", 500, 1, 3, 4);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(28, "Ghollabsangdar-E-Toorani", 600, 1, 4, 2);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(29, "Neyzedar-E-Toorani", 600, 1, 4, 4);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltMinions.add(warrior);
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
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(211, "Gorzdar-E-Toorani", 450, 2, 3, 10);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(212, "Shahzade-E-Toorani", 800, 6, 6, 10);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getEffects().add(new Combo(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "Deal 4 more damage for every Turanian soldier that participate in attack (except itself)";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(213, "Div-E-Siah", 300, 9, 14, 10);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(214, "Ghool-E-Sangandaz", 300, 9, 14, 10);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 7));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(215, "Oghab", 200, 2, 0, 2);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.GOOD, 3));

            warrior.getEffects().add(new HP(-1, Dispelablity.GOOD, 10));


            warrior.description.descriptionOfCardSpecialAbility = "Has 10 power buff with increasiing health";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(216, "Div-E-Gorazsavar", 300, 6, 16, 8);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(217, "Ghool-E-Takcheshm", 500, 7, 12, 11);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));
            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasDied());
            trigger.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -2));
            trigger.getActions().put(new Applier(), new AdjacentGetter(false, false));


            warrior.description.descriptionOfCardSpecialAbility = "Deals 2 damage to every minion in 8 adjacent spaces on death";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(218, "Mar-E-Sammi", 300, 4, 5, 6);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 4));
            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getTriggers().add(new Poisoned(4, Dispelablity.BAD));
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Applier(), new AttackedGetter());


            warrior.description.descriptionOfCardSpecialAbility = "Poison enemy for 3 turns";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(219, "Ejhdeha-E-Atashandaz", 250, 5, 9, 5);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 4));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(220, "Shir-E-Darrande", 600, 2, 1, 8);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getEffects().add(new AntiHolyBuff(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "Holy buff doesn't affect its attack";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(221, "Mar-E-Ghoolpeykar", 500, 8, 14, 7);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));
            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasSpawned());
            trigger.getTriggers().add(new HolyBuff(-1, Dispelablity.BAD, -1));
            trigger.getActions().put(new Applier(), new WithinDistanceGetter(false, 2, false));


            warrior.description.descriptionOfCardSpecialAbility = "Minions in 2 spaces distance take 1 more damage when attacked (permanently)";
            allBuiltMinions.add(warrior);
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
            allBuiltMinions.add(warrior);
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
            allBuiltMinions.add(warrior);
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
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(225, "Jadoogar", 550, 4, 5, 4);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));
            Aura aura = new Aura(-1, Dispelablity.UNDISPELLABLE
                    , new AdjacentGetter(true, false).and(new TriggerOwnerGetter()));
            aura.getEffects().add(new AP(1, Dispelablity.GOOD, 2));
            aura.getEffects().add(new HP(1, Dispelablity.BAD, -1));
            warrior.getTriggers().add(aura);


            warrior.description.descriptionOfCardSpecialAbility = "Gives a Power buff with +2 AP and a Weakness buff with -1 HP to itself and every friendly minion in 8 adjacent spaces";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(226, "Jadoogar-E-Aazam", 550, 6, 6, 6);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));
            Aura aura = new Aura(-1, Dispelablity.UNDISPELLABLE, new AdjacentGetter(true, false)
                    .and(new TriggerOwnerGetter()));
            aura.getEffects().add(new AP(1, Dispelablity.GOOD, 2));
            aura.getTriggers().add(new HolyBuff(1, Dispelablity.GOOD, 1));
            warrior.getTriggers().add(aura);


            warrior.description.descriptionOfCardSpecialAbility = "Gives a Power buff with +2 AP and a Holy buff to every friendly minion in 8 adjacent spaces";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(227, "Jen", 500, 5, 10, 4);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 4));
            Aura aura = new Aura(-1, Dispelablity.UNDISPELLABLE, new AllWarriorsGetter(true, false));
            aura.getEffects().add(new AP(1, Dispelablity.GOOD, 1));
            warrior.getTriggers().add(aura);


            warrior.description.descriptionOfCardSpecialAbility = "Gives all friendly minions a passive Power buff with +1 AP";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(228, "Goraz-E-Vahshi", 500, 6, 10, 14);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getTriggers().add(new EffTriggImmune(-1, Dispelablity.UNDISPELLABLE, Disarm.class));


            warrior.description.descriptionOfCardSpecialAbility = "can't be disarmed";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(229, "Piran", 400, 8, 20, 12);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getTriggers().add(new EffTriggImmune(-1, Dispelablity.UNDISPELLABLE, Poisoned.class));


            warrior.description.descriptionOfCardSpecialAbility = "can't be poisoned";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(230, "Giv", 450, 4, 5, 7);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));

            warrior.getTriggers().add(new SpellImmune(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "doesn't take negative affects from cards";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(231, "Bahman", 450, 8, 16, 9);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasSpawned());
            trigger.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -16));
            trigger.getActions().put(new Applier(), new RandomGetter((TriggerTarget) new AllWarriorsGetter(false, false)));
            warrior.getTriggers().add(trigger);


            warrior.description.descriptionOfCardSpecialAbility = "Deals 16 damage to a random enemy minion";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(232, "Ashkboos", 400, 7, 14, 8);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getTriggers().add(new LowerAttackCanceller(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "Doesn't take damage from minions with less AP";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(233, "Iraj", 500, 4, 6, 20);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 3));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(234, "Ghool-E-Bozorg", 600, 9, 30, 8);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 2));


            warrior.description.descriptionOfCardSpecialAbility = "None";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(235, "Ghool-E-Dosar", 550, 4, 10, 4);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Dispeller(), new AttackedGetter());
            warrior.getTriggers().add(trigger);


            warrior.description.descriptionOfCardSpecialAbility = "Disables every positive affects of attacked minion";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(236, "Nane-Sarma", 500, 3, 3, 4);
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, 5));
            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasSpawned());
            trigger.getActions().put(new Applier(), new AdjacentGetter(false, false));
            trigger.getTriggers().add(new Stun(1, Dispelablity.BAD));
            warrior.getTriggers().add(trigger);


            warrior.description.descriptionOfCardSpecialAbility = "Stun enemy minions in 8 adjacent spaces for one turn";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(237, "Foolad-Zereh", 650, 3, 1, 1);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            Aura aura = new Aura(-1, Dispelablity.UNDISPELLABLE, new TriggerOwnerGetter());
            aura.getTriggers().add(new HolyBuff(1, Dispelablity.GOOD, 12));
            warrior.getTriggers().add(aura);


            warrior.description.descriptionOfCardSpecialAbility = "Turn itself to a random enemy minion";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(238, "Siavash", 350, 4, 8, 5);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasDied());
            trigger.getActions().put(new Applier(), new HeroGetter(false));
            trigger.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -6));


            warrior.description.descriptionOfCardSpecialAbility = "Deals 6 damage to enemy Hero on death";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(239, "ShahGhool", 600, 5, 10, 4);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getEffects().add(new Combo(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "for every giant that participates in attack (except itself) disarms enemy for one turn";
            allBuiltMinions.add(warrior);
        }
        {
            Warrior warrior = new Warrior(240, "Arzhang-E-Div", 600, 3, 6, 6);
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));

            warrior.getEffects().add(new Combo(-1, Dispelablity.UNDISPELLABLE));


            warrior.description.descriptionOfCardSpecialAbility = "Gives a Weakness buff with -1 AP to enemy and for every demon (except itself and white demon) adds -3 to Weakness buff";
            allBuiltMinions.add(warrior);
        }
    }

    private static void makeAllSpells() {
        {
            Spell spell = new Spell(11, "TotalDisarm", 0, 1000, false);

            spell.getTriggers().add(new Disarm(-1, Dispelablity.BAD));
            spell.getActions().put(new Applier(), new RectGetter(1, 1, false, true, false, true, false));

            spell.description.targetType = "one enemy";
            spell.description.descriptionOfCardSpecialAbility = "Disarm to the end of game";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(12, "AreaDispel", 2, 1500, false);

            spell.getActions().put(new Dispeller(), new RectGetter(2, 2, false, true, true, true, true));

            spell.description.targetType = "square 2*2";
            spell.description.descriptionOfCardSpecialAbility = "it delete enemy's positive buffs and our negative buffs";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(13, "Empower", 1, 250, false);

            spell.getEffects().add(new AP(-1, Dispelablity.GOOD, 2));
            spell.getActions().put(new Applier(), new RectGetter(1, 1, false, false, true, false, true));

            spell.description.targetType = "one friend";
            spell.description.descriptionOfCardSpecialAbility = "increase hit power of one person 2 units";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(14, "Fireball", 1, 400, false);

            spell.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -4));
            spell.getActions().put(new Applier(), new RectGetter(1, 1, false, true, false, true, false));

            spell.description.targetType = "one enemy";
            spell.description.descriptionOfCardSpecialAbility = "hit 4 unit to one enemy";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(15, "GodStrength", 2, 450, false);

            spell.getEffects().add(new AP(-1, Dispelablity.GOOD, 4));
            spell.getActions().put(new Applier(), new RectGetter(1, 1, false, false, false, false, true));

            spell.description.targetType = "hero friend";
            spell.description.descriptionOfCardSpecialAbility = "increase hit power of one hero 4 units";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(16, "HellFire", 3, 600, false);

            spell.getTriggers().add(new BurningCell(2,Dispelablity.BAD));
            spell.getActions().put(new Applier(), new RectGetter(2, 2, true, false, false, false, false));

            spell.description.targetType = "square 2*2";
            spell.description.descriptionOfCardSpecialAbility = "make fiery effect in 2 cells for 2 turns";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(17, "LightingBolt", 2, 1250, false);

            spell.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -8));
            spell.getActions().put(new Applier(), new RectGetter(1, 1, false, false, false, true, false));

            spell.description.targetType = "hero enemy";
            spell.description.descriptionOfCardSpecialAbility = "hit 8 units to the hero";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(18, "PoisonLake", 5, 900, false);

            Trigger poison = new Trigger(1, Dispelablity.BAD);
            poison.getTriggers().add(new Poisoned(3, Dispelablity.BAD));
            poison.getConditions().add(new HasWarriorOnIt());
            poison.getActions().put(new Applier(), new OnCellGetter());
            spell.getTriggers().add(poison);
            spell.getActions().put(new Applier(), new RectGetter(3, 3, true, false, false, false, false));

            spell.description.targetType = "square 3*3";
            spell.description.descriptionOfCardSpecialAbility = "make poisoned 8 cells for one turn";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(19, "Madness", 0, 650, false);

            spell.getActions().put(new Applier(), new RectGetter(1, 1, false, false, true, false, true));
            spell.getTriggers().add(new Disarm(3, Dispelablity.BAD));
            spell.getEffects().add(new AP(3, Dispelablity.GOOD, 4));

            spell.description.targetType = "one friend";
            spell.description.descriptionOfCardSpecialAbility = "increase hit power of one person 4 units for 3 turns but it disarm";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(110, "AllDisarm", 9, 2000, false);

            spell.getActions().put(new Applier(), new AllWarriorsGetter(false, true));
            spell.getTriggers().add(new Disarm(1, Dispelablity.BAD));

            spell.description.targetType = "all enemies";
            spell.description.descriptionOfCardSpecialAbility = "disarm for one turn";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(111, "AllPoison", 8, 1500, false);

            spell.getActions().put(new Applier(), new AllWarriorsGetter(false, true));
            spell.getTriggers().add(new Poisoned(4, Dispelablity.BAD));

            spell.description.targetType = "all enemies";
            spell.description.descriptionOfCardSpecialAbility = "all heroes poisoned for 4 turns";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(112, "Dispel", 0, 2100, false);

            spell.getActions().put(new Dispeller(), new RectGetter(1, 1, false, true, true, true, true));

            spell.description.targetType = "one friend or enemy";
            spell.description.descriptionOfCardSpecialAbility = "it delete enemy's positive buffs and our negative buffs";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(113, "HealthWithProfit", 0, 2250, false);

            spell.getActions().put(new Applier(), new RectGetter(1, 1, false, false, true, false, true));
            spell.getTriggers().add(new HolyBuff(3, Dispelablity.GOOD, 2));
            spell.getEffects().add(new HP(3, Dispelablity.BAD, -6));

            spell.description.targetType = "one friend";
            spell.description.descriptionOfCardSpecialAbility = "Gives a weakness buff -6 HP but also gives 2 holy buffs for 3 turns";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(114, "GhazaBokhorJoonBegiri", 2, 2500, false);

            spell.getActions().put(new Applier(), new RectGetter(1, 1, false, false, true, false, true));
            spell.getEffects().add(new AP(-1, Dispelablity.GOOD, 6));

            spell.description.targetType = "one friend";
            spell.description.descriptionOfCardSpecialAbility = "Gives power buff +6 AP";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(115, "AllPower", 4, 2000, false);

            spell.getActions().put(new Applier(), new AllWarriorsGetter(true, true));
            Aura aura = new Aura(-1, Dispelablity.GOOD, new TriggerOwnerGetter());
            aura.getEffects().add(new AP(1, Dispelablity.GOOD, 2));
            spell.getTriggers().add(aura);

            spell.description.targetType = "all friends";
            spell.description.descriptionOfCardSpecialAbility = "Gives power buff +6 AP";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(116, "AllAttack", 4, 1500, false);

            spell.getActions().put(new Applier(), new RectGetter(Constant.GameConstants.boardRow, 1, false, true, false, true, false));
            spell.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -6));

            spell.description.targetType = "all enemies in one column";
            spell.description.descriptionOfCardSpecialAbility = "hit all enemies 6 units";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(117, "Weakening", 1, 1000, false);

            spell.getActions().put(new Applier(), new RectGetter(1, 1, false, true, false, false, false));
            spell.getEffects().add(new AP(-1, Dispelablity.BAD, -4));

            spell.description.targetType = "one minion enemy";
            spell.description.descriptionOfCardSpecialAbility = "Gives weakness buff -4 AP";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(118, "Sacrifice", 3, 1600, false);

            spell.getActions().put(new Applier(), new RectGetter(1, 1, false, false, true, false, false));
            spell.getEffects().add(new AP(-1, Dispelablity.GOOD, 8));
            spell.getEffects().add(new HP(-1, Dispelablity.BAD, -6));

            spell.description.targetType = "one minion friend";
            spell.description.descriptionOfCardSpecialAbility = "Gives weakness buff -6 HP and power buff +8 AP";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(119, "KingsGaurd", 3, 1750, false);

            spell.getActions().put(new Killer(), new RandomGetter((SpellTarget) new AdjacentGetter(new RectGetter(1,1,false,false,false,false,true))));

            spell.description.targetType = "random enemy minion around hero";
            spell.description.descriptionOfCardSpecialAbility = "killes enemy";
            allBuiltSpells.add(spell);
        }
        {
            Spell spell = new Spell(120, "Shock", 1, 1200, false);

            spell.getActions().put(new Applier(), new RectGetter(1, 1, false, true, false, true, false));
            spell.getTriggers().add(new Stun(2, Dispelablity.BAD));

            spell.description.targetType = "one enemy";
            spell.description.descriptionOfCardSpecialAbility = "stun for 2 turns";
            allBuiltSpells.add(spell);
        }
    }

    private static void makeAllHeroes() {
        {
            Hero hero = new Hero(31, "Div_E_Sefid", 8000, 50, 4, -1);

            Aura aura = new Aura(-1, Dispelablity.UNDISPELLABLE, new TriggerOwnerGetter());
            aura.getEffects().add(new AP(1, Dispelablity.GOOD, 4));
            hero.getTriggers().add(aura);


            hero.description.descriptionOfCardSpecialAbility = "Apply power buff with 4 point additional attack damage on himself";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(32, "Simorgh", 9000, 50, 4, -1);

            HeroPower spell = new HeroPower(0, "", 5, 0, false,8);
            spell.getActions().put(new Applier(), new AllWarriorsGetter(false, true));
            spell.getTriggers().add(new Stun(1, Dispelablity.BAD));
            hero.power = spell;

            hero.description.descriptionOfCardSpecialAbility = "Make 8 cell around firable and apply holy buff on himself for 2 round";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(33, "Ezhdeha-E-Haftsar", 8000, 50, 4, -1);

            HeroPower spell = new HeroPower(0, "", 0, 0, false,1);
            spell.getActions().put(new Applier(), new RectGetter(1, 1, false, true, false, true, false));
            spell.getTriggers().add(new Disarm(1, Dispelablity.BAD));
            hero.power = spell;

            hero.description.descriptionOfCardSpecialAbility = "Disarm one person";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(34, "Rakhsh", 8000, 50, 4, -1);

            HeroPower spell = new HeroPower(0, "", 1, 0, false,2);
            spell.getActions().put(new Applier(), new RectGetter(1, 1, false, true, false, true, false));
            spell.getTriggers().add(new Stun(1, Dispelablity.BAD));
            hero.power = spell;

            hero.description.descriptionOfCardSpecialAbility = "Stun one enemy for 1 round";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(35, "Zahhak", 10000, 50, 2, -1);

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Applier(), new AttackedGetter());
            trigger.getTriggers().add(new Poisoned(3, Dispelablity.BAD));
            hero.getTriggers().add(trigger);

            hero.description.descriptionOfCardSpecialAbility = "Add 2 snakes with 1/1 randomly around himself";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(36, "Kaveh", 8000, 50, 4, -1);

            Trigger holyCell = new Trigger(3, Dispelablity.GOOD);
            holyCell.getConditions().add(new HasWarriorOnIt());
            holyCell.getActions().put(new Applier(), new OnCellGetter());
            holyCell.getTriggers().add(new HolyBuff(1, Dispelablity.GOOD, 1));
            HeroPower spell = new HeroPower(0, "", 1, 0, false,3);
            spell.getActions().put(new Applier(), new RectGetter(1, 1, true, false, false, false, false));
            spell.getTriggers().add(holyCell);
            hero.power = spell;

            hero.description.descriptionOfCardSpecialAbility = "Make one cell saint for 3 turns";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(37, "Arash", 10000, 30, 2, -1);

            HeroPower spell = new HeroPower(0, "", 2, 0, false,2);
            spell.getActions().put(new Applier(), new RectGetter(1, Constant.GameConstants.boardColumn, false, true, false, true, false));
            spell.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -4));
            hero.power = spell;

            hero.description.descriptionOfCardSpecialAbility = "Add 4 point to all minions in hero's row";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(38, "Afsane", 11000, 40, 3, -1);

            HeroPower spell = new HeroPower(0, "", 1, 0, false,2);
            spell.getActions().put(new Dispeller(), new RectGetter(1, 1, false, true, false, true, false));
            hero.power = spell;

            hero.description.descriptionOfCardSpecialAbility = "Dispel one enemy";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(39, "Esfandiyar", 12000, 35, 3, -1);

            Aura aura = new Aura(-1, Dispelablity.UNDISPELLABLE, new TriggerOwnerGetter());
            aura.getTriggers().add(new HolyBuff(1, Dispelablity.GOOD, 3));
            hero.getTriggers().add(aura);

            hero.description.descriptionOfCardSpecialAbility = "Have 3 passive holy buff";
            allBuiltHeroes.add(hero);
        }
        {
            Hero hero = new Hero(310, "Rostam", 8000, 55, 7, -1);

            hero.description.descriptionOfCardSpecialAbility = "None";
            allBuiltHeroes.add(hero);
        }
    }

    private static void makeAllItems() {
        {
            Spell item = new Spell(41, "Taj-E-Daanayi", 0, 300, true);

            Trigger manaRegen = new Trigger(6, Dispelablity.UNDISPELLABLE) {
                @Override
                protected void executeActions(GameState gameState, QualityHaver owner) {
                    assert owner instanceof Warrior;
                    Warrior warrior = (Warrior) owner;
                    warrior.getCell().getBoard().getGame().getWarriorsPlayer(warrior).mana += 1;
                }
            };
            manaRegen.getConditions().add(new HasTurnStarted());
            item.getTriggers().add(manaRegen);
            item.getActions().put(new Applier(), new RectGetter(1, 1, false, false, false, false, true));

            item.description.descriptionOfCardSpecialAbility = "increasing mana from roand 3 onwards";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(42, "Namoos-E-Separ", 0, 4000, true);

            item.getActions().put(new Applier(), new RectGetter(1, 1, false, false, false, false, true));
            item.getTriggers().add(new HolyBuff(-1, Dispelablity.UNDISPELLABLE, 12));

            item.description.descriptionOfCardSpecialAbility = "activate holy buff in our hero in passive kind";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(43, "Kaman-E-Daamol", 0, 30000, true);

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Applier(), new AttackedGetter());
            trigger.getTriggers().add(new Disarm(1, Dispelablity.BAD));
            item.getActions().put(new Applier(), new ByEffTriggGetter(Ranged.class, new RectGetter(1, 1, false, false, false, false, true)));
            item.getTriggers().add(trigger);

            item.description.descriptionOfCardSpecialAbility = "just for ranged and hybrid : increasing hero range 2 units";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(44, "NooshDaroo", 0, 0, true);

            item.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, 6));
            item.getActions().put(new Applier(), new RandomGetter((SpellTarget) new AllWarriorsGetter(true, true)));

            item.description.descriptionOfCardSpecialAbility = "increasing HP 6 units";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(45, "Tir-E-DoShakh", 0, 0, true);

            item.getEffects().add(new AP(-1, Dispelablity.UNDISPELLABLE, 2));
            item.getActions().put(new Applier(), new RandomGetter(new ByEffTriggGetter(Ranged.class, new AllWarriorsGetter(true, true))));

            item.description.descriptionOfCardSpecialAbility = "Hitting power of one ranged or increase hybrid 2 units";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(46, "Par-E-Simorgh", 0, 3500, true);

            item.getEffects().add(new AP(-1, Dispelablity.UNDISPELLABLE, -2));
            item.getActions().put(new Applier(), new RectGetter(1, 1, false, false, false, true, false));

            item.description.descriptionOfCardSpecialAbility = "When for the first time our heroes HP reach less than 15, make its HP double";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(47, "Eksir", 0, 0, true);

            item.getEffects().add(new AP(-1, Dispelablity.GOOD, 3));
            item.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, 3));
            item.getActions().put(new Applier(), new RandomGetter((SpellTarget) new AllWarriorsGetter(true, false)));

            item.description.descriptionOfCardSpecialAbility = "Increase each of health and hitting power 3 units";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(48, "Maajoon-E-Mana", 0, 0, true);

            Trigger manaPotion = new Trigger(3, Dispelablity.UNDISPELLABLE) {
                @Override
                protected void executeActions(GameState gameState, QualityHaver owner) {
                    assert owner instanceof Warrior;
                    Warrior warrior = (Warrior) owner;
                    ((Warrior) owner).getCell().getBoard().getGame().getWarriorsPlayer(warrior).mana += 3;
                }
            };
            manaPotion.getConditions().add(new HasTurnStarted());

            item.description.descriptionOfCardSpecialAbility = "Increase Mana 3 units";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(49, "Maajoon-E-RooyinTanoo", 0, 0, true);

            item.getTriggers().add(new HolyBuff(2, Dispelablity.GOOD, 10));
            item.getActions().put(new Applier(), new RandomGetter((SpellTarget) new AllWarriorsGetter(true, true)));


            item.description.descriptionOfCardSpecialAbility = "In two turn activate 10 holy buff inside ours";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(410, "Nefrin-E-Marg", 0, 0, true);

            Trigger curse = new Trigger(-1, Dispelablity.GOOD);
            curse.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -8));
            curse.getConditions().add(new HasDied());
            curse.getActions().put(new Applier(), new RandomGetter((TriggerTarget) (triggerOwner, gameState) -> {
                int distance = 0;
                ArrayList<? extends QualityHaver> targets = new ArrayList<>();
                while (targets.size() == 0 && distance <= Math.max(Constant.GameConstants.boardColumn, Constant.GameConstants.boardRow)) {
                    distance++;
                    targets = new WithinDistanceGetter(false, distance, true).getTarget(triggerOwner, gameState);
                }
                return targets;
            }));
            item.getActions().put(new Applier(), new RandomGetter((SpellTarget) new AllWarriorsGetter(true, false)));
            item.getTriggers().add(curse);


            item.description.descriptionOfCardSpecialAbility = "For one minion : at death time 8 hits to random nearest person";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(411, "RandomDamage", 0, 0, true);

            item.getEffects().add(new AP(-1, Dispelablity.UNDISPELLABLE, 2));
            item.getActions().put(new Applier(), new RandomGetter((SpellTarget) new AllWarriorsGetter(true, true)));

            item.description.descriptionOfCardSpecialAbility = "Two hits to three random persons";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(412, "TerrorHood", 0, 5000, true);

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Applier(), new RandomGetter((TriggerTarget) new AllWarriorsGetter(false, true)));
            trigger.getEffects().add(new AP(-1, Dispelablity.BAD, -2));
            item.getActions().put(new Applier(), new RectGetter(1, 1, false, false, false, false, true));
            item.getTriggers().add(trigger);

            item.description.descriptionOfCardSpecialAbility = "Apply a WeaknessBuff with two decrement in AP of a random enemy";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(413, "BladesOfAgility", 0, 0, true);

            item.getEffects().add(new AP(-1, Dispelablity.UNDISPELLABLE, 6));
            item.getActions().put(new Applier(), new RandomGetter((SpellTarget) new AllWarriorsGetter(true, true)));

            item.description.descriptionOfCardSpecialAbility = "Increase hit power six units";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(414, "KingWisdom", 0, 9000, true);

            Trigger manaRegen = new Trigger(-1, Dispelablity.UNDISPELLABLE) {
                @Override
                protected void executeActions(GameState gameState, QualityHaver owner) {
                    assert owner instanceof Warrior;
                    Warrior warrior = (Warrior) owner;

                    warrior.getCell().getBoard().getGame().getWarriorsPlayer(warrior).mana += 1;
                }
            };
            manaRegen.getConditions().add(new HasTurnStarted());

            item.description.descriptionOfCardSpecialAbility = "Take one mana in each turn and killed enemy hero after 15 turn";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(415, "AssassinationDagger", 0, 15000, true);

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add((gameState, trigger1, triggerOwner) -> {
                assert triggerOwner instanceof Warrior;
                Warrior warrior = (Warrior) triggerOwner;
                Game game = warrior.getCell().getBoard().getGame();

                return (gameState instanceof PutMinionState) && (game.getActivePlayer().equals(game.getWarriorsPlayer(warrior)));
            });
            trigger.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -1));
            trigger.getActions().put(new Applier(), new HeroGetter(false));
            item.getActions().put(new Applier(), new RectGetter(1, 1, false, false, false, true, false));
            item.getTriggers().add(trigger);

            item.description.descriptionOfCardSpecialAbility = "Just for ranged and hybrid: increase hit power when our hero hit enemy hero";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(416, "PoisonousDagger", 0, 7000, true);

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new Condition() {
                @Override
                public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
                    assert triggerOwner instanceof Warrior;
                    Warrior warrior = (Warrior) triggerOwner;
                    Game game = warrior.getCell().getBoard().getGame();

                    return (gameState instanceof AttackState) &&
                            game.getWarriorsPlayer(((AttackState) gameState).getAttacker()).equals(game.getWarriorsPlayer(warrior));
                }
            });
            trigger.getActions().put(new Applier(), new AttackedGetter());
            trigger.getTriggers().add(new Poisoned(1, Dispelablity.BAD));
            item.getTriggers().add(trigger);
            item.getActions().put(new Applier(), new RectGetter(1, 1, false, false, false, true, false));

            item.description.descriptionOfCardSpecialAbility = "Each of ours poison one enemy when hit";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(417, "ShockHammer", 0, 15000, true);

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(new HasAttacked());
            trigger.getActions().put(new Applier(), new AttackedGetter());
            trigger.getTriggers().add(new Disarm(1, Dispelablity.BAD));

            item.description.descriptionOfCardSpecialAbility = "Just in one turn stun enemy when hit";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(418, "SoulEater", 0, 25000, true);

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getActions().put(new Applier(), new RandomGetter((TriggerTarget) new AllWarriorsGetter(true, true)));
            trigger.getEffects().add(new AP(-1, Dispelablity.GOOD, 1));
            trigger.getConditions().add((gameState, trigger12, triggerOwner) -> {
                assert triggerOwner instanceof Warrior;
                Warrior warrior = (Warrior) triggerOwner;
                Game game = warrior.getCell().getBoard().getGame();

                return (gameState instanceof DeathState) && (game.getWarriorsPlayer(((DeathState) gameState).getWarrior()).equals(game.getWarriorsPlayer(warrior)));
            });
            item.getActions().put(new Applier(), new RectGetter(1, 1, false, false, false, false, true));
            item.getTriggers().add(trigger);

            item.description.descriptionOfCardSpecialAbility = "Just for melee: increase its HP 2 units when hit";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(419, "Ghosl-E-Taamid", 0, 20000, true);

            Trigger trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getActions().put(new Applier(), (triggerOwner, gameState) -> {
                assert gameState instanceof PutMinionState;
                PutMinionState state = (PutMinionState) gameState;
                ArrayList<QualityHaver> arrayList = new ArrayList<>();
                arrayList.add(state.getWarrior());

                return arrayList;
            });
            trigger.getConditions().add((gameState, trigger1, triggerOwner) -> {
                assert triggerOwner instanceof Warrior;
                Warrior warrior = (Warrior) triggerOwner;
                Game game = warrior.getCell().getBoard().getGame();

                return (gameState instanceof PutMinionState) && (game.getActivePlayer().equals(game.getWarriorsPlayer(warrior)));
            });
            trigger.getTriggers().add(new HolyBuff(2, Dispelablity.GOOD, 1));
            item.getActions().put(new Applier(), new RectGetter(1, 1, false, false, false, false, true));
            item.getTriggers().add(trigger);

            item.description.descriptionOfCardSpecialAbility = "When put each minion has holy buff until toe turn";
            allBuiltItems.add(item);
        }
        {
            Spell item = new Spell(420, "Shamshir-E-Chini", 0, 0, true);

            item.getActions().put(new Applier(), new ByEffTriggGetter(Melee.class, new RectGetter(1, 1, false, false, true, false, true)));
            item.getEffects().add(new AP(-1, Dispelablity.UNDISPELLABLE, 5));

            item.description.descriptionOfCardSpecialAbility = "Until warrior doesn't hit, for 5 times it hits 5 times more";
            allBuiltItems.add(item);
        }
    }

    public static void main() {
        makeAllMinions();
        cardAdder(allBuiltMinions);
        makeAllSpells();
        cardAdder(allBuiltSpells);
        makeAllHeroes();
        cardAdder(allBuiltHeroes);
        makeAllItems();
        cardAdder(allBuiltItems);
    }

    private static void cardAdder(ArrayList<Card> allBuiltCards) {
        for (Card card : allBuiltCards) {
            Card.getAllCards().put(card.getID(), card);
        }
        for (Card card : allBuiltCards) {
            Shop.getShop().getCardIDs().add(card.getID());
        }
    }

    //***


    public static ArrayList<Card> getAllBuiltMinions() {
        return allBuiltMinions;
    }

    public static ArrayList<Card> getAllBuiltSpells() {
        return allBuiltSpells;
    }

    public static ArrayList<Card> getAllBuiltHeroes() {
        return allBuiltHeroes;
    }

    public static ArrayList<Card> getAllBuiltItems() {
        return allBuiltItems;
    }
}