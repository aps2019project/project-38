package model.actions.gameactions;

import model.Cell;
import model.Game;
import model.cards.warriors.Warrior;
import model.effects.Attacked;
import model.effects.Combo;
import model.effects.Dispelablity;
import model.effects.HP;
import model.gamestate.AttackState;

import java.util.Arrays;

import static model.actions.gameactions.Attack.checkWarriorsEffectsForAttack;

public abstract class ComboAttack {
    public static void doIt(Cell[] attackersCell, Cell defenderCell) {
        Game game = attackersCell[0].getBoard().getGame();
        Warrior defender = defenderCell.getWarrior();
        AttackState attackState = new AttackState(attackersCell[0].getWarrior(), defender, 0);
        for (Cell attackerCell : attackersCell) {
            Warrior attacker = attackerCell.getWarrior();
            int jumperManhattanDistance = game.getBoard().getJumperManhattanDistance(attackerCell, defenderCell);
            if (checkWarriorsEffectsForAttack(game, attackerCell, defenderCell, jumperManhattanDistance) &&
                    checkWarriorHasComboEffect(attacker) && !attackState.canceled) {
                attackState.ap += attacker.getAp();
                attackState.setAttacker(attacker);
                game.iterateAllTriggers(attackState);
            }
        }
        if (!attackState.canceled) {
            Arrays.stream(attackersCell).forEach(cell -> cell.getWarrior().getEffects().add(new Attacked()));
            defender.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -1 * attackState.ap));
            attackState.pending = false;
            game.iterateAllTriggers(attackState);
        }
    }

    private static boolean checkWarriorHasComboEffect(Warrior warrior) {
        return warrior.getEffects().stream().anyMatch(effect -> effect instanceof Combo);
    }
}
