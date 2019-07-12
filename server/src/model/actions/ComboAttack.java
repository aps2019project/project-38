package model.actions;

import model.Cell;
import model.Game;
import model.cards.Warrior;
import model.effects.*;
import model.exceptions.NotEnoughConditions;
import model.gamestate.AttackState;
import model.triggers.HolyBuff;

import java.util.ArrayList;
import java.util.Arrays;

import static model.actions.Attack.checkWarriorsEffectsForAttack;

public class ComboAttack {
    public static void doIt(ArrayList<Cell> attackersCells, Cell defenderCell) throws NotEnoughConditions {
        Game game = attackersCells.get(0).getBoard().getGame();
        Warrior defender = defenderCell.getWarrior();
        AttackState attackState = new AttackState(null, defender, 0);
        for (Cell attackerCell : attackersCells) {
            Warrior attacker = attackerCell.getWarrior();
            int jumperManhattanDistance = game.getBoard().getJumperManhattanDistance(attackerCell, defenderCell);

            checkWarriorsEffectsForAttack(game, attackerCell, defenderCell, jumperManhattanDistance);
            checkWarriorHasComboEffect(attacker);

            attackState.ap += attacker.getAp();
            attackState.setAttacker(attacker);
//            AntiHolyBuff antiHolyBuff = new AntiHolyBuff(-1, Dispelablity.UNDISPELLABLE); //todo what is this?
//            if (attacker != attackersCells.get(0).getWarrior()) {
//                attacker.addEffect(antiHolyBuff);
//            }
            game.iterateAllTriggersCheck(attackState);
//            if (attacker != attackersCells.get(0).getWarrior()) {
//                attacker.removeEffect(antiHolyBuff);
//            }
        }
        if (!attackState.canceled) {
            attackersCells.forEach(cell -> cell.getWarrior().getEffects().add(new Attacked()));
            defender.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -1 * attackState.ap));
            attackState.pending = false;
            attackState.setAttacker(attackersCells.get(0).getWarrior());
            game.iterateAllTriggersCheck(attackState);
        }else {
            throw new NotEnoughConditions("Something prevented you from combo attacking!");
        }
    }

    private static void checkWarriorHasComboEffect(Warrior warrior) throws NotEnoughConditions {
        if (!warrior.getEffects().stream().anyMatch(effect -> effect instanceof Combo)) {
            throw new NotEnoughConditions("All unit are not do not have combo ability");
        }
    }
}