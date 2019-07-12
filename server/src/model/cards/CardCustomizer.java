package model.cards;

import model.Shop;
import model.actions.Applier;
import model.conditions.*;
import model.effects.*;
import model.exceptions.NotEnoughConditions;
import model.targets.*;
import model.triggers.*;
import view.fxmlControllers.MoreCustomCardController;

import java.util.ArrayList;

public class CardCustomizer {
    static ArrayList<Card> allCustomCards = new ArrayList<>();

    MoreCustomCardController mccc;

    public CardCustomizer(MoreCustomCardController mccc) {
        this.mccc = mccc;
    }

    public void build() throws NotEnoughConditions {
        if (mccc.tp.getTabs().get(0).isSelected()) {
            buildSpell();
        } else if (mccc.tp.getTabs().get(1).isSelected()) {
            buildMinion();
        } else {
            buildHero();
        }
    }

    void buildHero() throws NotEnoughConditions {
        Hero hero = new Hero(Integer.valueOf("5"+allCustomCards.size()), mccc.hName.getText(), Integer.valueOf(mccc.hPrice.getText()), Integer.valueOf(mccc.hHP.getText()), Integer.valueOf(mccc.hAP.getText()), 0);
        if (mccc.hMelee.isSelected()) {
            hero.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
        }
        if (mccc.hRanged.isSelected()) {
            hero.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, (int) mccc.hRange.getValue()));
        }

        HeroPower power = new HeroPower(5, mccc.hSPName.getText(), (int) mccc.hMana.getValue(), 0, false, (int) mccc.hCool.getValue());
        power.description.descriptionOfCardSpecialAbility = mccc.hDes.getText();
        power.description.targetType = (String) mccc.hTar.getValue();
        hero.power = power;
        hero.getTriggers().add(new CounterAttack(-1, Dispelablity.UNDISPELLABLE));

        power.getActions().put(new Applier(), getSpellTarget((String) mccc.hTar.getValue()));
        if (mccc.hHB.isSelected()) {
            power.getTriggers().add(new HolyBuff(Integer.valueOf(mccc.hHBD.getText()), Dispelablity.GOOD, Integer.valueOf(mccc.hHBA.getText())));
        }
        if (mccc.hP.isSelected()) {
            power.getTriggers().add(new Poisoned(Integer.valueOf(mccc.hPD.getText()), Dispelablity.BAD));
        }
        if (mccc.hPWB.isSelected()) {
            power.getEffects().add(new AP(Integer.valueOf(mccc.hPWBD.getText()), Dispelablity.UNDISPELLABLE, Integer.valueOf(mccc.hPWBAP.getText())));
            power.getEffects().add(new HP(Integer.valueOf(mccc.hPWBD.getText()), Dispelablity.UNDISPELLABLE, Integer.valueOf(mccc.hPWBHP.getText())));
        }
        if (mccc.hD.isSelected()) {
            power.getTriggers().add(new Disarm(Integer.valueOf(mccc.hDD.getText()), Dispelablity.BAD));
        }
        if (mccc.hS.isSelected()) {
            power.getTriggers().add(new Stun(Integer.valueOf(mccc.hSD.getText()), Dispelablity.BAD));
        }

        CardFactory.getAllBuiltHeroes().add(hero);
        Card.getAllCards().put(hero.getID(), hero);
        Shop.getShop().addNewCardToShop(hero);
    }

    void buildSpell() throws NotEnoughConditions {
        Spell spell = new Spell(Integer.valueOf("5"+allCustomCards.size()), mccc.sName.getText(), (int) mccc.sMana.getValue(), Integer.valueOf(mccc.sPrice.getText()), false);
        spell.description.descriptionOfCardSpecialAbility = mccc.sDes.getText();
        spell.description.targetType = (String) mccc.sTarget.getValue();

        spell.getActions().put(new Applier(), getSpellTarget((String) mccc.sTarget.getValue()));
        if (mccc.sHB.isSelected()) {
            spell.getTriggers().add(new HolyBuff(Integer.valueOf(mccc.sHBD.getText()), Dispelablity.GOOD, Integer.valueOf(mccc.sHBA.getText())));
        }
        if (mccc.sP.isSelected()) {
            spell.getTriggers().add(new Poisoned(Integer.valueOf(mccc.sPD.getText()), Dispelablity.BAD));
        }
        if (mccc.sPWB.isSelected()) {
            spell.getEffects().add(new AP(Integer.valueOf(mccc.sPWBD.getText()), Dispelablity.UNDISPELLABLE, Integer.valueOf(mccc.sPWBAP.getText())));
            spell.getEffects().add(new HP(Integer.valueOf(mccc.sPWBD.getText()), Dispelablity.UNDISPELLABLE, Integer.valueOf(mccc.sPWBHP.getText())));
        }
        if (mccc.sD.isSelected()) {
            spell.getTriggers().add(new Disarm(Integer.valueOf(mccc.sDD.getText()), Dispelablity.BAD));
        }
        if (mccc.sS.isSelected()) {
            spell.getTriggers().add(new Stun(Integer.valueOf(mccc.sSD.getText()), Dispelablity.BAD));
        }

        CardFactory.getAllBuiltSpells().add(spell);
        Card.getAllCards().put(spell.getID(), spell);
        Shop.getShop().addNewCardToShop(spell);
    }

    void buildMinion() throws NotEnoughConditions {
        Warrior warrior = new Warrior(Integer.valueOf("5"+allCustomCards.size()), mccc.mName.getText(), Integer.valueOf(mccc.mPrice.getText()), (int) mccc.mMana.getValue(), Integer.valueOf(mccc.mHP.getText()), Integer.valueOf(mccc.mAP.getText()));
        if (mccc.mMeele.isSelected()) {
            warrior.getEffects().add(new Melee(-1, Dispelablity.UNDISPELLABLE));
        }
        if (mccc.mRanged.isSelected()) {
            warrior.getEffects().add(new Ranged(-1, Dispelablity.UNDISPELLABLE, (int) mccc.mRange.getValue()));
        }

        warrior.description.descriptionOfCardSpecialAbility = mccc.mDes.getText();

        Trigger trigger;
        if(getTriggerCondition((String)mccc.mO.getValue())!=null){
            trigger = new Trigger(-1, Dispelablity.UNDISPELLABLE);
            trigger.getConditions().add(getTriggerCondition((String)mccc.mO.getValue()));
            trigger.getActions().put(new Applier(),getTriggerTarget((String)mccc.mT.getValue()));
        }else {
            trigger = new Aura(-1,Dispelablity.UNDISPELLABLE,getTriggerTarget((String)mccc.mT.getValue()));
        }

        if (mccc.mHB.isSelected()) {
            trigger.getTriggers().add(new HolyBuff(Integer.valueOf(mccc.mHBD.getText()), Dispelablity.GOOD, Integer.valueOf(mccc.mHBA.getText())));
        }
        if (mccc.mP.isSelected()) {
            trigger.getTriggers().add(new Poisoned(Integer.valueOf(mccc.mPD.getText()), Dispelablity.BAD));
        }
        if (mccc.mPWB.isSelected()) {
            trigger.getEffects().add(new AP(Integer.valueOf(mccc.mPWBD.getText()), Dispelablity.UNDISPELLABLE, Integer.valueOf(mccc.mPWBAP.getText())));
            trigger.getEffects().add(new HP(Integer.valueOf(mccc.mPWBD.getText()), Dispelablity.UNDISPELLABLE, Integer.valueOf(mccc.mPWBHP.getText())));
        }
        if (mccc.mD.isSelected()) {
            trigger.getTriggers().add(new Disarm(Integer.valueOf(mccc.mDD.getText()), Dispelablity.BAD));
        }
        if (mccc.mS.isSelected()) {
            trigger.getTriggers().add(new Stun(Integer.valueOf(mccc.mSD.getText()), Dispelablity.BAD));
        }
        warrior.getTriggers().add(trigger);

        CardFactory.getAllBuiltMinions().add(warrior);
        Card.getAllCards().put(warrior.getID(),warrior);
        Shop.getShop().addNewCardToShop(warrior);
    }

    private SpellTarget getSpellTarget(String input) throws NotEnoughConditions {
        switch (input) {
            case "A 2*2 Square(Friends)":
                return new RectGetter(2, 2, false, false, true, false, true);
            case "A 2*2 Square(Enemies)":
                return new RectGetter(2, 2, false, true, false, true, false);
            case "A Friend Warrior":
                return new RectGetter(1, 1, false, false, true, false, true);
            case "An Opponent Warrior":
                return new RectGetter(1, 1, false, true, false, true, false);
            case "All Opponent Warriors":
                return new AllWarriorsGetter(false, true);
            case "All Friend Warriors":
                return new AllWarriorsGetter(true, true);
            case "Random Enemy":
                return new RandomGetter((SpellTarget) new AllWarriorsGetter(false, true));
            case "Random Friend":
                return new RandomGetter((SpellTarget) new AllWarriorsGetter(true, true));
            default:
                throw new NotEnoughConditions("Spell target is not selected");
        }
    }

    private TriggerTarget getTriggerTarget(String input) throws NotEnoughConditions {
        switch (input) {
            case "Adjacent cells(Friends)":
                return new AdjacentGetter(true,true);
            case "Adjacent cells(Enemies)":
                return new AdjacentGetter(false,true);
            case "All Friend Warriors":
                return new AllWarriorsGetter(true,true);
            case "All Enemy Warriors":
                return new AllWarriorsGetter(false,true);
            case "Enemy Hero":
                return new HeroGetter(false);
            case "Friend Hero":
                return new HeroGetter(true);
            case "Random Enemy":
                return new RandomGetter((TriggerTarget)new AllWarriorsGetter(false,true));
            case "Random Friend":
                return new RandomGetter((TriggerTarget)new AllWarriorsGetter(true,true));
            case "Self":
                return new TriggerOwnerGetter();
            case "Attacker(for on attack)":
                return new AttackerGetter();
            default:
                throw new NotEnoughConditions("Target is not selected");
        }
    }

    private Condition getTriggerCondition(String input) throws NotEnoughConditions {
        switch (input) {
            case "Attack":
                return new HasAttacked();
            case "Spawn":
                return new HasSpawned();
            case "Death":
                return new HasDied();
            case "Turn End":
                return new HasTurnEnded();
            case "Defend":
                return new HasBeenAttacked();
            case "Passive":
                return null;
            default:
                throw new NotEnoughConditions("\"On\" is not selected");
        }
    }
}