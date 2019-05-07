package model.targets;

import model.Cell;
import model.Constant;
import model.Game;
import model.QualityHaver;
import model.cards.Hero;
import model.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
//in getTarget method i mix cells and warrior it may cause trouble. change cellsToo to cellMod to solve the issue.
public class RectGetter implements SpellTarget {
    private int rowLen;
    private int colLen;
    private boolean cellsToo;
    private boolean enemies;
    private boolean friends;
    private boolean enemyHero;
    private boolean friendHero;

    public RectGetter(int rowLen, int colLen, boolean cellsToo, boolean enemies, boolean friends, boolean enemyHero, boolean friendHero) {
        this.rowLen=rowLen;
        this.colLen=colLen;
        this.cellsToo = cellsToo;
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

        ArrayList<QualityHaver> targets = new ArrayList<>();
        if (cellsToo) {
            targets.addAll(cells);
        }
        targets.addAll( cells.stream().map(Cell::getWarrior).filter(Objects::nonNull)
                .filter(warrior -> (((game.getWarriorsEnemyPlayer(warrior).equals(spellOwner) && !(warrior instanceof Hero)) == enemies)&&enemies) ||
                        (((game.getWarriorsPlayer(warrior).equals(spellOwner) && !(warrior instanceof Hero)) == friends)&&friends) ||
                        (((game.getWarriorsEnemyPlayer(warrior).equals(spellOwner) && (warrior instanceof Hero)) == enemyHero)&&enemyHero) ||
                        (((game.getWarriorsPlayer(warrior).equals(spellOwner) && (warrior instanceof Hero)) == friendHero)&&friendHero))
                .collect(Collectors.toCollection(ArrayList::new)));

        return targets;
    }

    private void addCells(Cell cell, ArrayList<Cell> cells) {
        for (int i = cell.getColumn(); i < Math.min(cell.getColumn() + colLen, Constant.GameConstants.boardColumn); i++) {
            for (int j = cell.getRow(); j < Math.min(cell.getRow() + rowLen, Constant.GameConstants.boardRow); j++) {
                cells.add(cell.getBoard().getCell(j, i));
            }
        }
    }
}
