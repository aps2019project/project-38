package model;

import model.gamestate.GameState;
import model.triggers.Trigger;

import java.util.ArrayList;


public class Board {
    private ArrayList<ArrayList<Cell>> board;
    private Game game;

    public Board(Game game) {
        this.game = game;
        for (int i = 0; i < Constant.GameConstants.boardRow; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < Constant.GameConstants.boardColumn; j++) {
                board.get(i).add(new Cell(i, j));
            }
        }
    }

    public Cell getCell(int row, int column) {
        return board.get(row).get(column);
    }

    public void iterateBoardTriggers(GameState gameState) {
        for (ArrayList<Cell> row : board) {
            for (Cell cell : row) {
                for (Trigger trigger : cell.getTriggers()) {
                    trigger.check(gameState);
                }
            }
        }
    }

    public ArrayList<Cell> getEightAdjacent(Cell cell){
        ArrayList<Cell> eightAdjacentPartOne = getNextCells(cell);
        ArrayList<Cell> eightAdjacentPartTwo = new ArrayList<>();
        eightAdjacentPartOne.forEach
                (theCell -> updateListOneByListTwo(eightAdjacentPartTwo, getNextCells(theCell)));
        updateListOneByListTwo(eightAdjacentPartOne, eightAdjacentPartTwo);
        eightAdjacentPartOne.stream().distinct().filter(theCell -> theCell!=cell).
                filter(theCell -> 1 == Math.abs(theCell.getRow() - cell.getRow())).
                filter(theCell -> 1 == Math.abs(theCell.getColumn() - cell.getColumn()));
        return eightAdjacentPartOne;
    }

    public ArrayList<Cell> getCellWithinDistance(Cell cell,int maxDistance){
        //this function doesn't returns the origin cell.
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

    public int getManhatanDistance(Cell originCell, Cell targetCell) {
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
        listTwo.addAll(listOne);
    }

    private ArrayList<Cell> getNextCells (Cell cell) {
        ArrayList<Cell> nextCells = new ArrayList<>();
        if (cell.getRow() + 1 < Constant.GameConstants.boardRow) {
            nextCells.add(board.get(cell.getRow() + 1).get(cell.getColumn()));
        }
        if (cell.getColumn() + 1 < Constant.GameConstants.boardColumn) {
            nextCells.add(board.get(cell.getRow()).get(cell.getColumn() + 1));
        }
        if (cell.getRow() - 1 >= 0) {
            nextCells.add(board.get(cell.getRow() - 1).get(cell.getColumn()));
        }
        if (cell.getColumn() - 1 >= 0) {
            nextCells.add(board.get(cell.getRow()).get(cell.getColumn() - 1));
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
