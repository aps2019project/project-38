package model.targets.spelltargets;

import model.Cell;
import model.Constant;
import model.Game;
import model.QualityHaver;
import model.cards.Hero;
import model.player.Player;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class SqrFromTL implements SpellTarget {
    private int len;
    private boolean cellMod;
    private boolean enemies;
    private boolean friends;
    private boolean enemyHero;
    private boolean friendHero;

    public SqrFromTL(int len, boolean cellMod, boolean enemies, boolean friends, boolean enemyHero, boolean friendHero) {
        this.len = len;
        this.cellMod = cellMod;
        this.enemies = enemies;
        this.friends = friends;
        this.enemyHero = enemyHero;
        this.friendHero = friendHero;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(Player spellOwner, Cell cell) {
        Game game = cell.getBoard().getGame();
        ArrayList<Cell> cells = new ArrayList<>();
        addCells(cell, cells);
        if (cellMod) {
            return cells;
        }
        return cells.stream().map(Cell::getWarrior).filter(Objects::nonNull)
                .filter(warrior -> (game.getWarriorsEnemyPlayer(warrior).equals(spellOwner) && !(warrior instanceof Hero)) == enemies ||
                        (game.getWarriorsPlayer(warrior).equals(spellOwner) && !(warrior instanceof Hero)) == friends ||
                        (game.getWarriorsEnemyPlayer(warrior).equals(spellOwner) && warrior instanceof Hero) == enemyHero ||
                        (game.getWarriorsPlayer(warrior).equals(spellOwner) && warrior instanceof Hero) == friendHero)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void addCells(Cell cell, ArrayList<Cell> cells) {
        for (int i = cell.getColumn(); i < Math.min(cell.getColumn() + len, Constant.GameConstants.boardColumn); i++) {
            for (int j = cell.getRow(); j < Math.min(cell.getRow() + len, Constant.GameConstants.boardRow); j++) {
                cells.add(cell.getBoard().getCell(j, i));
            }
        }
    }
}
