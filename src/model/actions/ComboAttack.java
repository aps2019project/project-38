package model.actions;

import model.Cell;
import model.Game;
import model.cards.Warrior;
import model.effects.Attacked;
import model.effects.Combo;
import model.effects.Dispelablity;
import model.effects.HP;
import model.gamestate.AttackState;

import java.util.ArrayList;
import java.util.Arrays;

import static model.actions.Attack.checkWarriorsEffectsForAttack;

public class ComboAttack {
    public static boolean doIt(ArrayList<Cell> attackersCell, Cell defenderCell) {
        Game game = attackersCell.get(0).getBoard().getGame();
        Warrior defender = defenderCell.getWarrior();
        AttackState attackState = new AttackState(attackersCell.get(0).getWarrior(), defender, 0);
        for (Cell attackerCell : attackersCell) {
            Warrior attacker = attackerCell.getWarrior();
            int jumperManhattanDistance = game.getBoard().getJumperManhattanDistance(attackerCell, defenderCell);
            if (checkWarriorsEffectsForAttack(game, attackerCell, defenderCell, jumperManhattanDistance) &&
                    checkWarriorHasComboEffect(attacker) && !attackState.canceled) {
                attackState.ap += attacker.getAp();
                attackState.setAttacker(attacker);
                game.iterateAllTriggersCheck(attackState);
            }
        }
        if (!attackState.canceled) {
            attackersCell.forEach(cell -> cell.getWarrior().getEffects().add(new Attacked()));
            defender.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -1 * attackState.ap));
            attackState.pending = false;
            game.iterateAllTriggersCheck(attackState);
        }
        return !attackState.canceled;
    }

    private static boolean checkWarriorHasComboEffect(Warrior warrior) {
        return warrior.getEffects().stream().anyMatch(effect -> effect instanceof Combo);
    }
}
