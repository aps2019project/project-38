package model.actions;

import model.Cell;
import model.Game;
import model.cards.Warrior;
import model.effects.*;
import model.exceptions.NotEnoughConditions;
import model.gamestate.AttackState;

import java.util.stream.Stream;

public class Attack {
    public static void doIt(Cell attackerCell, Cell defenderCell, boolean counterAttack) throws NotEnoughConditions {
        Game game = attackerCell.getBoard().getGame();
        Warrior attacker = attackerCell.getWarrior();
        Warrior defender = defenderCell.getWarrior();
        int jumperManhattanDistance = game.getBoard().getJumperManhattanDistance(attackerCell, defenderCell);
        checkWarriorsEffectsForAttack(game, attackerCell, defenderCell, jumperManhattanDistance);

        AttackState attackState = new AttackState(attacker, defender, attacker.getAp());
        game.iterateAllTriggersCheck(attackState);
        if (!attackState.canceled) {
            System.err.println("Attacked: (" + attackerCell.getRow() + "," + attackerCell.getColumn() + ") (" + defenderCell.getRow() + "," + defenderCell.getColumn() + ")");
            if (!counterAttack) {
                attacker.addEffect(new Attacked());
            }
            defender.addEffect(new HP(-1, Dispelablity.UNDISPELLABLE, -1 * attackState.ap));
            attackState.pending = false;
            game.iterateAllTriggersCheck(attackState);
        }else {
            throw new NotEnoughConditions("Something prevented you from attacking!");
        }
    }

    static void checkWarriorsEffectsForAttack
            (Game game, Cell attackerCell, Cell defenderCell, int JumperManhattanDistance) throws NotEnoughConditions {
        Warrior attacker = attackerCell.getWarrior();
        if (attacker.getEffects().stream().anyMatch(effect -> effect instanceof Attacked)) {
            throw new NotEnoughConditions("This unit has already attacked");
        }
        if (attacker.getEffectsBuffer().stream().anyMatch(effect -> effect instanceof Attacked)) {
            throw new NotEnoughConditions("This unit has already attacked");
        }

        if (game.getBoard().getEightAdjacent(attackerCell).contains(defenderCell)) {
            if (attacker.getEffects().stream().anyMatch(effect -> effect instanceof Melee)) {
            } else {
                throw new NotEnoughConditions("That unit is out of reach!");
            }
        } else if (attacker.getEffects().stream().anyMatch(effect -> effect instanceof Ranged)) {
            if (attacker.getEffects().stream().filter(effect -> effect instanceof Ranged)
                    .anyMatch(effect -> ((Ranged) effect).getRange() >= JumperManhattanDistance)) {
            } else {
                throw new NotEnoughConditions("That unit is out of reach!");
            }
        }else {
            throw new NotEnoughConditions("That unit is out of reach!");
        }
    }
}