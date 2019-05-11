package model;

import model.effects.Effect;
import model.gamestate.GameState;
import model.triggers.Trigger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;


public class Board implements Serializable {
    private ArrayList<ArrayList<Cell>> table = new ArrayList<>();
    private Game game;

    public Board(Game game) {
        this.game = game;
        for (int i = 0; i < Constant.GameConstants.boardRow; i++) {
            table.add(new ArrayList<>());
            for (int j = 0; j < Constant.GameConstants.boardColumn; j++) {
                table.get(i).add(new Cell(this, i, j));
            }
        }
    }

    public Cell getCell(int row, int column) {
        return table.get(row).get(column);
    }

    public Game getGame() {
        return game;
    }

    public void iterateBoardTriggers(GameState gameState) {
        for (ArrayList<Cell> row : table) {
            for (Cell cell : row) {
                UUID id = UUID.randomUUID();
                cell.setLock(id+"bti",true);
                for (Trigger trigger : cell.getTriggers()) {
                    trigger.check(gameState,cell);
                }
                cell.setLock(id+"bti",false);
            }
        }
    }

    public void iterateAndExpireBoardTriggers() {
        for (ArrayList<Cell> row : table) {
            for (Cell cell : row) {
                UUID id = UUID.randomUUID();
                cell.setLock(id+"bte",true);
                for (Trigger trigger : cell.getTriggers()) {
                    if(trigger.duration>0){
                        trigger.duration--;
                        if(trigger.duration == 0){
                            cell.removeTrigger(trigger);
                        }
                    }
                }
                cell.setLock(id+"bte",false);
            }
        }
    }

    public  void iterateAndExpireBoardEffects() {
        for (ArrayList<Cell> row : table) {
            for (Cell cell : row) {
                UUID id = UUID.randomUUID();
                cell.setLock(id+"bee",true);
                for (Effect effect : cell.getEffects()) {
                    if(effect.duration>0){
                        effect.duration--;
                        if(effect.duration == 0){
                            cell.removeEffect(effect);
                        }
                    }
                }
                cell.setLock(id+"bee",false);
            }
        }
    }

    public ArrayList<Cell> getEightAdjacent(Cell cell){
        ArrayList<Cell> eightAdjacentPartOne = getNextCells(cell);
        ArrayList<Cell> eightAdjacentPartTwo = new ArrayList<>();
        eightAdjacentPartOne.forEach
                (theCell -> updateListOneByListTwo(eightAdjacentPartTwo, getNextCells(theCell)));
        updateListOneByListTwo(eightAdjacentPartOne, eightAdjacentPartTwo);
        return eightAdjacentPartOne.stream().distinct().filter(theCell -> theCell!=cell).
                filter(theCell -> 1 >= Math.abs(theCell.getRow() - cell.getRow())).
                filter(theCell -> 1 >= Math.abs(theCell.getColumn() - cell.getColumn())).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Cell> getCellWithinDistance(Cell cell,int maxDistance){
        /*this function doesn't returns the origin cell.*/
        ArrayList<Cell> cells = new ArrayList<>();
        ArrayList<Cell> layerCells = new ArrayList<>();
        layerCells.add(cell);
        updateListOneByListTwo(cells, layerCells);
        for (int i = 0; i < maxDistance; i++) {
            ArrayList<Cell> newLayerCell = new ArrayList<>();
            layerCells.forEach(theCell -> updateListOneByListTwo(newLayerCell, getNextCells(theCell)));
            newLayerCell.stream().distinct().filter(theCell -> !cells.contains(cell));
            layerCells = newLayerCell;
            updateListOneByListTwo(cells, layerCells);
        }
        cells.remove(cell);
        return cells;
    }

    public int getJumperManhattanDistance(Cell originCell, Cell targetCell) {
        return Math.abs(originCell.getRow() - targetCell.getRow()) +
                Math.abs(originCell.getColumn() - targetCell.getColumn());
    }

    public int getManhattanDistance(Cell originCell, Cell targetCell) {
        ArrayList<Cell> checkedCells = new ArrayList<>();
        ArrayList<Cell> layerCells = new ArrayList<>();
        layerCells.add(originCell);
        for (int i = 0; true; i++) {
            if (layerCells.contains(targetCell)) {
                return i;
            }
            else if (layerCells.size() == 0) {
                return -1;
            }
            ArrayList nextLayerCells = new ArrayList();
            for (Cell cell : layerCells) {
                checkedCells.add(cell);
                updateListOneByListTwo(nextLayerCells, getAvailableNextLayerCellsFromCell(cell, checkedCells));
            }
            layerCells = nextLayerCells;
        }
    }

    private ArrayList<Cell> getAvailableNextLayerCellsFromCell(Cell cell, ArrayList<Cell> checkedCells) {
        ArrayList<Cell> availableNexLayerCellsFromCell = new ArrayList<>();
        getNextCells(cell).stream().filter(this::availableCell).filter(theCell -> !checkedCells.
                contains(theCell)).forEach(availableNexLayerCellsFromCell::add);
        return availableNexLayerCellsFromCell;
    }

    private void updateListOneByListTwo (ArrayList<Cell> listOne, ArrayList<Cell> listTwo) {
        listOne.addAll(listTwo);
    }

    private ArrayList<Cell> getNextCells (Cell cell) {
        ArrayList<Cell> nextCells = new ArrayList<>();
        if (cell.getRow() + 1 < Constant.GameConstants.boardRow) {
            nextCells.add(this.getCell(cell.getRow() + 1, cell.getColumn()));
        }
        if (cell.getColumn() + 1 < Constant.GameConstants.boardColumn) {
            nextCells.add(this.getCell(cell.getRow(), cell.getColumn() + 1));
        }
        if (cell.getRow() - 1 >= 0) {
            nextCells.add(this.getCell(cell.getRow() - 1, cell.getColumn()));
        }
        if (cell.getColumn() - 1 >= 0) {
            nextCells.add(this.getCell(cell.getRow(), cell.getColumn() - 1));
        }
        return nextCells;
    }

    private boolean availableCell (Cell cell) {
        if (cell.getWarrior() == null ||
                game.getActivePlayer().getWarriors().contains(cell.getWarrior())) {
            return true;
        }
        return false;
    }
}
